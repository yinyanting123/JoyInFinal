package com.example.android.joyin.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by MAC on 2017/5/5.
 */

public class user_activity extends BmobObject {

    private String user_id;
    private String activity_id;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(String activity_id) {
        this.activity_id = activity_id;
    }
}
