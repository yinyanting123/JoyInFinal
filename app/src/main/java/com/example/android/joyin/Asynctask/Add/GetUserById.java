package com.example.android.joyin.Asynctask.Add;

import android.os.Handler;
import android.os.Message;

import com.example.android.joyin.entity.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2017/5/24.
 */

public class GetUserById extends Thread {

    String user_id;
    Handler handler;
    Message message;
    public GetUserById( String user_id,Handler handler) {
        this.user_id = user_id;
        this.handler = handler;
        message = this.handler.obtainMessage();
    }
    @Override
    public void run(){
        BmobQuery<User> query = new BmobQuery<User>();
        query.addWhereEqualTo("user_id", user_id);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if(e==null){
                    message.arg1=0;
                    if(list.size() != 0)
                        message.obj = list.get(0);
                    handler.sendMessage(message);
                }else{
                    message.arg1=1;
                }
            }
        });
    }
}
