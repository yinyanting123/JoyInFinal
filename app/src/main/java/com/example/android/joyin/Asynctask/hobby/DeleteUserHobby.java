package com.example.android.joyin.Asynctask.hobby;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.android.joyin.entity.user_hobby;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Mr yin on 2017/5/23.
 */

public class DeleteUserHobby extends Thread {

    public  static final String TAG="DeleteUserHobby";
    static boolean success=true;

    Handler handler;
    Message message;
    List<user_hobby> user_hobbies;
    public DeleteUserHobby(Handler handler,List<user_hobby> user_hobbies){
        this.handler=handler;
        message = this.handler.obtainMessage();
        this.user_hobbies = user_hobbies;
    }

    @Override
    public void run() {
        for (final user_hobby u_h_item:user_hobbies
                ) {
            u_h_item.delete(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        Log.d(TAG, "done: "+u_h_item.getHobby_id());
                    } else {
                        Log.d(TAG, "done: fail");
                        success=false;
                    }
                }
            });
        }
        if(success){
            message.what = 4;
            handler.sendMessage(message);
        }else{
            message.what = 5;
            handler.sendMessage(message);
        }


    }
}
