package com.example.lts;

import android.app.Notification;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.*;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView qunChatText;
    EditText qunEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Sever sever = new Sever(this);
        Client client = new Client("nby",this);
        recyclerView = findViewById(R.id.recyclerView);
        qunChatText = findViewById(R.id.qunchat_text);
        qunEditText = findViewById(R.id.qun_edit);
        qunChatText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });



//        MyHandler handler = new MyHandler(this);
//        Message msg = new Message();
//        msg.what = 101;
//        handler.handleMessage(msg);
    }
}
