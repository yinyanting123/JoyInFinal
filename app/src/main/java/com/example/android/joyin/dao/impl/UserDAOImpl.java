package com.example.android.joyin.dao.impl;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.android.joyin.MyApplication;
import com.example.android.joyin.dao.UserDAO;
import com.example.android.joyin.entity.User;
import com.tencent.TIMCallBack;
import com.tencent.TIMManager;
import com.tencent.TIMUser;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import tencent.tls.platform.TLSErrInfo;
import tencent.tls.platform.TLSLoginHelper;
import tencent.tls.platform.TLSPwdLoginListener;
import tencent.tls.platform.TLSUserInfo;

import static cn.bmob.v3.Bmob.getApplicationContext;
import static com.example.android.joyin.Activity.RegisterActivity.isPhone;

/**
 * Created by Mr yin on 2017/5/1.
 */

public class UserDAOImpl implements UserDAO {
    public MyApplication myApplication;
    public Context context;
    public Handler handler;
    public UserDAOImpl(){}
    public UserDAOImpl(Handler handler)
    {
        this.handler=handler;
    }
    public UserDAOImpl(Handler handler,Context context)
    {
        this.handler=handler;
        this.context=context;
    }
    @Override
    //还需补充查找是否电话号码重复
    public void register(String telephone,String password,String password_twice){

        if(telephone.isEmpty()||password.isEmpty()||password_twice.isEmpty()){
            Toast.makeText(getApplicationContext(),"请将信息添加完整",Toast.LENGTH_SHORT).show();
            return;
        }

        if(!isPhone(telephone)){
            Toast.makeText(getApplicationContext(),"电话号码格式不正确，请重新输入",Toast.LENGTH_SHORT).show();
            return;
        }

        if(!(password.equals(password_twice))){
            Toast.makeText(getApplicationContext(),"两次密码输入不一致",Toast.LENGTH_SHORT).show();
            return;
        }


        User user=new User();
        user.setUser_id("86-"+telephone);
        user.setUser_telephone(telephone);
        user.setPassword(password);
        user.save(new SaveListener<String>() {
            @Override
            public void done(String objectId,BmobException e) {
                if(e==null){
                    Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_LONG).show();
                }else{
                   Toast.makeText(getApplicationContext(),"创建数据失败：" + e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void uploadPhoto(String ObjectId,String imagePath) {
        final BmobFile bmobFile=new BmobFile(new File(imagePath));
        User user=new User();
        user.setImagePhoto(bmobFile);
        bmobFile.uploadblock(new UploadFileListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    //bmobFile.getFileUrl()--返回的上传文件的完整地址
                    Toast.makeText(getApplicationContext(), "上传文件成功:" + bmobFile.getFileUrl(), Toast.LENGTH_SHORT).show();
                    User user = new User();//是继承了BmobObject的一个类
                    user.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if(e==null){
                                Log.d("bmob", "成功");
                                Message message = handler.obtainMessage();
                                message.what = 0;
                                //向handler发送消息
                                handler.sendMessage(message);
                            }else{
                                Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                                Message message = handler.obtainMessage();
                                message.what = 1;
                            }
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(), "上传文件失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
                Toast.makeText(getApplicationContext(),"========="+value+"=========" , Toast.LENGTH_SHORT).show();

            }
        });
        /*bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    //bmobFile.getFileUrl()--返回的上传文件的完整地址
                    Toast.makeText(getApplicationContext(),"上传文件成功:",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"上传文件失败:",Toast.LENGTH_LONG).show();
                }
            }
        });*/

    }

    public void BothLogin(final String userTele,final String userPassword,final TLSLoginHelper tlsLoginHelper){

        BmobQuery<User> query = new BmobQuery<User>();
        //用user_id进行登录
        query.addWhereEqualTo("user_id", "86-"+userTele);
        query.setLimit(100);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if (e == null) {
                    if (object.get(0).getPassword().equals(userPassword)){
                        Toast.makeText(getApplicationContext(), "bmob登录成功", Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), "开始进行QQ登陆", Toast.LENGTH_LONG).show();
                        login(userTele,userPassword,tlsLoginHelper);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "失败：", Toast.LENGTH_LONG).show();
                    Message message = handler.obtainMessage();
                    message.what = 1;
                    //向handler发送消息
                    handler.sendMessage(message);
                    return;
                }
            }
        });

    }


    public void login(final String userTele,final String userPassword,final TLSLoginHelper tlsLoginHelper){
        Log.d("IMSDK","======tls login begin");
        Toast.makeText(context,"tls login begin",Toast.LENGTH_LONG).show();

        tlsLoginHelper.TLSPwdLogin("86-"+userTele, userPassword.getBytes(),
                new TLSPwdLoginListener() {
                    @Override
                    public void OnPwdLoginSuccess(TLSUserInfo tlsUserInfo) {
                        Toast.makeText(context,"tls login success",Toast.LENGTH_LONG).show();
                        Log.d("IMSDK","======tls login succeed");
                        String usersig=tlsLoginHelper.getUserSig("86-"+userTele);
                        Toast.makeText(context,usersig,Toast.LENGTH_LONG).show();

                        TIMUser user=new TIMUser();
                        user.setIdentifier("86-"+userTele);
                        user.setAppIdAt3rd(String.valueOf(myApplication.getSdkAppid()));
                        user.setAccountType(String.valueOf(myApplication.getAccType()));
                        TIMManager.getInstance().login(myApplication.getSdkAppid(), user, usersig,
                                new TIMCallBack() {
                                    @Override
                                    public void onError(int i, String s) {
                                        Log.d("IMSDK","======imsdk login error");
                                        Toast.makeText(context,"======imsdk login error",Toast.LENGTH_LONG).show();
                                        Message message = handler.obtainMessage();
                                        message.what = 1;
                                        //向handler发送消息
                                        handler.sendMessage(message);
                                    }

                                    @Override
                                    public void onSuccess() {
                                        Log.d("IMSDK","======imsdk login succeed");
                                       Toast.makeText(context,"======imsdk login succeed",Toast.LENGTH_LONG).show();
                                        Message message = handler.obtainMessage();
                                        message.what = 0;
                                        //向handler发送消息
                                        handler.sendMessage(message);
                                    }
                                });
                    }

                    @Override
                    public void OnPwdLoginReaskImgcodeSuccess(byte[] bytes) {
                        Toast.makeText(context,"tls login OnPwdLoginReaskImgcodeSuccess",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void OnPwdLoginNeedImgcode(byte[] bytes, TLSErrInfo tlsErrInfo) {
                        Toast.makeText(context,"tls login OnPwdLoginNeedImgcode",Toast.LENGTH_LONG).show();

                        Log.d("IMSDK","======tls login error");

                    }

                    @Override
                    public void OnPwdLoginFail(TLSErrInfo tlsErrInfo) {
                        Toast.makeText(context,"tls login OnPwdLoginFail",Toast.LENGTH_LONG).show();

                        Log.d("IMSDK","======tls login OnPwdLoginFail");
                    }

                    @Override
                    public void OnPwdLoginTimeout(TLSErrInfo tlsErrInfo) {
                        Toast.makeText(context,"tls login OnPwdLoginTimeout",Toast.LENGTH_LONG).show();

                        Log.d("IMSDK","======tls login OnPwdLoginTimeout");
                    }
                });
    }



   /* public boolean LogIn(String username, final String password)  {
        username="you";
        if(username.isEmpty()||password.isEmpty()){
            Toast.makeText(getApplicationContext(),"请将信息添加完整",Toast.LENGTH_SHORT).show();
            return false;
        }
        BmobQuery<User> query=new BmobQuery<User>();
        query.addWhereEqualTo("userName",username);
        query.setLimit(100);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if(e==null){
                    User user=new User();
                    user= object.get(0);
                    if(user.getPassword().equals(testpassword))
                        isFind=true;

                }else{
                    isFind=false;
                }
            }
        });try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return isFind;
    }*/

}
