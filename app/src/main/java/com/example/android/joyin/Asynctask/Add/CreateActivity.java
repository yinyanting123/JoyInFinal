package com.example.android.joyin.Asynctask.Add;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.android.joyin.entity.activity;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by Administrator on 2017/5/23.
 */

public class CreateActivity extends Thread {

    activity act = new activity();
    BmobDate starttime;
    Handler handler;
    String userid;
    Number hobby_id;
    String activity_name;
    String activity_addr;
    String activity_detail;
    Number activity_per_num;
    Number activity_per_need;
    Number aclassify_id;
    String photoPath;
    Message message;
    public CreateActivity(String photoPath, String activity_name, String activity_addr, String activity_detail,
                          Number activity_per_num, Number activity_per_need, Number aclassify_id,Number hobby_id, String userid, BmobDate Starttime, Handler handler){

        this.photoPath = photoPath;
        this.activity_name = activity_name;
        this.activity_addr = activity_addr;
        this.activity_detail=activity_detail;
        this.activity_per_num =activity_per_num;
        this.activity_per_need = activity_per_need;
        this.aclassify_id = aclassify_id;
        this.hobby_id = hobby_id;
        this.userid =userid;
        this.starttime = Starttime;
        this.handler  = handler;
        message = this.handler.obtainMessage();
    }
    public void run() {

        BmobQuery<activity> query = new BmobQuery<activity>();
        query.addWhereEqualTo("activity_host_id",userid);
        query.findObjects(new FindListener<activity>() {
            @Override
            public void done(List<activity> list, BmobException e) {
                if(e==null){
                    if(list.size() > 0){
                        act.setAclassify_id(aclassify_id);
                        act.setActivity_description(activity_detail);
                        act.setActivity_position(activity_addr);
                        act.setActivity_starttime(starttime);
                        act.setActivity_host_id(userid);
                        act.setActivity_name(activity_name);
                        act.setActivity_person_num(activity_per_num);
                        act.setActivity_person_need(activity_per_need);
                        act.setHobby_id(hobby_id);
                        act.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e1) {
                                if(e1==null){
                                    act.setActivity_id(act.getObjectId());
                                    final BmobFile bmobFile=new BmobFile(new File(photoPath));
                                    act.setActivity_image_id(bmobFile);
                                    bmobFile.uploadblock(new UploadFileListener() {
                                        @Override
                                        public void done(BmobException e3) {
                                            if(e3==null){
                                                act.update(act.getObjectId(), new UpdateListener() {
                                                    @Override
                                                    public void done(BmobException e) {
                                                        if(e==null){
                                                            act.update(new UpdateListener() {
                                                                @Override
                                                                public void done(BmobException e2) {
                                                                    if(e2==null){
                                                                        Log.i("bmob","activityID修改成功，发布成功，id为" + act.getActivity_id());
                                                                        message.arg1 = 0;
                                                                        message.obj = act;
                                                                        handler.sendMessage(message);
                                                                    }
                                                                    else{
                                                                        System.out.println("activityID修改成功" + e2.getMessage()+","+e2.getErrorCode());
                                                                    }
                                                                }
                                                            });
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                    });


                                }else{
                                    message.arg1 = 1;
                                    Log.i("bmob","发布失败:" + e1.getMessage()+","+e1.getErrorCode());
                                    handler.sendMessage(message);
                                }
                            }
                        });
                    }
                }
            }
        });

    }
}
