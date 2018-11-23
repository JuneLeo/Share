package com.leo.events;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.leo.events.socket.PushClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by spf on 2018/11/19.
 */
public class ChatActivity extends AppCompatActivity {
    PushClient pushClient;
    ChatAdapter adapter;
    List<String> data = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initView();
        initPush();
    }

    private void initView() {
        final EditText editText = findViewById(R.id.et_text);
        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                if (TextUtils.isEmpty(text)) {
                    return;
                }
                pushClient.send(text);
                editText.setText("");
            }
        });

        RecyclerView recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
        adapter = new ChatAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void initPush() {
        pushClient = new PushClient();
        pushClient.connect(13456, ChatActivity.this, new PushClient.Callback() {
            @Override
            public void callback(String o) {
                if (TextUtils.isEmpty(o)) {
                    return;
                }
                data.add(o);
                adapter.notifyDataSetChanged();
                ((RecyclerView) findViewById(R.id.list)).smoothScrollToPosition(data.size() - 1);
            }
        });
    }

    private class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatVH> {

        @Override
        public ChatVH onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            return new ChatVH(LayoutInflater.from(context).inflate(R.layout.item_chat, parent, false));
        }

        @Override
        public void onBindViewHolder(ChatVH holder, int position) {
            holder.tv.setText(data.get(position));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class ChatVH extends RecyclerView.ViewHolder {
            public TextView tv;

            public ChatVH(View itemView) {
                super(itemView);
                tv = itemView.findViewById(R.id.item_txt);
            }
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        pushClient.disconnect();
    }
}
