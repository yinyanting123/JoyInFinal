package com.example.android.joyin.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.android.joyin.R;
import com.example.android.joyin.Tool.SpecialTextView;
import com.example.android.joyin.entity.activity;

import java.util.List;

/**
 * Created by Administrator on 2017/5/25.
 */

public class RecommendActivityAdapter extends BaseAdapter {
    public static final String TAG="RecommendAdapter" ;

    Context context;
    List<activity> data;
    LayoutInflater layout;

    ImageView recommend_activity_background_image,recommend_activity_person_image;
    SpecialTextView recommend_activity_title,recommend_activity_requirement_time,recommend_activity_requirement_address;
    SpecialTextView recommend_activity_requirement_sum, recommend_activity_requirement_need;

    int sreenHeight;
    public final static int PART=4;//item占屏幕高度的配比

    public RecommendActivityAdapter(Context context, List<activity>  data, int sreenHeight){
        Log.d(TAG, "create: ");
        this.context = context;
        this.data = data;
        this.layout = LayoutInflater.from(context);
        this.sreenHeight=sreenHeight;
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Log.d(TAG, "getView: "+i);
        view = layout.inflate(R.layout.activity_now_item,null);
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) view.getLayoutParams();
        recommend_activity_background_image = (ImageView)view.findViewById(R.id.recommend_activity_background_image);
        recommend_activity_person_image = (ImageView)view.findViewById(R.id.recommend_activity_person_image);
        recommend_activity_title = (SpecialTextView)view.findViewById(R.id.recommend_activity_title);
        recommend_activity_requirement_time = (SpecialTextView)view.findViewById(R.id.recommend_activity_requirement_time);
        recommend_activity_requirement_address = (SpecialTextView)view.findViewById(R.id.recommend_activity_requirement_address);
        recommend_activity_requirement_sum = (SpecialTextView)view.findViewById(R.id.recommend_activity_requirement_sum);
        recommend_activity_requirement_need = (SpecialTextView)view.findViewById(R.id.recommend_activity_requirement_need);

        activity actRecommend = data.get(i);
        Log.i("Recommend:",actRecommend.getActivity_name());
        recommend_activity_title.setText(actRecommend.getActivity_name());
        Log.d(TAG, actRecommend.getActivity_name());
        recommend_activity_requirement_time.setText(actRecommend.getActivity_starttime().getDate());
        Log.d(TAG, actRecommend.getActivity_starttime().getDate());
        recommend_activity_requirement_address.setText(actRecommend.getActivity_position());
        Log.d(TAG, actRecommend.getActivity_position());
        recommend_activity_requirement_sum.setText("sum"+actRecommend.getActivity_person_num().intValue()+"人");
        Log.d(TAG, ""+actRecommend.getActivity_person_num().intValue());
        //recommend_activity_requirement_need.setText("need:"+actRecommend.getActivity_person_need().intValue()+"人");

        return view;
    }
}
