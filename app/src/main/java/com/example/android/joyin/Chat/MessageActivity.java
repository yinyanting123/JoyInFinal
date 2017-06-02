package com.example.android.joyin.Chat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ListView;

import com.example.android.joyin.R;

import java.util.ArrayList;

public class MessageActivity extends AppCompatActivity {
    private ArrayList<Msg> msgs;
    private EditText et_input;
//    private MyAdapter myAdapter;
    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);


        lv = (ListView) findViewById(R.id.lv);
        et_input = (EditText) findViewById(R.id.et_input);

//        findViewById(R.id.bt_send).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String content = et_input.getText().toString();
//                if (!content.isEmpty()) {
//                    //界面操作
//                    msgs.add(new Msg(content, Msg.TYPE_SEND));
//                    myAdapter.notifyDataSetChanged();
//                    lv.setSelection(msgs.size() - 1);
//                    et_input.setText("");
//                    //通过第三方服务器发送消息
//                    sendText(content);
//                } else {
//                    Toast.makeText(MessageActivity.this, "请输入内容!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        msgs = new ArrayList<>();
//        msgs.add(new Msg("hello", Msg.TYPE_RECEIVE));
//        msgs.add(new Msg("who is that?", Msg.TYPE_SEND));
//        msgs.add(new Msg("this is LiLei,nice to meet you!", Msg.TYPE_RECEIVE));
//
//        myAdapter = new MyAdapter();
//        lv.setAdapter(myAdapter);
    }

    protected void onDestroy(){
        super.onDestroy();
//        NIMClient.getService(MsgServiceObserve.class).observeReceiveMessage(incomingMessageObserver,true);
    }

//    //监听发过来的消息
//    Observer<List<IMMessage>> incomingMessageObserver=
//            new Observer<List<IMMessage>>() {
//                @Override
//                public void onEvent(List<IMMessage> imMessages) {
//                    //处理新收到的消息，为了上传处理方便，SDK保证参数messages全部来自同一个聊天对象
//                    for(IMMessage message:imMessages){
//                       // text.setText(message.getContent());
//                        msgs.add(new Msg(message.getContent(), Msg.TYPE_RECEIVE));
//                        myAdapter.notifyDataSetChanged();
//                    }
//                }
//            };
//    //对消息进行发送
//    public void sendText(String textMessage){
//        IMMessage message= MessageBuilder.createTextMessage("user2", SessionTypeEnum.P2P,textMessage);
//        NIMClient.getService(MsgService.class).sendMessage(message,false).setCallback(new RequestCallback<Void>() {
//            @Override
//            public void onSuccess(Void param) {
//                Toast.makeText(MessageActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailed(int code) {
//                Toast.makeText(MessageActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onException(Throwable exception) {
//
//            }
//        });
//    }
//
//    private class MyAdapter extends BaseAdapter {
//
//        @Override
//        public int getCount() {
//            return msgs.size();
//        }
//
//        @Override
//        public Msg getItem(int position) {
//            return msgs.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            ViewHolder holder;
//            if (convertView == null) {
//                holder = new ViewHolder();
//                convertView = View.inflate(getApplicationContext(), R.layout.activity_message_item, null);
//                holder.tv_receive = (TextView) convertView.findViewById(R.id.tv_receive);
//                holder.tv_send = (TextView) convertView.findViewById(R.id.tv_send);
//                convertView.setTag(holder);
//            } else {
//                holder = (ViewHolder) convertView.getTag();
//            }
//            Msg msg = getItem(position);
//            if (msg.type == Msg.TYPE_RECEIVE) {
//                holder.tv_receive.setVisibility(View.VISIBLE);
//                holder.tv_send.setVisibility(View.GONE);
//                holder.tv_receive.setText(msg.content);
//            } else if (msg.type == Msg.TYPE_SEND) {
//                holder.tv_send.setVisibility(View.VISIBLE);
//                holder.tv_receive.setVisibility(View.GONE);
//                holder.tv_send.setText(msg.content);
//            }
//            return convertView;
//        }
//    }
//
//    private static class ViewHolder {
//        TextView tv_receive;
//        TextView tv_send;
//    }
}
