package com.example.android.joyin.dao;

import tencent.tls.platform.TLSLoginHelper;

/**
 * Created by Mr yin on 2017/5/1.
 */

public interface UserDAO {
    //注册
    void register(String username,String password,String email);
    //上传图片
    void uploadPhoto(String ObjectID,String imagePath);
    void BothLogin(String userTele,String userPassword,TLSLoginHelper tlsLoginHelper);
}
