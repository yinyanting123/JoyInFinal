package com.example.android.joyin.Asynctask.activityList;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.android.joyin.entity.user_activity;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by MAC on 2017/5/20.
 */

public class GetActivityByUserid extends Thread {

    String user_id;
    Handler handler;
    Message message;

    public GetActivityByUserid( Handler handler,String user_id) {
        super();
        this.user_id = user_id;
        this.handler = handler;
        message = this.handler.obtainMessage();
    }

    @Override
    public void run() {
        BmobQuery<user_activity> query = new BmobQuery<user_activity>();
        query.addWhereEqualTo("user_id", user_id);
        query.findObjects(new FindListener<user_activity>() {
            @Override
            public void done(List<user_activity> object, BmobException e) {
                if(e==null){
                    //查询成功
                    message.what = 2;
                    if(object.size() != 0)
                        message.obj = object;
                    handler.sendMessage(message);
                    Log.d("bmob","GetDepotByID成功:" + object.size());
                }
                //查询失败
                else {
                    Log.d("bmob","GetDepotByID失败:" + e.getMessage() + e.getErrorCode());
                    message.what = 3;
                    handler.sendMessage(message);
                }
            }
        });
    }
}
