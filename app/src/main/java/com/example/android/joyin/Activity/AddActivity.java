package com.example.android.joyin.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.android.joyin.Asynctask.Add.CreateActivity;
import com.example.android.joyin.MyApplication;
import com.example.android.joyin.R;
import com.example.android.joyin.Tool.StringUtil;
import com.example.android.joyin.entity.User;
import com.example.android.joyin.entity.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import cn.bmob.v3.datatype.BmobDate;

import static com.example.android.joyin.Activity.PersonSettingsActivity.Change_User_Photo;

public class AddActivity extends AppCompatActivity {
    private Spinner spinner ;
    private EditText StartAddr;
    private EditText Detail;
    private EditText ActivityName;
    private EditText ActPerNum;
    private Button Pub,StartDate,StartTime;
    private ImageView Gallery;
    private String activity_name,activity_addr,activity_detail;
    private Number activity_per_num,aclassify,activity_per_need,hobby_id;
    private int  mYear, mMonth, mDay, mHour, mMinute, person;
    private String startDate="", startTime="";
    private Calendar calendar;
    private Handler handler;
    private activity act;
    private MyApplication myApplication;
    private User user;
    private Uri updated_user_photo;
    //从本地文件夹获取图片
    public final static int Activity_Picture=1;
    private String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        myApplication = (MyApplication) getApplicationContext() ;
        user = myApplication.getUser();
        spinner = (Spinner)findViewById(R.id.Classify);
        StartAddr = (EditText)findViewById(R.id.StartAddr);
        Detail = (EditText)findViewById(R.id.Detail);
        ActivityName = (EditText)findViewById(R.id.ActivityName);
        ActPerNum = (EditText)findViewById(R.id.ActPerNum);
        Pub = (Button)findViewById(R.id.Pub);
        StartDate = (Button)findViewById(R.id.btn_StartDate);
        StartTime = (Button)findViewById(R.id.btn_StartTime);
        Gallery = (ImageView)findViewById(R.id.Gallery);
        calendar = Calendar.getInstance();
        Gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,Activity_Picture);
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                String[] Classify = getResources().getStringArray(R.array.Classify);
                aclassify = (Number) (pos+1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        StartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        // TODO Auto-generated method stub
                        mYear = year;
                        mMonth = month;
                        mDay = day;

                        StringBuilder sb = new StringBuilder().append(mYear).append("-")
                                .append((mMonth + 1) < 10 ? 0 + (mMonth + 1) : (mMonth + 1))
                                .append("-")
                                .append((mDay < 10) ? 0 + mDay : mDay);
                        startDate = sb.toString();
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH) ).show();
            }
        });
        StartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(AddActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {
                        // TODO Auto-generated method stub
                        mHour = hour;
                        mMinute = minute;

                        StringBuilder sb = new StringBuilder()
                                .append(mHour < 10 ? 0 + mHour : mHour).append(":")
                                .append(mMinute < 10 ? 0 + mMinute : mMinute).append(":00") ;
                        startTime = sb.toString();
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE), true).show();
            }
        });
        Pub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if("".equals(startDate) || "".equals(startTime)) {
                    Toast.makeText(getApplicationContext(), "请选择完整的开始使用时间！", Toast.LENGTH_SHORT).show();
                }else{
                    person  = Integer.parseInt(ActPerNum.getText().toString());
                    activity_per_num = (Number)person;
                    activity_per_need = (Number)(person-1);
                    activity_name=ActivityName.getText().toString();
                    activity_addr=StartAddr.getText().toString();
                    activity_detail=Detail.getText().toString();
                    hobby_id = aclassify;
                    handler = new Handler(new CreateActivityCallBack());
                    try {
                        new CreateActivity(path,activity_name,activity_addr,activity_detail,activity_per_num,activity_per_need,aclassify,hobby_id,user.getUser_id(), new BmobDate(sdf.parse(startDate + " " + startTime)),  handler).start();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                Intent intent = new Intent(AddActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });
    }
    private class CreateActivityCallBack implements Handler.Callback{

        @Override
        public boolean handleMessage(Message msg) {
            if(msg.arg1 == 0){
                Toast.makeText(getApplicationContext(), "发布成功！", Toast.LENGTH_SHORT).show();
//                responseText = (String) msg.obj;
            }
            return true;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Change_User_Photo){
            if(resultCode == RESULT_OK){
                if (data != null) {
                    Gallery.setImageURI(data.getData());
                    updated_user_photo=data.getData();
                    path= StringUtil.getRealPathFromURI(getApplicationContext(), updated_user_photo);
                    Toast.makeText(getApplicationContext(), path, Toast.LENGTH_LONG).show();

                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
