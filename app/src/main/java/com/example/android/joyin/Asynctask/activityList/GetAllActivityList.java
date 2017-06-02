package com.example.android.joyin.Asynctask.activityList;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.android.joyin.entity.activity;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Mr yin on 2017/5/20.
 */

public class GetAllActivityList extends Thread{
    Handler handler;
    Message message;
    public GetAllActivityList(Handler handler){
        this.handler=handler;
        message = this.handler.obtainMessage();
    }

    @Override
    public void run(){
        BmobQuery<activity> query = new BmobQuery<activity>();
        query.addWhereGreaterThan("activity_person_num",-1);
        query.setLimit(50);
        query.findObjects(new FindListener<activity>() {
            @Override
            public void done(List<activity> object, BmobException e) {
                if(e==null){
                    //查询成功
                    message.arg1 = 0;
                    message.obj = object;
                    handler.sendMessage(message);
                    Log.i("bmob","GetAllOrder成功:" + object.size());
                }
                //查询失败
                else {
                    Log.i("bmob","GetAllOrder失败:" + e.getMessage());
                    message.arg1 = 1;
                    handler.sendMessage(message);
                }
            }
        });
    }
}
