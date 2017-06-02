package com.example.android.joyin.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.android.joyin.R;
import com.example.android.joyin.entity.TopicMessage;

import java.util.List;


/**
 * Created by Mr yin on 2017/5/25.
 */

public class TopicContentAdapter extends BaseAdapter {
    private static final String TAG = "TopicContentAdapter";

    Context context;
    List<TopicMessage> topicMessageList;
    LayoutInflater layout;
    TextView user_id;
    TextView topic_message;

    public TopicContentAdapter(Context context, List<TopicMessage> topicMessageList){
        Log.d(TAG, "create view success===="+topicMessageList.size());
        this.topicMessageList=topicMessageList;
        this.context = context;
        this.layout = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return topicMessageList.size();
    }

    @Override
    public Object getItem(int i) {
        return topicMessageList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "get view success");
        convertView = layout.inflate(R.layout.topic_content_item,null);
        user_id=(TextView) convertView.findViewById(R.id.topic_content_item_userid);
        user_id.setText(topicMessageList.get(position).getUser_id());
        topic_message=(TextView)convertView.findViewById(R.id.topic_content_item_message);
        topic_message.setText(topicMessageList.get(position).getMessage_content());
        Log.d(TAG, topicMessageList.get(position).getTopic_id().toString());
        return convertView;
    }
}
