package com.example.android.joyin.Asynctask.Login;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.android.joyin.MyApplication;
import com.tencent.TIMCallBack;
import com.tencent.TIMManager;
import com.tencent.TIMUser;

import tencent.tls.platform.TLSErrInfo;
import tencent.tls.platform.TLSLoginHelper;
import tencent.tls.platform.TLSPwdLoginListener;
import tencent.tls.platform.TLSUserInfo;

import static cn.bmob.v3.Bmob.getApplicationContext;

/**
 * Created by Mr yin on 2017/5/10.
 */

public class QQLogin extends Thread{
    Message message;
    String userTele;
    String userPassword;
    TLSLoginHelper tlsLoginHelper;
    Handler handler;
    MyApplication myApplication;
    Context context;
    public QQLogin(String userTele,
                   String userPassword,
                   TLSLoginHelper tlsLoginHelper,
                   Handler handler,
                   Context context){
        this.userTele=userTele;
        this.userPassword=userPassword;
        this.handler=handler;
        message = this.handler.obtainMessage();
        this.context=context;
        myApplication=(MyApplication)context;
        this.tlsLoginHelper = TLSLoginHelper.getInstance().init(getApplicationContext(), myApplication.getSdkAppid(), myApplication.getAccType(), myApplication.getAppVer());
        this.tlsLoginHelper.setTimeOut(5000);
    }

    @Override
    public void run() {
        Log.d("IMSDK","======tls login begin");

        tlsLoginHelper.TLSPwdLogin("86-"+userTele, userPassword.getBytes(),
                new TLSPwdLoginListener() {
                    @Override
                    public void OnPwdLoginSuccess(TLSUserInfo tlsUserInfo) {
                        Log.d("IMSDK","======tls login succeed");
                        String usersig=tlsLoginHelper.getUserSig("86-"+userTele);

                        TIMUser user=new TIMUser();
                        user.setIdentifier("86-"+userTele);
                        user.setAppIdAt3rd(String.valueOf(myApplication.getSdkAppid()));
                        user.setAccountType(String.valueOf(myApplication.getAccType()));
                        TIMManager.getInstance().login(myApplication.getSdkAppid(), user, usersig,
                                new TIMCallBack() {
                                    @Override
                                    public void onError(int i, String s) {
                                        Log.d("IMSDK","======imsdk login error");
                                        Message message = handler.obtainMessage();
                                        message.what = 2;
                                        //向handler发送消息
                                        handler.sendMessage(message);
                                    }

                                    @Override
                                    public void onSuccess() {
                                        Log.d("IMSDK","======imsdk login succeed");
                                        Message message = handler.obtainMessage();
                                        message.what = 0;
                                        //向handler发送消息
                                        handler.sendMessage(message);
                                    }
                                });
                    }

                    @Override
                    public void OnPwdLoginReaskImgcodeSuccess(byte[] bytes) {
                    }

                    @Override
                    public void OnPwdLoginNeedImgcode(byte[] bytes, TLSErrInfo tlsErrInfo) {
                        Log.d("IMSDK","======tls login error");

                    }

                    @Override
                    public void OnPwdLoginFail(TLSErrInfo tlsErrInfo) {
                        Log.d("IMSDK","======tls login OnPwdLoginFail");
                    }

                    @Override
                    public void OnPwdLoginTimeout(TLSErrInfo tlsErrInfo) {
                        Log.d("IMSDK","======tls login OnPwdLoginTimeout");
                    }
                });
        super.run();
    }
}
