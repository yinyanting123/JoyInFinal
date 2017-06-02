package com.example.android.joyin.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ListFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.joyin.ActivityDetail.AcDetailItemActivity;
import com.example.android.joyin.Adapter.myFragmentPagerAdapter;
import com.example.android.joyin.Asynctask.hobby.DeleteUserHobby;
import com.example.android.joyin.Asynctask.hobby.GetAllHobbyList;
import com.example.android.joyin.Asynctask.hobby.UpdateUserHobby;
import com.example.android.joyin.MyApplication;
import com.example.android.joyin.R;
import com.example.android.joyin.Tool.StringUtil;
import com.example.android.joyin.entity.activity;
import com.example.android.joyin.entity.user_hobby;
import com.example.android.joyin.fragment.ActivityNow;
import com.example.android.joyin.fragment.FriendList;
import com.example.android.joyin.fragment.MainMenu;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.datatype.BmobDate;

import static com.example.android.joyin.R.id.activity_now;

//import static com.tencent.qalsdk.base.a.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";
    /*fragment变量*/
    private ViewPager mPager;
    private RadioGroup mGroup;
    private RadioButton mainMenu, activityNow, friendList;
    private ArrayList<ListFragment> fragmentList;

    //游
    //NavigationView的变量
    private ImageView imageView;
    private TextView navName;
    private TextView navAccount;
    private NavigationView navigationView;
    private View headerView;
    Handler handler1;


    public static final String[] allHobbyName={"交友联谊","发现美食","室内精彩","智慧碰撞","户外high起","其他一切"};
    final boolean b[]=new boolean[]{false,false,false,false,false,false};
    final List<user_hobby> user_hobby_add=new ArrayList<user_hobby>();
    private Handler handler;
    private MyApplication myApplication;
    private BmobDate activity_starttime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myApplication=(MyApplication)getApplicationContext();
        navigationView = (NavigationView)findViewById(R.id.nav_view);
        headerView = navigationView.getHeaderView(0);

        this.handler1=new Handler((new UploadHandler()));
        //获取内容
        imageView = (ImageView)headerView.findViewById(R.id.nav_img);
        navName  = (TextView)headerView.findViewById(R.id.nav_name);
        navAccount = (TextView)headerView.findViewById(R.id.nav_account);

        navName.setText(myApplication.getUser().getUser_name());
        navAccount.setText(myApplication.getUser().getUser_telephone());
        //imageView.setImageURI();
        //获取头像
        //new DownloadPhoto(myApplication.getUser().getObjectId(),handler1).start();

        //悬浮按钮用于直接添加活动
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        /*对fragment进行初始化*/
        //初始化界面组件
        initView();
        //初始化ViewPager
        initViewPager();

        handler=new Handler(new GetActivityHandler());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    //在这里添加右上角的响应事件
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
      public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_gallery) {//历史活动
            startActivity(new Intent(getApplicationContext(),ActivityHistory.class));
        } else if (id == R.id.nav_current) {//当前活动
            List<activity> myActivities=myApplication.getMyActivities();
            activity activity_now;
            boolean hasNowActivity=false;
            for (activity ac:myActivities
                 ) {
                activity_starttime = ac.getActivity_starttime();
                String ac_time=activity_starttime.getDate();
                Date date = StringUtil.StringToTimestamp(ac_time);
                if(new Date().before(date)){
                    hasNowActivity=true;
                    myApplication.setActivityNow(ac);
                }
            }
            if(hasNowActivity)
                 startActivity(new Intent(getApplicationContext(), AcDetailItemActivity.class));
            else
                Toast.makeText(getApplicationContext(),"您当前暂时无活动",Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_settings) {//修改个人设置
            startActivity(new Intent(getApplicationContext(),PersonSettingsActivity.class));
        }else if (id == R.id.nav_hobby) {//兴趣爱好
            new GetAllHobbyList(handler,myApplication.getUser().getUser_id()).start();
        }else if (id == R.id.nav_share) {//聊天室

        }else if (id == R.id.nav_send) {//聊天室

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*fragment具体操作*/
    private void initView(){
        mPager=(ViewPager)findViewById(R.id.viewPager);
        mGroup=(RadioGroup)findViewById(R.id.radiogroup);
        mainMenu =(RadioButton)findViewById(R.id.main_menu);
        activityNow =(RadioButton)findViewById(activity_now);
        friendList =(RadioButton)findViewById(R.id.friend_list);
        //RadioGroup选中状态改变监听
        mGroup.setOnCheckedChangeListener(new myCheckChangeListener());
    }

    private void initViewPager(){
        MainMenu mainMenu =new MainMenu();
        ActivityNow activityNow =new ActivityNow();
        FriendList friendList =new FriendList();
        fragmentList=new ArrayList<ListFragment>();
        fragmentList.add(mainMenu);
        fragmentList.add(activityNow);
        fragmentList.add(friendList);
        //ViewPager设置适配器
        mPager.setAdapter(new myFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
        //ViewPager显示第一个Fragment
        mPager.setCurrentItem(0);
        //ViewPager页面切换监听
        mPager.setOnPageChangeListener(new myOnPageChangeListener());
    }

    /**
     *RadioButton切换Fragment
     */
    private class myCheckChangeListener implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.main_menu:
                    //ViewPager显示第一个Fragment且关闭页面切换动画效果
                    mPager.setCurrentItem(0,false);
                    break;
                case activity_now:
                    mPager.setCurrentItem(1,false);
                    break;
                case R.id.friend_list:
                    mPager.setCurrentItem(2,false);
                    break;
            }
        }
    }

    /**
     *ViewPager切换Fragment,RadioGroup做相应变化
     */
    private class myOnPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position){
                case 0:
                    mGroup.check(R.id.main_menu);
                    break;
                case 1:
                    mGroup.check(activity_now);
                    break;
                case 2:
                    mGroup.check(R.id.friend_list);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


    //
    private  class GetActivityHandler implements Handler.Callback{

        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 0) {
                Toast.makeText(getApplicationContext(),"已更新您的兴趣爱好",Toast.LENGTH_SHORT).show();
            }else if(msg.what == 1){
                Toast.makeText(getApplicationContext(),"未能成功更新，请稍后再试",Toast.LENGTH_SHORT).show();
            }else if(msg.what == 2){
                //成功获取用户爱好
                List<user_hobby> u_h_list=(List<user_hobby>)msg.obj;
                for (user_hobby u_h_item:u_h_list
                     ) {
                    b[u_h_item.getHobby_id().intValue()]=true;
                }
                selectHobby(u_h_list);
            }else if(msg.what == 3){
                Log.d(TAG, "handleMessage: 未能获取用户hobby");
            }else if(msg.what == 4){
                Log.d(TAG, "handleMessage: 成功删除用户之前的hobby");
                new UpdateUserHobby(handler,user_hobby_add).start();
            }else if(msg.what == 5){
                Log.d(TAG, "handleMessage: 未能成功删除用户之前的hobby");
            }
            return true;
        }
    }

    public  void selectHobby(final List<user_hobby> u_h_list){
        final String userid=myApplication.getUser().getUser_id();
        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("修改兴趣爱好").setIcon(R.drawable.icon)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //不做任何处理
                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        for(int i=0;i<b.length;i++){
                            if(b[i]==true){
                                user_hobby u_h=new user_hobby();
                                u_h.setUser_id(userid);
                                u_h.setHobby_id(i);
                                user_hobby_add.add(u_h);
                            }
                        }
                        //先删除之前所有的，再添加新的
                        new DeleteUserHobby(handler,u_h_list).start();
                    }
                })
                .setMultiChoiceItems(allHobbyName, b, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        //update用户的兴趣爱好
                       /* if(b[which]==true)
                            b[which]=false;
                        else
                            b[which]=true;*/
                        b[which]=isChecked;

                    }
                }).create();
        dialog.show();
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
                imageView.setImageURI(photo_uri);
            }else if(msg.what == 4){
                Toast.makeText(getApplicationContext(), "图片下载失败", Toast.LENGTH_LONG).show();
            }else if(msg.what == 5){
                Toast.makeText(getApplicationContext(), "获取属性失败", Toast.LENGTH_LONG).show();
            }
            return true;
        }
    }

}
