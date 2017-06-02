package com.example.android.joyin.Asynctask.Add;

import android.os.Handler;
import android.os.Message;

import com.example.android.joyin.entity.activity;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2017/5/25.
 */

public class GetActivityByHobbyId extends Thread {

    Number hobby_id;
    Handler handler;
    Message message;
    public GetActivityByHobbyId( Number hobby_id,Handler handler) {
        this.hobby_id = hobby_id;
        this.handler = handler;
        message = this.handler.obtainMessage();
    }
    @Override
    public void run(){
        BmobQuery<activity> query = new BmobQuery<activity>();
        query.addWhereEqualTo("hobby_id", hobby_id);
        query.findObjects(new FindListener<activity>() {
            @Override
            public void done(List<activity> list, BmobException e) {
                if(e==null){
                    message.arg1=0;
                    if(list.size() != 0)
                        message.obj = list;
                    handler.sendMessage(message);
                }else{
                    message.arg1=1;
                }
            }
        });

    }
}

