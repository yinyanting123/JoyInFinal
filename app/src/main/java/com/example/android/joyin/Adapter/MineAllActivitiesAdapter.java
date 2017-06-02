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
 * Created by MAC on 2017/5/4.
 */

public class MineAllActivitiesAdapter extends BaseAdapter {

    Context context;
    List<activity> data;
    LayoutInflater layout;

    ImageView activity_background_image ;
    SpecialTextView activity_title;
    ImageView activity_person_image ;
    SpecialTextView activity_requirement_time ;
    SpecialTextView activity_requirement_sum ;
    SpecialTextView activity_requirement_need ;
    SpecialTextView activity_requirement_address ;

    int sreenHeight;
    public final static int PART=4;//item占屏幕高度的配比


    public MineAllActivitiesAdapter(Context context, List<activity>  data){
        Log.d("create", "create view success===="+data.size());

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
        Log.d("getView", "get view success");

        //获取项目布局
        /*View mineAllActivities = view;
        if(mineAllActivities==null)
            mineAllActivities = layout.inflate(R.layout.activity_ac_detail_item,null);
       
        //获取布局中的各组件
        /*ImageView activity_background_image = (ImageView)mineAllActivities.findViewById(R.id.activity_background_image);
        TextView activity_title = (TextView)mineAllActivities.findViewById(R.id.activity_title);
        ImageView activity_person_image = (ImageView)mineAllActivities.findViewById(R.id.activity_person_image);
        TextView activity_requirement_time = (TextView)mineAllActivities.findViewById(R.id.activity_requirement_time);
        TextView activity_requirement_sum = (TextView)mineAllActivities.findViewById(R.id.activity_requirement_sum);
        TextView activity_requirement_need = (TextView)mineAllActivities.findViewById(R.id.activity_requirement_need);
        TextView activity_requirement_address = (TextView)mineAllActivities.findViewById(R.id.activity_requirement_address);
*/
        view = layout.inflate(R.layout.main_menu_item,null);
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) view.getLayoutParams();
        // 取控件aaa当前的布局参数
        //linearParams.height = sreenHeight/PART;        // 当控件的高强制设成365象素
       // view.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件aaa
        activity_background_image = (ImageView)view.findViewById(R.id.ymain_activity_background_image);
        //activity_background_image.setMaxHeight(sreenHeight/PART);
        activity_title = (SpecialTextView) view.findViewById(R.id.ymain_activity_title);
        activity_person_image = (ImageView)view.findViewById(R.id.ymain_activity_person_image);
        activity_requirement_time = (SpecialTextView)view.findViewById(R.id.ymain_activity_requirement_time);
        activity_requirement_sum = (SpecialTextView)view.findViewById(R.id.ymain_activity_requirement_sum);
        activity_requirement_need = (SpecialTextView)view.findViewById(R.id.ymain_activity_requirement_need);
        activity_requirement_address = (SpecialTextView)view.findViewById(R.id.ymain_activity_requirement_address);

        //填充数据
        activity mineAll = data.get(i);
        Log.d("activityId", mineAll.getActivity_id()+"");
        activity_title.setText(mineAll.getActivity_name());
        Log.d("activityName", mineAll.getActivity_name());
//        activity_requirement_time.setText(mineAll.getActivity_starttime().toString());
        activity_requirement_time.setText(mineAll.getActivity_starttime().getDate());
        Log.d("activityStartTime", mineAll.getActivity_starttime().getDate());
        activity_requirement_sum.setText("sum"+mineAll.getActivity_person_num().intValue()+"人");
        Log.d("activityPersonNumber", mineAll.getActivity_person_num().toString());
        activity_requirement_address.setText(mineAll.getActivity_position());
        activity_requirement_need.setText("need:"+mineAll.getActivity_person_need().intValue()+"人");
        Log.d("activityPosition", mineAll.getActivity_position());

        if(mineAll.getAclassify_id().intValue() == 1){
            activity_background_image.setImageResource(R.drawable.basket);
        }else if(mineAll.getAclassify_id().intValue() == 2){
            activity_background_image.setImageResource(R.drawable.football);
        }else if(mineAll.getAclassify_id().intValue() == 3){
            activity_background_image.setImageResource(R.drawable.pingpang);
        }else if(mineAll.getAclassify_id().intValue() == 4){
            activity_background_image.setImageResource(R.drawable.meeting);
        }else if(mineAll.getAclassify_id().intValue() == 5){
            activity_background_image.setImageResource(R.drawable.music);
        }else if(mineAll.getAclassify_id().intValue() == 6){
            activity_background_image.setImageResource(R.drawable.running);
        }else if(mineAll.getAclassify_id().intValue() == 7){
            activity_background_image.setImageResource(R.drawable.dating);
        }
        return view;
    }
}
