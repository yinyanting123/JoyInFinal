package com.example.android.joyin.Asynctask.Login;

import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.example.android.joyin.MyApplication;
import com.example.android.joyin.entity.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static cn.bmob.v3.Bmob.getApplicationContext;

/**
 * Created by Mr yin on 2017/5/10.
 */

public class BmobLogin extends Thread {
    String userTele;
    String userPassword;
    Handler handler;
    Message message;
    MyApplication myApplication;
    public BmobLogin(String userTele,
                     String userPassword,
                     Handler handler){
        this.userTele=userTele;
        this.userPassword=userPassword;
        this.handler=handler;
        message = this.handler.obtainMessage();
        myApplication=(MyApplication)getApplicationContext();
    }

    @Override
    public void run() {
        BmobQuery<User> query = new BmobQuery<User>();
        //用user_id进行登录
        query.addWhereEqualTo("user_id", "86-"+userTele);
        query.setLimit(10);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if (e == null) {
                    if (object.get(0).getPassword().equals(userPassword)){
                        Toast.makeText(getApplicationContext(), "bmob登录成功", Toast.LENGTH_LONG).show();
                        Message message = handler.obtainMessage();
                        message.what = 1;
                        message.obj=object.get(0);
                        myApplication.setUser((User) object.get(0));
                        //向handler发送消息
                        handler.sendMessage(message);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "失败：", Toast.LENGTH_LONG).show();
                    Message message = handler.obtainMessage();
                    message.what = 2;
                    //向handler发送消息
                    handler.sendMessage(message);
                    return;
                }
            }
        });
        super.run();
    }
}
