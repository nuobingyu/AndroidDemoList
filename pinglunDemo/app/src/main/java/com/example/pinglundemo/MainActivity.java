package com.example.pinglundemo;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.*;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<CommentBean> comments = new ArrayList<>();
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 10:

                    break;
                case 11:// 一级评论的回复

                    break;
                default:
                    break;
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView mListView = findViewById(R.id.replyList);
        CommentAdapter mAdapter = new CommentAdapter("0", MainActivity.this,
                comments, R.layout.comment_item, handler);
        mAdapter.SetOnRepalClickListener(new CommentAdapter.replyClickListener() {

            @Override
            public void onClick(String realName, String tuid, String parentid,
                                int pos, int sonPosition) {// 二级评论的回复
                Toast.makeText(MainActivity.this, "二级的回复", Toast.LENGTH_SHORT).show();

            }
        });
        mListView.setAdapter(mAdapter);
    }
}
