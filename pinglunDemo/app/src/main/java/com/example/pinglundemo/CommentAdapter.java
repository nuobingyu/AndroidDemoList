package com.example.pinglundemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import android.os.*;

public class CommentAdapter extends BaseAdapter{

    private Context context;
    private List<CommentBean> list;
    private LayoutInflater inflater;
    private replyClickListener rClickListener;
  //  private AnimateFirstDisplayListener animateFirstDisplayListener = new AnimateFirstDisplayListener();

    private String nid;
    private Handler handler;
    int maxDescripLine = 5;

    public CommentAdapter(String nid, Context context , List<CommentBean> list , int resourceId, Handler handler){
        this.nid = nid;
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.handler = handler;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position) ;
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    private final class ViewHolder {
        public ImageView commentItemImg; // 评论人图片
        public TextView commentNickname; // 评论人昵称
        public TextView likeNum; // 点赞人数
        public TextView commentItemTime; // 评论时间
        public TextView commentItemContent; // 评论内容
        public NoScrollListView replyList; // 评论回复列表
        public ImageView ivLike;
        public ImageView ivSex;
        public TextView replayNum;
        public LinearLayout digLayout;
        public LinearLayout comLayout;
        public View last;
        public View nolast;
        // public Button moreButton;
        // public View line;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final CommentBean bean = list.get(position);
        ViewHolder holder = null;
        if(convertView  == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.all_comment_item,null);
            holder.nolast = convertView.findViewById(R.id.nolast);
            holder.last = convertView.findViewById(R.id.last);
            holder.commentItemImg = convertView.findViewById(R.id.commentItemImg);
            holder.commentItemContent = convertView.findViewById(R.id.commentItemContent);
            holder.replyList = convertView.findViewById(R.id.replyList);
            holder.ivLike = (ImageView)convertView.findViewById(R.id.like);
        }


        return null;
    }

    private class TextViewClickListener implements View.OnClickListener{

        int mPosition;

        public TextViewClickListener(int position){
            mPosition = position;
        }

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.ll_dig:
                    Message digmsg = handler.obtainMessage();
                    digmsg.what = 10;
                    digmsg.arg1 = mPosition;

                    handler.sendMessage(digmsg);
                    break;
                case R.id.ll_comm:
                    Message msg = handler.obtainMessage();
                    msg.what = 11;
                    msg.arg1 = mPosition;
                    handler.sendMessage(msg);
                    break;
                case R.id.commentItemContent:
                    Message msg1 = handler.obtainMessage();
                    msg1.what = 11;
                    msg1.arg1 = mPosition;
                    handler.sendMessage(msg1);
                    break;
            }
        }
    }
    public interface replyClickListener{
        public void onClick(String realName,String tuid, String parentid,
                            int pos, int sonPosition);
    }
    public void SetOnRepalClickListener(replyClickListener rListener) {

        rClickListener = rListener;
    }

}
