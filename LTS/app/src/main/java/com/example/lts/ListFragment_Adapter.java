package com.example.lts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListFragment_Adapter extends RecyclerView.Adapter<ListFragment_Adapter.ViewHolder>{

    private List<Client> data = new ArrayList<>();
    private Context mContext;


    public ListFragment_Adapter(List<Client> list, Context context){
        data = list;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext ,R.layout.list_fragment_item,parent );
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView txImg;
        TextView nameText;

        public ViewHolder(View itemView) {
            super(itemView);
            txImg = itemView.findViewById(R.id.item1_tx);
            nameText = itemView.findViewById(R.id.item1_name);
        }
    }
}
