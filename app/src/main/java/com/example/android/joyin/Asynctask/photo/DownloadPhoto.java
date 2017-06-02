package com.example.android.joyin.Asynctask.photo;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.android.joyin.MyApplication;
import com.example.android.joyin.entity.User;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;

import static cn.bmob.v3.Bmob.getApplicationContext;

/**
 * Created by Mr yin on 2017/5/20.
 */

public class DownloadPhoto extends Thread {
    String objectId;
    Handler handler;
    Message message;
    MyApplication myApplication;

    public Message getMessage() {
        return message;
    }

    public DownloadPhoto(String objectId,
                       Handler handler){
        this.objectId=objectId;
        this.handler=handler;
        message = this.handler.obtainMessage();
        myApplication=(MyApplication)getApplicationContext();
    }

    @Override
    public void run() {
        BmobQuery<User> query = new BmobQuery<User>();
        //用user_id进行登录
        query.addWhereEqualTo("objectId", objectId);
        query.setLimit(10);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(), "获取图片路径", Toast.LENGTH_LONG).show();
                    BmobFile bmobfile =object.get(0).getImagePhoto();
                    if(bmobfile!=null){
                        downloadFile(bmobfile);
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "获取属性失败", Toast.LENGTH_LONG).show();
                    Message message = handler.obtainMessage();
                    message.what = 5;
                    //向handler发送消息
                    handler.sendMessage(message);
                    return;
                }
            }
        });
        super.run();
    }

    private void downloadFile(BmobFile file){
        //允许设置下载文件的存储路径，默认下载文件的目录为：context.getApplicationContext().getCacheDir()+"/bmob/"
        File saveFile = new File(Environment.getExternalStorageDirectory(), file.getFilename());
        file.download(saveFile, new DownloadFileListener() {

            @Override
            public void onStart() {
                Toast.makeText(getApplicationContext(), "开始下载图片", Toast.LENGTH_LONG).show();
            }

            @Override
            public void done(String savePath,BmobException e) {
                if(e==null){
                    Toast.makeText(getApplicationContext(), "下载成功,保存路径:"+savePath, Toast.LENGTH_LONG).show();
                    Message message = handler.obtainMessage();
                    message.what = 3;
                    message.obj=savePath;
                    Toast.makeText(getApplicationContext(), "下载成功,保存路径:"+savePath, Toast.LENGTH_LONG).show();
                    Log.d("图片路径", "done: "+savePath);
                    //向handler发送消息
                    handler.sendMessage(message);
                }else{
                    Toast.makeText(getApplicationContext(), "下载失败："+e.getErrorCode()+","+e.getMessage(), Toast.LENGTH_LONG).show();
                    Message message = handler.obtainMessage();
                    message.what = 4;
                    //向handler发送消息
                    handler.sendMessage(message);
                }
            }

            @Override
            public void onProgress(Integer value, long newworkSpeed) {
                Toast.makeText(getApplicationContext(),"下载进度："+value+","+newworkSpeed, Toast.LENGTH_LONG).show();
            }

        });
    }
}
