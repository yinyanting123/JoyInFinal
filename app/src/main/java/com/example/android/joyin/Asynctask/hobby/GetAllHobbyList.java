package com.example.android.joyin.Asynctask.hobby;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.android.joyin.entity.user_hobby;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Mr yin on 2017/5/23.
 */

public class GetAllHobbyList extends Thread{
    Handler handler;
    Message message;
    String user_id;
    public GetAllHobbyList(Handler handler,String user_id){
        this.handler=handler;
        this.user_id=user_id;
        message = this.handler.obtainMessage();
    }

    @Override
    public void run(){
        BmobQuery<user_hobby> query = new BmobQuery<user_hobby>();
        query.addWhereEqualTo("user_id",user_id);
        query.setLimit(50);
        query.findObjects(new FindListener<user_hobby>() {
            @Override
            public void done(List<user_hobby> object, BmobException e) {
                if(e==null){
                    //查询成功
                    message.what = 2;
                    message.obj = object;
                    handler.sendMessage(message);
                    Log.i("bmob","Get user_hobby 成功:" + object.size());
                }
                //查询失败
                else {
                    Log.i("bmob","Get user_hobby 失败:" + e.getMessage());
                    message.what = 3;
                    handler.sendMessage(message);
                }
            }
        });
    }

}
