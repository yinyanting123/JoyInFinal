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

import com.example.android.joyin.ActivityDetail.AcDetailItemActivity;
import com.example.android.joyin.Adapter.MineAllActivitiesAdapter;
import com.example.android.joyin.Asynctask.Add.GetActivityByHobbyId;
import com.example.android.joyin.Asynctask.hobby.GetAllHobbyList;
import com.example.android.joyin.MyApplication;
import com.example.android.joyin.R;
import com.example.android.joyin.entity.activity;
import com.example.android.joyin.entity.user_hobby;

import java.util.ArrayList;
import java.util.List;

import static cn.bmob.v3.Bmob.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class ActivityNow extends ListFragment {
    public static final String TAG="ActivityNow";

    private Handler handler,handler1,handler2;
    private MyApplication myApplication;
    private List<Number> hobby_ids = new ArrayList<Number>();
    private MineAllActivitiesAdapter mineAllActivitiesAdapter;
    private List<activity> RecommendActivitys;
    private List<activity>RecommendList = new ArrayList<activity>();
    private TextView MessageShow;

    private int Flag=0;
    public ActivityNow() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frag_activity_now, container, false);
    }
    public void onActivityCreated(Bundle savedInstanceState){
        MessageShow = (TextView)getActivity().findViewById(R.id.MessageShow);
        super.onActivityCreated(savedInstanceState);
        myApplication = (MyApplication)getApplicationContext();
        this.handler = new Handler(new GetHobbys());
        new GetAllHobbyList(handler,myApplication.getUser().getUser_id()).start();

    }
    public void onListItemClick(ListView parent, View v,
                                int position, long id) {
        Log.d(TAG, "onListItemClick===" + position);

        myApplication.setActivityNow(RecommendList.get(position));
        startActivity(new Intent(getApplicationContext(), AcDetailItemActivity.class));
    }
    private class  GetHobbys implements Handler.Callback{

        @Override
        public boolean handleMessage(Message message) {
            if(message.what==2){
                List<user_hobby> user_hobbies =(List<user_hobby>) message.obj;
                if(user_hobbies.size()>0)
                {
                    for (user_hobby u_h_item:user_hobbies
                            ) {
                        // hobby_ids.add(u_h_item.getHobby_id());

                        handler1=new Handler(new GetRecommendActivitys());
                        new GetActivityByHobbyId(u_h_item.getHobby_id(),handler1).start();
                        MessageShow.setVisibility(View.INVISIBLE);
                    }
                }else{
                    MessageShow.setVisibility(View.VISIBLE);
                }

            }
            return true;
        }
    }
    private class GetRecommendActivitys implements Handler.Callback{

        @Override
        public boolean handleMessage(Message message) {
            if(message.arg1==0){
                RecommendActivitys = (List<activity>)message.obj;
                if(RecommendActivitys.size()>0)
                    for (activity act:RecommendActivitys){
                        RecommendList.add(act);
                    }

                flushData(RecommendList);
                Log.i("RecommendList:",""+RecommendList);
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
