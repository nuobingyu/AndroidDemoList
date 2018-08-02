package com.example.lts;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class ListFragment extends Fragment{

    RecyclerView recyclerView;
    TextView qunChatText;
    EditText qunEditText;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_fragment,container ,false );
        recyclerView = v.findViewById(R.id.recyclerView);
        qunChatText = v.findViewById(R.id.qunchat_text);
        qunEditText = v.findViewById(R.id.qun_edit);
        qunChatText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return v;
    }
}
