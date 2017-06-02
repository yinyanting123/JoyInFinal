package com.example.android.joyin.ActivityDetail;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.joyin.Activity.MainActivity;
import com.example.android.joyin.Asynctask.Add.CreateUserActivity;
import com.example.android.joyin.Asynctask.Add.GetUserById;
import com.example.android.joyin.Asynctask.Add.JoinActivity;
import com.example.android.joyin.MyApplication;
import com.example.android.joyin.R;
import com.example.android.joyin.entity.User;
import com.example.android.joyin.entity.activity;



public class AcDetailItemActivity extends AppCompatActivity {

    public ImageView activityPicture;
    public TextView activityTitle,host_name;
    public TextView activityTime,activityAddr;
    public TextView act_per_sum,per_need_num;
    public TextView activityDetail;
    private Button Join;
    private MyApplication myApplication;
    private activity act;
    private User host,user;
    private Handler handler,handler1,handler2;
    private String userid;
    private Number act_per_need;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_ac_detail_item);
        activityPicture = (ImageView)findViewById(R.id.activityPicture);
        activityTitle = (TextView)findViewById(R.id.activityTitle);
        host_name = (TextView)findViewById(R.id.host_name);
        activityTime = (TextView)findViewById(R.id.activityTime);
        activityAddr = (TextView)findViewById(R.id.activityAddr);
        act_per_sum = (TextView)findViewById(R.id.act_per_sum);
        per_need_num = (TextView)findViewById(R.id.per_need_num);
        activityDetail = (TextView)findViewById(R.id.activityDetail);
        Join = (Button)findViewById(R.id.Join);

        myApplication = (MyApplication)getApplicationContext();
        act = myApplication.getActivityNow();
        user = myApplication.getUser();

        /*this.handler=new Handler((new UploadHandler()));
        new DownloadPhoto(act.getObjectId(),handler).start();*/

        activityTitle.setText(act.getActivity_name());
        activityTime.setText(act.getActivity_starttime().getDate());
        activityAddr.setText(act.getActivity_position());
        act_per_sum.setText(act.getActivity_person_num().toString());
        per_need_num.setText("目前还需"+act.getActivity_person_need()+"人");
        activityDetail.setText(act.getActivity_description());

        userid=act.getActivity_host_id();
        this.handler1 = new Handler(new GetHost());
        new GetUserById(userid,handler1).start();

        Join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user.getUser_id()==userid){
                    Toast.makeText(getApplicationContext(), "您举办的这次活动，无需再次参加！", Toast.LENGTH_LONG).show();
                }else if(act.getActivity_person_need().intValue()>0){
                    Toast.makeText(getApplicationContext(), "JoyIn!!!!!", Toast.LENGTH_LONG).show();
                    handler2 = new Handler(new Join());
                    new CreateUserActivity(user.getUser_id(),act.getActivity_id(),handler2).start();
                    act_per_need = act.getActivity_person_need().intValue() - 1;
                }else{
                    Toast.makeText(getApplicationContext(), "当前活动人数已满！", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
    private class GetHost implements Handler.Callback{
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 0) {
                host =  (User)msg.obj;
                host_name.setText(host.getUser_name());
            }
            return true;
        }
    }
    private class  Join implements Handler.Callback{
        Handler handler3;
        @Override
        public boolean handleMessage(Message message) {
            if(message.arg1==0){
                this.handler3 = new Handler(new JoyIn());
                new JoinActivity(act.getObjectId(),act_per_need,handler3).start();
            }
            else{
                Toast.makeText(getApplicationContext(), "您已参加该活动，无需再次参加！", Toast.LENGTH_LONG).show();
            }
            return true;
        }
    }
    private class  JoyIn implements Handler.Callback{
        @Override
        public boolean handleMessage(Message message) {
            if(message.arg1==0){
                Toast.makeText(getApplicationContext(), "参加成功", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AcDetailItemActivity.this, MainActivity.class);
                startActivity(intent);
            }
            return true;
        }
    }
 /*   private  class UploadHandler implements Handler.Callback{

        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 0) {
                Toast.makeText(getApplicationContext(), "图片存储成功", Toast.LENGTH_LONG).show();
                // personPhoto.setImageURI(updated_user_photo);
            }else if(msg.what == 1){
                Toast.makeText(getApplicationContext(), "图片存储失败", Toast.LENGTH_LONG).show();
            }else if(msg.what == 2){
                Toast.makeText(getApplicationContext(), "文件上传失败", Toast.LENGTH_LONG).show();
            }else if(msg.what == 3){
                Toast.makeText(getApplicationContext(), "下载成功", Toast.LENGTH_LONG).show();
                String image_path=(String)msg.obj;
                Uri photo_uri=Uri.fromFile(new File(image_path));
                activityPicture.setImageURI(photo_uri);
            }else if(msg.what == 4){
                Toast.makeText(getApplicationContext(), "图片下载失败", Toast.LENGTH_LONG).show();
            }else if(msg.what == 5){
                Toast.makeText(getApplicationContext(), "获取属性失败", Toast.LENGTH_LONG).show();
            }
            return true;
        }
    }*/
}
