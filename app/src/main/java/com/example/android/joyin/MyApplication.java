package com.example.android.joyin;

/**
 * Created by Mr yin on 2017/4/23.
 */

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.example.android.joyin.entity.User;
import com.example.android.joyin.entity.activity;
import com.tencent.TIMManager;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Mr yin on 2017/4/20.
 */

public class MyApplication extends Application {

    //个人信息，在登陆时或者注册的时候进行填充
    private String user_id;
    private String user_name;
    private String password;
    private BmobFile imagePhoto;
    private int user_score;
    private String user_telephone;
    private String gender;
    private User user;
    private int ScreenHeight;
    private activity ActivityNow;
    private List<activity> MyActivities;

    public List<activity> getMyActivities() {
        return MyActivities;
    }

    public void setMyActivities(List<activity> myActivities) {
        MyActivities = myActivities;
    }
    
    public activity getActivityNow() {
        return ActivityNow;
    }

    public void setActivityNow(activity activityNow) {
        ActivityNow = activityNow;
    }


    public int getScreenHeight() {
        return ScreenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        ScreenHeight = screenHeight;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static String getApplication_ID() {
        return Application_ID;
    }

    public static void setApplication_ID(String application_ID) {
        Application_ID = application_ID;
    }




    final int accType=12319;

    public int getAccType() {
        return accType;
    }

    public int getSdkAppid() {
        return sdkAppid;
    }

    public String getAppVer() {
        return appVer;
    }

    final int sdkAppid =1400029571;
    final String appVer="1.0";
    /**
     * 个人活动列表，需要包含历史活动，感兴趣活动（添加参加标注但活动未开始），正在参加活动
     * 用list数据结构进行存储
     * 还未填写
     */

    /**
     * BMOBh后台
     */
    public static String Application_ID="d60e003a0dceb5c11b6f04c039c2c4bd";



    public void onCreate() {
        // ... your codes
        // 务必检查IMSDK已做以下初始化
        TIMManager.getInstance().init(getApplicationContext());
        TIMManager.getInstance();

        Bmob.initialize(this, Application_ID);
        super.onCreate();

    }


    //*********************************************私有变量的get和set方法***************************************************
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getUser_score() {
        return user_score;
    }

    public void setUser_score(int user_score) {
        this.user_score = user_score;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public BmobFile getImagePhoto() {
        return imagePhoto;
    }

    public void setImagePhoto(BmobFile imagePhoto) {
        this.imagePhoto = imagePhoto;
    }

    public String getUser_telephone() {
        return user_telephone;
    }

    public void setUser_telephone(String user_telephone) {
        this.user_telephone = user_telephone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    //*********************************************私有变量的get和set方法***************************************************

    /**
     * @function: 对屏幕中间显示一个Toast，其内容为msg
     * */
    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

}
