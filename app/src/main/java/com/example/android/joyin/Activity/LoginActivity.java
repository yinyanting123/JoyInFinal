package com.example.android.joyin.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.joyin.Asynctask.Login.BmobLogin;
import com.example.android.joyin.Asynctask.Login.QQLogin;
import com.example.android.joyin.MyApplication;
import com.example.android.joyin.R;
import com.example.android.joyin.entity.User;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.tencent.TIMManager;

import tencent.tls.platform.TLSLoginHelper;

public class LoginActivity extends AppCompatActivity {

    public EditText userTele;
    public EditText userPassword;
    public Handler handler;
    User user;

    TLSLoginHelper tlsLoginHelper;

    //***********全局变量************************
    MyApplication myApplication;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    //***********全局变量************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myApplication = (MyApplication) getApplication();
        this.handler = new Handler(new LoginCallback());

        userTele = (EditText) findViewById(R.id.login_name);
        userPassword = (EditText) findViewById(R.id.login_password);

        Toast.makeText(this, TIMManager.getInstance().getVersion() + "", Toast.LENGTH_LONG).show();
        TIMManager.getInstance().init(getApplicationContext());
        //qq登录
        initTLS();

        (findViewById(R.id.login_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* UserDAO userDAO = new UserDAOImpl(handler, getApplicationContext());
                userDAO.BothLogin(userTele.getText().toString(), userPassword.getText().toString(), tlsLoginHelper);*/
                new BmobLogin(userTele.getText().toString(), userPassword.getText().toString(), handler).start();

            }


        });

        (findViewById(R.id.goto_register_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), RegisterPhoneActivity.class));
                finish();
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    void initTLS() {
        tlsLoginHelper = TLSLoginHelper.getInstance().init(getApplicationContext(), myApplication.getSdkAppid(), myApplication.getAccType(), myApplication.getAppVer());
        tlsLoginHelper.setTimeOut(5000);
    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Login Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    private  class LoginCallback implements Handler.Callback{

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 0://登陆成功
                    Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                    break;
                case 1://bmob登陆成功
                    //将登陆信息存储在本地
                    User user=(User)msg.obj;
                    myApplication.setUser(user);
                    SharedPreferences sp = getSharedPreferences("UserInfo", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("userid", user.getUser_id());
                    editor.putString("usertele", user.getUser_telephone());
                    editor.putString("password", user.getPassword());
                    editor.commit();
                    Toast.makeText(getApplicationContext(), "开始qq登陆", Toast.LENGTH_SHORT).show();
                    //开始进行qq登陆
                    QQLogin qqLogin=new QQLogin(userTele.getText().toString(),
                            userPassword.getText().toString(),tlsLoginHelper,handler,getApplicationContext());
                    qqLogin.start();
                case 2://登陆出错
//                    Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        }
    }

}
