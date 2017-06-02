package com.example.android.joyin.Topic;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.joyin.Asynctask.topic.SendTopicMessage;
import com.example.android.joyin.R;

public class SetMessage extends AppCompatActivity {

    EditText SetMessageItem;
    Button SetMessageSend;
    Handler handler;
    int topic_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_message);
        SetMessageItem=(EditText) findViewById(R.id.set_message_item);
        SetMessageSend=(Button)findViewById(R.id.set_message_send);
        handler=new Handler(new SetMessageHandler());
        topic_id=getIntent().getIntExtra("topic_id", 0);
        SetMessageSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SendTopicMessage(SetMessageItem.getText().toString(),topic_id,handler).start();
            }
        });
    }

    private  class SetMessageHandler implements Handler.Callback{

        @Override
        public boolean handleMessage(Message msg) {
            if(msg.what==0)
                Toast.makeText(getApplicationContext(),"提交话题讨论内容成功",Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getApplicationContext(),"提交话题讨论内容失败",Toast.LENGTH_SHORT).show();
            return true;
        }
    }
}
