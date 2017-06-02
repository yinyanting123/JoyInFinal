package com.example.android.joyin.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by Mr yin on 2017/5/23.
 */

public class hobby extends BmobObject {
    private String hobby_name;
    private Number hobby_id;

    public Number getHobby_id() {
        return hobby_id;
    }

    public void setHobby_id(Number hobby_id) {
        this.hobby_id = hobby_id;
    }

    public String getHobby_name() {
        return hobby_name;
    }

    public void setHobby_name(String hobby_name) {
        this.hobby_name = hobby_name;
    }


}
