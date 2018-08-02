package com.example.lts;

import android.app.Activity;
import android.app.Notification;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.os.*;

public class MultiPeopleChatActivity extends Activity{

    TextView jiahaoText;
    TextView sendText;
    EditText inputText;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multipeoplechat_layout);

        jiahaoText = findViewById(R.id.qun_people);
        sendText = findViewById(R.id.qun_send);
        inputText = findViewById(R.id.qun_edit);
        recyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout = findViewById(R.id.swipe_qun);

        sendText.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
               MyHandler handler = new MyHandler(getBaseContext());
                Message message = Message.obtain();
                message.what  = 102;
                message.obj = inputText.getText().toString();
                inputText.setText("");
                handler.handleMessage(message);
            }
        });
        inputText.addTextChangedListener(new TextWatcher( ) {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String content = inputText.getText().toString();
                if(!content.equals("")){
                    if(content.contains("@")){
                        //跳转到群列表people并且点击表示@的人
                    }
                }
            }
        });
    }
}
