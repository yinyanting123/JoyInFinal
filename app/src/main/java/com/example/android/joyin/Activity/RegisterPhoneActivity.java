package com.example.android.joyin.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.joyin.MyApplication;
import com.example.android.joyin.R;
import com.example.android.joyin.Tool.Util;
import com.example.android.joyin.dao.UserDAO;
import com.example.android.joyin.dao.impl.UserDAOImpl;

import tencent.tls.platform.TLSAccountHelper;
import tencent.tls.platform.TLSErrInfo;
import tencent.tls.platform.TLSPwdRegListener;
import tencent.tls.platform.TLSUserInfo;

public class RegisterPhoneActivity extends AppCompatActivity {
    EditText telephone;
    EditText verification;
    EditText password;
    EditText password_twice;
    Button signIn;
    Button getVerification;
    Button verify;
    private Context context;
    //***********全局变量************************
    MyApplication myApplication;
    //***********全局变量************************
    /**
     * QQ注册
     * @param savedInstanceState
     */
    TLSAccountHelper accountHelper;
    PwdRegListener pwdRegListener;
    private final static String TAG = "RegisterPhoneActivity";
    private String phoneNumber;
    private String checkCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_phone);

        telephone=(EditText)findViewById(R.id.register_tele);
        verification=(EditText)findViewById(R.id.register_ma);
        password=(EditText)findViewById(R.id.register_phone_password);
        password_twice = (EditText)findViewById(R.id.register_phone_password_twice);
        getVerification=(Button)findViewById(R.id.register_get);
        verify=(Button)findViewById(R.id.register_verify);
        signIn=(Button)findViewById(R.id.register_sign);
//对QQ通信进行初始化
        myApplication = (MyApplication) getApplication();
        accountHelper = TLSAccountHelper.getInstance()
                .init(getApplicationContext(),
                        myApplication.getSdkAppid(),
                        myApplication.getAccType(),
                        myApplication.getAppVer());
        pwdRegListener=new PwdRegListener();

        //获取验证码
        getVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountHelper.TLSPwdRegAskCode("86-"+telephone.getText().toString(),pwdRegListener);
            }
        });

        //对验证码进行验证
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountHelper.TLSPwdRegVerifyCode(verification.getText().toString(),pwdRegListener);

            }
        });

        //输入密码完成注册
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountHelper.TLSPwdRegCommit(password.getText().toString(),pwdRegListener);
            }
        });

    }

    public class PwdRegListener implements TLSPwdRegListener {
        @Override
        public void OnPwdRegAskCodeSuccess(int reaskDuration, int expireDuration) {
            Toast.makeText(getApplicationContext(),"请求下发短信成功,验证码" + expireDuration / 60 + "分钟内有效",Toast.LENGTH_LONG).show();
            // 在获取验证码按钮上显示重新获取验证码的时间间隔
          Util.startTimer(getVerification, "获取验证码", "重新获取", reaskDuration, 1);
        }

        @Override
        public void OnPwdRegReaskCodeSuccess(int reaskDuration, int expireDuration) {
            Toast.makeText(getApplicationContext(),"注册短信重新下发,验证码" + expireDuration / 60 + "分钟内有效",Toast.LENGTH_LONG).show();

            Util.startTimer(getVerification, "获取验证码", "重新获取", reaskDuration, 1);
        }

        @Override
        public void OnPwdRegVerifyCodeSuccess() {
            Toast.makeText(getApplicationContext(),"注册验证通过，准备获取号码",Toast.LENGTH_LONG).show();
        }

        @Override
        public void OnPwdRegCommitSuccess(TLSUserInfo userInfo) {
            Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_LONG).show();
            //将信息填写到云服务器后台
            UserDAO userdao=new UserDAOImpl();
            userdao.register(telephone.getText().toString(),password.getText().toString(),password_twice.getText().toString());
            //将用户信息存放在全局变量中
            myApplication.setUser_id("86-"+telephone.getText().toString());
            myApplication.setPassword(password.getText().toString());
            myApplication.setUser_telephone(password.getText().toString());
            //将登陆信息存储在本地
            SharedPreferences sp = getSharedPreferences("UserInfo", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("userid", myApplication.getUser_id());
            editor.putString("password", myApplication.getPassword());
            editor.commit();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }

        @Override
        public void OnPwdRegFail(TLSErrInfo errInfo) {
            Util.notOK(context, errInfo);
        }

        @Override
        public void OnPwdRegTimeout(TLSErrInfo errInfo) {
            Util.notOK(context, errInfo);
        }
    }
}
