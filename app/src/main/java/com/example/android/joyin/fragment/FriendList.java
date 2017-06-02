package com.example.android.joyin.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.android.joyin.Adapter.TopicAdapter;
import com.example.android.joyin.MyApplication;
import com.example.android.joyin.R;
import com.example.android.joyin.Topic.TopicContent;

import static cn.bmob.v3.Bmob.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class FriendList extends ListFragment {
    private static final String TAG = "FriendList";
    MyApplication myApplication;
    TopicAdapter topicAdapter;
    String[] allHobbyName={"交友联谊","发现美食","室内精彩","智慧碰撞","户外high起","其他一切"};

    public FriendList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frag_friend_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myApplication=(MyApplication)getApplicationContext();

        flushData();

    }


    public void onListItemClick(ListView parent, View v,
                                int position, long id) {
        Log.d(TAG, "onListItemClick===" + position);
        Intent intent = new Intent(getApplicationContext(), TopicContent.class);
        intent.putExtra("topic_id", position);
        startActivity(intent);
    }

    public void flushData(){
        Log.d(TAG, "onListItemClick===" + "flushData");

        myApplication=(MyApplication)getApplicationContext();
        topicAdapter=new TopicAdapter(getApplicationContext(),allHobbyName);
         this.setListAdapter(topicAdapter);
        topicAdapter.notifyDataSetChanged();
    }

    }
