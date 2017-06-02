package com.example.android.joyin.Asynctask.Add;

import android.os.Handler;
import android.os.Message;

import com.example.android.joyin.entity.activity;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2017/5/25.
 */

public class JoinActivity extends Thread {
    String objectid;
    Handler handler;
    Message message;
    Number activity_per_need;
    public JoinActivity(String objectid, Number activity_per_need, Handler handler){
        this.objectid = objectid;
        this.handler = handler;
        this.activity_per_need = activity_per_need;
        message = this.handler.obtainMessage();
    }
    public void run(){
        activity act = new activity();
        act.setActivity_person_need(activity_per_need);
        act.update(objectid,new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                   message.arg1=0;
                    handler.sendMessage(message);
                }else{
                    message.arg1=1;
                    handler.sendMessage(message);
                }
            }
        });

    }
}
