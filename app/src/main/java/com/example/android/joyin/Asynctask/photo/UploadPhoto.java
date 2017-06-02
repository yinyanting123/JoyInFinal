package com.example.android.joyin.Asynctask.photo;

import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.example.android.joyin.MyApplication;
import com.example.android.joyin.entity.User;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

import static cn.bmob.v3.Bmob.getApplicationContext;

/**
 * Created by Mr yin on 2017/5/12.
 */

public class UploadPhoto extends Thread {
    String objectId;
    String photoPath;
    Handler handler;
    Message message;
    MyApplication myApplication;

    public Message getMessage() {
        return message;
    }

    public UploadPhoto(String objectId,
                       String photoPath,
                       Handler handler){
        this.objectId=objectId;
        this.photoPath=photoPath;
        this.handler=handler;
        message = this.handler.obtainMessage();
        myApplication=(MyApplication)getApplicationContext();
    }

    @Override
    public void run() {
        final BmobFile bmobFile=new BmobFile(new File(photoPath));
        User user=new User();
        user.setImagePhoto(bmobFile);
        bmobFile.uploadblock(new UploadFileListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    //bmobFile.getFileUrl()--返回的上传文件的完整地址
                    Toast.makeText(getApplicationContext(), "上传文件成功:" + bmobFile.getFileUrl(), Toast.LENGTH_SHORT).show();
                    User user = new User();//是继承了BmobObject的一个类
                    user.setImagePhoto(bmobFile);
                    user.update(myApplication.getUser().getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Toast.makeText(getApplicationContext(), "修改用户属性成功:" + bmobFile.getFileUrl(), Toast.LENGTH_SHORT).show();
                                Message message = handler.obtainMessage();
                                message.what = 0;
                                //向handler发送消息
                                handler.sendMessage(message);
                            }else{
                                Toast.makeText(getApplicationContext(), "修改用户属性失败" + bmobFile.getFileUrl(), Toast.LENGTH_SHORT).show();
                                Message message = handler.obtainMessage();
                                message.what = 1;
                            }
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(), "上传文件失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Message message = handler.obtainMessage();
                    message.what = 2;
                }

            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
                Toast.makeText(getApplicationContext(),"========="+value+"=========" , Toast.LENGTH_SHORT).show();

            }
        });
        super.run();
    }
}
