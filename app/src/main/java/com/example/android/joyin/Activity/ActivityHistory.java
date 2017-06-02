package com.example.android.joyin.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.joyin.ActivityDetail.AcDetailItemActivity;
import com.example.android.joyin.Adapter.MineAllActivitiesAdapter;
import com.example.android.joyin.MyApplication;
import com.example.android.joyin.R;
import com.example.android.joyin.Tool.StringUtil;
import com.example.android.joyin.entity.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivityHistory extends AppCompatActivity {
    private List<activity> ActivityHistory=new ArrayList<activity>();
    ListView historyActivities;
    MyApplication myApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        myApplication=(MyApplication)getApplicationContext();
        historyActivities=(ListView)findViewById(R.id.historyAtivities) ;
        initData();
        flushData();
        historyActivities.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                myApplication.setActivityNow(ActivityHistory.get(position));
                startActivity(new Intent(getApplicationContext(), AcDetailItemActivity.class));
            }
        });

    }

    private void initData(){
        List<activity> myActivities=myApplication.getMyActivities();
        for (activity ac:myActivities
                ) {
            String ac_time=ac.getActivity_starttime().getDate();
            Date date = StringUtil.StringToTimestamp(ac_time);
            if(new Date().after(date)){
                ActivityHistory.add(ac);
            }
        }
    }

    private void flushData(){
        MineAllActivitiesAdapter mineAllActivitiesAdapter = new MineAllActivitiesAdapter(getApplicationContext(), ActivityHistory);
        historyActivities.setAdapter(mineAllActivitiesAdapter);
        mineAllActivitiesAdapter.notifyDataSetChanged();
    }

}
