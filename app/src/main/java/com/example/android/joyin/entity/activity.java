package com.example.android.joyin.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by MAC on 2017/5/4.
 */

public class activity extends BmobObject {

    private BmobDate activity_starttime;
    private String activity_position;
    private Number activity_person_num;
    private String activity_name;
    private BmobFile activity_image_id;
    private String activity_id;
    private String activity_host_id;
    private String activity_description;
    private Number aclassify_id;
    private Number hobby_id;

    public Number getHobby_id() {
        return hobby_id;
    }

    public void setHobby_id(Number hobby_id) {
        this.hobby_id = hobby_id;
    }

    private Number activity_person_need;
    public Number getActivity_person_need() {
        return activity_person_need;
    }

    public void setActivity_person_need(Number activity_person_need) {
        this.activity_person_need = activity_person_need;
    }

    public BmobDate getActivity_starttime() {
        return activity_starttime;
    }

    public void setActivity_starttime(BmobDate activity_starttime) {
        this.activity_starttime = activity_starttime;
    }

    public String getActivity_position() {
        return activity_position;
    }

    public void setActivity_position(String activity_position) {
        this.activity_position = activity_position;
    }

    public Number getActivity_person_num() {
        return activity_person_num;
    }

    public void setActivity_person_num(Number activity_person_num) {
        this.activity_person_num = activity_person_num;
    }

    public String getActivity_name() {
        return activity_name;
    }

    public void setActivity_name(String activity_name) {
        this.activity_name = activity_name;
    }

    public BmobFile getActivity_image_id() {
        return activity_image_id;
    }

    public void setActivity_image_id(BmobFile activity_image_id) {
        this.activity_image_id = activity_image_id;
    }

    public String getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(String activity_id) {
        this.activity_id = activity_id;
    }

    public String getActivity_host_id() {
        return activity_host_id;
    }

    public void setActivity_host_id(String activity_host_id) {
        this.activity_host_id = activity_host_id;
    }

    public String getActivity_description() {
        return activity_description;
    }

    public void setActivity_description(String activity_description) {
        this.activity_description = activity_description;
    }

    public Number getAclassify_id() {
        return aclassify_id;
    }

    public void setAclassify_id(Number aclassify_id) {
        this.aclassify_id = aclassify_id;
    }
}
