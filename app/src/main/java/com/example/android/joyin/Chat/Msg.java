package com.example.android.joyin.Chat;

/**
 * Created by Mr yin on 2017/4/23.
 */

public class Msg {
    static final int TYPE_RECEIVE = 1;
    static final int TYPE_SEND = 2;
    String content;
    int type;

    Msg(String content, int type) {
        this.content = content;
        this.type = type;
    }
}
