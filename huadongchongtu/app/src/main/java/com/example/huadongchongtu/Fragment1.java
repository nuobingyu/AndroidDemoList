package com.example.huadongchongtu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;

public class Fragment1 extends Fragment {

    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);
        listView = view.findViewById(R.id.list1);
        ArrayList<String> datas = new ArrayList<>();
        for(int i = 0 ; i<50 ;i++){
            datas.add("第"+i+"个item");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.item_list ,R.id.text ,datas);
        listView.setAdapter(adapter);
        return view;
    }


}
