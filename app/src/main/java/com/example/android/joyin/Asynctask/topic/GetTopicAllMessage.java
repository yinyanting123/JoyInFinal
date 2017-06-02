package com.example.android.joyin.Asynctask.topic;

import android.os.Handler;
import android.os.Message;

import com.example.android.joyin.MyApplication;
import com.example.android.joyin.entity.TopicMessage;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static cn.bmob.v3.Bmob.getApplicationContext;

/**
 * Created by Mr yin on 2017/5/25.
 */

public class GetTopicAllMessage extends Thread {
    Number topic_id;
    Handler handler;
    Message message;
    MyApplication myApplication;
    public GetTopicAllMessage(int topic_id,
                     Handler handler){
        this.topic_id=topic_id;
        this.handler=handler;
        message = this.handler.obtainMessage();
        myApplication=(MyApplication)getApplicationContext();
    }

    @Override
    public void run() {
        BmobQuery<TopicMessage> query = new BmobQuery<TopicMessage>();
        //用user_id进行登录
        query.addWhereEqualTo("topic_id", topic_id);
        query.setLimit(50);
        query.findObjects(new FindListener<TopicMessage>() {
            @Override
            public void done(List<TopicMessage> object, BmobException e) {
                if (e == null) {
                        Message message = handler.obtainMessage();
                        message.what = 0;
                        message.obj=object;
                        //向handler发送消息
                        handler.sendMessage(message);
                    }
                 else {
                    message.what = 1;
                    //向handler发送消息
                    handler.sendMessage(message);
                    return;
                }
            }
        });
        super.run();
    }
}
