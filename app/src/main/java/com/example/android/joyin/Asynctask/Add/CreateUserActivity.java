package com.example.android.joyin.Asynctask.Add;

import android.os.Handler;
import android.os.Message;

import com.example.android.joyin.entity.user_activity;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2017/5/25.
 */

public class CreateUserActivity extends Thread {

    String user_id;
    String activity_id;
    Handler handler;
    Message message;
    user_activity userActivity = new user_activity();
    public CreateUserActivity(String user_id,String activity_id,Handler handler){
        this.user_id = user_id;
        this.activity_id = activity_id;
        this.handler = handler;
        message = this.handler.obtainMessage();
    }
    public void run(){
        BmobQuery<user_activity> query = new BmobQuery<user_activity>();
        query.addWhereEqualTo("user_id",user_id);
        query.findObjects(new FindListener<user_activity>() {
            @Override
            public void done(List<user_activity> list, BmobException e) {
                if(e==null){
                    if(list.size() > 0){
                        userActivity.setActivity_id(activity_id);
                        userActivity.setUser_id(user_id);
                        userActivity.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if(e==null){
                                    message.arg1=0;
                                    handler.sendMessage(message);
                                }else{
                                    message.arg1=1;
                                    handler.sendMessage(message);
                                }
                            }
                        });
                    }else{
                        message.arg1=2;
                        handler.sendMessage(message);
                    }
                }
                else{
                    message.arg1=3;
                    handler.sendMessage(message);
                }
            }
        });
    }
}
