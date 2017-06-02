package com.example.android.joyin.Persetting;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.joyin.Activity.PersonSettingsActivity;
import com.example.android.joyin.MyApplication;
import com.example.android.joyin.R;
import com.example.android.joyin.entity.User;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class PersonSettingsAddressActivity extends AppCompatActivity {

    private EditText changedAddress;
    private Button saveAddressChange;
    MyApplication myApplication;
    private String userid;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peraddress);

        myApplication = (MyApplication)getApplication();
        changedAddress = (EditText)findViewById(R.id.changed_address);
        saveAddressChange = (Button)findViewById(R.id.save_address_change);

        userid = myApplication.getUser().getUser_id();
    }

    public void saveAddressChange(View view){

        myApplication = (MyApplication)getApplication();
        changedAddress = (EditText)findViewById(R.id.changed_address);
        saveAddressChange = (Button)findViewById(R.id.save_address_change);

        userid = myApplication.getUser().getUser_id();

        User p2 = new User();
        p2.setUser_address(changedAddress.getText().toString());
        p2.update(myApplication.getUser().getObjectId(), new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    Toast.makeText(getApplicationContext(),"更新成功:",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"更新失败：" ,Toast.LENGTH_LONG).show();
                }
            }

        });

        myApplication.getUser().setUser_address(changedAddress.getText().toString());

        Intent intent = new Intent(PersonSettingsAddressActivity.this, PersonSettingsActivity.class);

        //启动Activity
        startActivity(intent);
    }

}
