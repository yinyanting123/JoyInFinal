package com.example.android.joyin.Asynctask.topic;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.android.joyin.MyApplication;
import com.example.android.joyin.entity.TopicMessage;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import static cn.bmob.v3.Bmob.getApplicationContext;

/**
 * Created by Mr yin on 2017/5/24.
 */

public class SendTopicMessage extends Thread {
    private static final String TAG = "ListFragmentImpl";
    String message_item;
    Number topic_id;
    Handler handler;
    Message message;
    MyApplication myApplication;

    public Message getMessage() {
        return message;
    }

    public SendTopicMessage(String message_item, int topic_id, Handler handler){
        this.message_item=message_item;
        this.topic_id=topic_id;
        this.handler=handler;
        message = this.handler.obtainMessage();
        myApplication=(MyApplication)getApplicationContext();
    }

    @Override
    public void run() {
        TopicMessage topicMessage=new TopicMessage();
        topicMessage.setMessage_content(message_item);
        topicMessage.setTopic_id(topic_id);
        topicMessage.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    Log.d(TAG, "done: success");
                    message.what = 0;
                    handler.sendMessage(message);
                }else{
                    Log.d(TAG, "done: fail");
                    message.what = 0;
                    handler.sendMessage(message);
                }
            }
        });
    }

}
