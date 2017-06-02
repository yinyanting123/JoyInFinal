package com.example.android.joyin.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.joyin.ActivityDetail.AcDetailItemActivity;
import com.example.android.joyin.Adapter.MineAllActivitiesAdapter;
import com.example.android.joyin.Asynctask.activityList.GetActivityByUserid;
import com.example.android.joyin.Asynctask.activityList.GetAllActivityList;
import com.example.android.joyin.MyApplication;
import com.example.android.joyin.R;
import com.example.android.joyin.entity.activity;
import com.example.android.joyin.entity.user_activity;

import java.util.ArrayList;
import java.util.List;

import static cn.bmob.v3.Bmob.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainMenu extends ListFragment {
    private static final String TAG = "ListFragmentImpl";

    //游加
    private TextView btnAllActivities;
    private TextView btnMine;
//    private String userid = "1";
    private Handler handler;

    private List<activity> AllActivities;
    private List<activity> MyActivities=new ArrayList<activity>();
    private ListView List;
    MineAllActivitiesAdapter mineAllActivitiesAdapter;
    boolean nowClicked=false;//为FALSE代表当前为显示全部活动，为TRUE代表显示我的活动

    MyApplication myApplication;

    public MainMenu() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.frag_main_menu, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //游加
        btnAllActivities = (TextView) getActivity().findViewById(R.id.all_activity);
        btnMine = (TextView) getActivity().findViewById(R.id.mine);

        this.handler=new Handler(new GetActivityHandler());
        new GetAllActivityList(handler).start();

        btnAllActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_LONG).show();
                //需要获取全部活动列表
               new GetAllActivityList(handler).start();
            }
        });

        btnMine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //需要获取我的活动列表
                Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_LONG).show();
                new GetActivityByUserid(handler,myApplication.getUser().getUser_id()).start();
                flushData(MyActivities);
            }
        });
    }

    public void onListItemClick(ListView parent, View v,
                                int position, long id) {
        Log.d(TAG, "onListItemClick==="+ position);
        if(nowClicked){
            myApplication.setActivityNow(MyActivities.get(position));
            startActivity(new Intent(getApplicationContext(), AcDetailItemActivity.class));
        }else{
            myApplication.setActivityNow(AllActivities.get(position));
            startActivity(new Intent(getApplicationContext(), AcDetailItemActivity.class));
        }



        /*Toast.makeText(getActivity(),
                "You have selected " + position,
                Toast.LENGTH_SHORT).show();*/
    }
    private  class GetActivityHandler implements Handler.Callback{

        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 0) {
                AllActivities = (List<activity>) msg.obj;
                if(AllActivities!=null) {
                    Log.d("activitySize", AllActivities.size()+"");
                    //需要加载列表
                    flushData(AllActivities);
                    new GetActivityByUserid(handler,myApplication.getUser().getUser_id()).start();

                }else{
                    Toast.makeText(getApplicationContext(), "size<0", Toast.LENGTH_LONG).show();
                }
            }else if(msg.what == 1){
                Toast.makeText(getApplicationContext(), "获取全部活动列表失败", Toast.LENGTH_LONG).show();
            }else if(msg.what == 2){
                Toast.makeText(getApplicationContext(), "获取我的活动列表成功", Toast.LENGTH_LONG).show();
                //需要根据列表中的activityID来填充myactivitylist
                nowClicked = true;
                List<user_activity> us_ac=(List<user_activity>)msg.obj;
                Log.d(TAG, "handleMessage: "+us_ac.size());
                for (user_activity usacitem:us_ac
                     ) {
                    String activity_id=usacitem.getActivity_id();
                    Log.d(TAG, "activity_id: "+activity_id);

                    for (activity acitem:AllActivities
                            ) {
                        Log.d(TAG, "activity_id,all: "+acitem.getActivity_id());
                        if(acitem.getActivity_id().equals(activity_id)){

                            MyActivities.add(acitem);
                            Log.d(TAG, "MyActivities,size: "+MyActivities.size());
                            break;
                        }
                    }
                }
                myApplication.setMyActivities(MyActivities);
//                flushData(MyActivities);
            }else if(msg.what == 3){
                Toast.makeText(getApplicationContext(), "获取我的活动列表失败", Toast.LENGTH_LONG).show();
            }
            return true;
        }
    }

    public void flushData(List<activity> Activities){
        myApplication=(MyApplication)getApplicationContext();
        mineAllActivitiesAdapter=
                new MineAllActivitiesAdapter(getApplicationContext(),Activities);
        this.setListAdapter(mineAllActivitiesAdapter);
        mineAllActivitiesAdapter.notifyDataSetChanged();
    }

}
