package com.example.android.joyin.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.android.joyin.R;

/**
 * Created by Mr yin on 2017/5/24.
 */

public class TopicAdapter extends BaseAdapter {
    private static final String TAG = "TopicAdapter";

    Context context;
    String[] data;
    LayoutInflater layout;
    TextView topic_name;

    public TopicAdapter(Context context, String[] data){
        Log.d(TAG, "create view success===="+data[0]);

        this.context = context;
        this.data = data;
        this.layout = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public String getItem(int i) {
        return data[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "get view success");
        convertView = layout.inflate(R.layout.topic_item,null);
        topic_name=(TextView) convertView.findViewById(R.id.topic_name);
        topic_name.setText(data[position]);
        Log.d(TAG, data[position]);
        return convertView;
    }
}
