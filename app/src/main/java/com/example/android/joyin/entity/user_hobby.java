package com.example.android.joyin.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by Mr yin on 2017/5/23.
 */

public class user_hobby extends BmobObject {
    private String user_id;
    private Number hobby_id;
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Number getHobby_id() {
        return hobby_id;
    }

    public void setHobby_id(Number hobby_id) {
        this.hobby_id = hobby_id;
    }



}
