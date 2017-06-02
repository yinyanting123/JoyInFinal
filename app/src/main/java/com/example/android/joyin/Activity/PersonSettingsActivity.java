package com.example.android.joyin.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.joyin.Asynctask.photo.DownloadPhoto;
import com.example.android.joyin.Asynctask.photo.UploadPhoto;
import com.example.android.joyin.MyApplication;
import com.example.android.joyin.Persetting.PersonSettingsAddressActivity;
import com.example.android.joyin.Persetting.PersonSettingsGenderActivity;
import com.example.android.joyin.Persetting.PersonSettingsNicknameActivity;
import com.example.android.joyin.Persetting.PersonSettingsSignatureActivity;
import com.example.android.joyin.R;
import com.example.android.joyin.Tool.StringUtil;
import com.example.android.joyin.entity.User;

import java.io.File;


public class PersonSettingsActivity extends AppCompatActivity {

    public ImageView personPhoto;
    private Uri updated_user_photo;
    //从本地文件夹获取图片
    public final static int Change_User_Photo=1;
    MyApplication myApplication;
    //用于上传图片
    User user;
    String userObjectId;
    Handler handler;

    private TextView personSettingNickname;
    private TextView personSettingGender;
    private TextView personSettingAddress;
    private TextView personSettingSignature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_settings);

        myApplication = (MyApplication) getApplication();

        this.handler=new Handler((new UploadHandler()));
        personPhoto=(ImageView)findViewById(R.id.person_setting_img);
        personSettingNickname = (TextView)findViewById(R.id.person_setting_nickname);
        personSettingGender = (TextView)findViewById(R.id.person_setting_gender);
        personSettingAddress = (TextView)findViewById(R.id.person_setting_address);
        personSettingSignature = (TextView)findViewById(R.id.person_setting_signature);

        //获取各内容
        personSettingNickname.setText(myApplication.getUser().getUser_name());
        personSettingGender.setText(myApplication.getUser().getGender());
        personSettingAddress.setText(myApplication.getUser().getUser_address());
        personSettingSignature.setText(myApplication.getUser().getUser_signature());

        //获取头像
        new DownloadPhoto(myApplication.getUser().getObjectId(),handler).start();

     personPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //startActivityForResult(intent,Change_User_Photo);
            }
        });


    }



    public void changeNickname(View view){
        //创建Intent
        Intent intent = new Intent(PersonSettingsActivity.this, PersonSettingsNicknameActivity.class);

        //启动Activity
        startActivity(intent);
    }

    public void changeGender(View view){
        //创建Intent
        Intent intent = new Intent(PersonSettingsActivity.this, PersonSettingsGenderActivity.class);

        //启动Activity
        startActivity(intent);
    }

    public void changeAddress(View view){
        //创建Intent
        Intent intent = new Intent(PersonSettingsActivity.this, PersonSettingsAddressActivity.class);

        //启动Activity
        startActivity(intent);
    }

    public void changeSignature(View view){
        //创建Intent
        Intent intent = new Intent(PersonSettingsActivity.this, PersonSettingsSignatureActivity.class);

        //启动Activity
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Change_User_Photo){
            if(resultCode == RESULT_OK){
                if (data != null) {
                    personPhoto.setImageURI(data.getData());
                    updated_user_photo=data.getData();
                    String path= StringUtil.getRealPathFromURI(getApplicationContext(), updated_user_photo);
                    Toast.makeText(getApplicationContext(), path, Toast.LENGTH_LONG).show();
                    new UploadPhoto(myApplication.getUser().getObjectId(),path,handler).start();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private  class UploadHandler implements Handler.Callback{

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
                personPhoto.setImageURI(photo_uri);
            }else if(msg.what == 4){
                Toast.makeText(getApplicationContext(), "图片下载失败", Toast.LENGTH_LONG).show();
            }else if(msg.what == 5){
                Toast.makeText(getApplicationContext(), "获取属性失败", Toast.LENGTH_LONG).show();
            }
            return true;
        }
    }

}
