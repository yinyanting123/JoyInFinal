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
 * Created by MAC on 2017/5/19.
 */

public class GetActivitiesPastList extends Thread {

    Handler handler;
    Message message;
    Number activityid;

    public GetActivitiesPastList(Handler handler,Number activity_id){
        super();
        this.handler = handler;
        activityid = activity_id;
        message = this.handler.obtainMessage();
    }


    public void run(){

        BmobQuery<activity> query = new BmobQuery<activity>();
        query.addWhereEqualTo("activity_id", activityid);
        query.findObjects(new FindListener<activity>() {
            @Override
            public void done(List<activity> object, BmobException e) {

                if(e==null){
                    //查询成功且有数据
                    message.arg1 = 0;
                    message.obj = object;
                    handler.sendMessage(message);
                    Log.i("bmob","成功，有数据:" + object.size());
                }
                //查询失败
                else {
                    message.arg1 = 1;
                    handler.sendMessage(message);
                    Log.i("bmob","失败有数据:" + e.getMessage()+e.getErrorCode());
                }
            }
        });
    }

}
