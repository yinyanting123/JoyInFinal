package com.example.android.joyin.Topic;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.android.joyin.Adapter.TopicContentAdapter;
import com.example.android.joyin.Asynctask.topic.GetTopicAllMessage;
import com.example.android.joyin.R;
import com.example.android.joyin.entity.TopicMessage;

import java.util.List;

public class TopicContent extends AppCompatActivity {

    public  static final String TAG="TopicContent";

    Handler handler;
    int topic_id;
    List<TopicMessage> TopicMessageList;
    ListView TopicContent;
    TopicContentAdapter topicContentAdapter;
    Button AddMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_content);
        topic_id=getIntent().getIntExtra("topic_id", 0);
        TopicContent=(ListView)findViewById(R.id.topic_content_list);
        AddMessage=(Button)findViewById(R.id.add_message);
        handler=new Handler(new GetTopicContent());
        new GetTopicAllMessage(topic_id,handler).start();
        AddMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SetMessage.class);
                intent.putExtra("topic_id", topic_id);
                startActivity(intent);
            }
        });
    }

    private  class GetTopicContent implements Handler.Callback{

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    TopicMessageList=(List<TopicMessage> )msg.obj;
                    Log.d(TAG, "handleMessage: size"+TopicMessageList.size());
                    flushData();
                    break;
                case 1:
                    Log.d(TAG, "handleMessage: not get list");
                    break;
            }
            return true;
        }
    }

    public void flushData(){
        Log.d(TAG, "onListItemClick===" + "flushData");
        topicContentAdapter=new TopicContentAdapter(getApplicationContext(),TopicMessageList);
        TopicContent.setAdapter(topicContentAdapter);
        topicContentAdapter.notifyDataSetChanged();
    }
}
