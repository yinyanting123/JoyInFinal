package com.example.android.joyin.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by Mr yin on 2017/5/24.
 */

public class TopicMessage extends BmobObject {
    private Number topic_id;
    private Number message_id;
    private String message_content;
    private String user_id;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }


    public Number getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(Number topic_id) {
        this.topic_id = topic_id;
    }

    public Number getMessage_id() {
        return message_id;
    }

    public void setMessage_id(Number message_id) {
        this.message_id = message_id;
    }

    public String getMessage_content() {
        return message_content;
    }

    public void setMessage_content(String message_content) {
        this.message_content = message_content;
    }


}
