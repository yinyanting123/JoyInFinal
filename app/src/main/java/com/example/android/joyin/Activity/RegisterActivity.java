package com.example.android.joyin.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.joyin.MyApplication;
import com.example.android.joyin.R;
import com.example.android.joyin.Tool.Util;
import com.example.android.joyin.dao.UserDAO;
import com.example.android.joyin.dao.impl.UserDAOImpl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tencent.tls.platform.TLSAccountHelper;
import tencent.tls.platform.TLSErrInfo;
import tencent.tls.platform.TLSPwdRegListener;
import tencent.tls.platform.TLSUserInfo;

import static com.tencent.qalsdk.service.QalService.context;

public class RegisterActivity extends AppCompatActivity {
    EditText telephone;
    EditText password;
    EditText password_twice;

    //***********全局变量************************
    MyApplication myApplication;
    //***********全局变量************************

    /**
     * QQ注册
     * @param savedInstanceState
     */
    TLSAccountHelper accountHelper;
    TLSPwdRegListener tlsPwdRegListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        telephone=(EditText)findViewById(R.id.register_name);
        password_twice=(EditText)findViewById(R.id.register_password_twice);
        password=(EditText)findViewById(R.id.register_password);

        //对悬浮按钮的额操作
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        findViewById(R.id.register_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDAO userdao=new UserDAOImpl();
                userdao.register(telephone.getText().toString(),password.getText().toString(),password_twice.getText().toString());
            }
        });
    }


    public static boolean isPhone(String inputText) {
        Pattern p = Pattern.compile("^((14[0-9])|(13[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\\d{8}$");
        Matcher m = p.matcher(inputText);
        return m.matches();
    }



}
