package com.example.lts;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyHandler extends Handler {
    private Context mContext;
    boolean isMsgCome = false;
    private String mMsg;
    private List<Client> dataList = new ArrayList<>();
    public MyHandler(Context context ){
        mContext = context;
    }

    @Override
    public void handleMessage(Message msg) {
       switch(msg.what){
           case 99:
               //
               int num =(int) msg.obj;
               int flag = 0;
               for(Client c:dataList){
                   if(c.getId() == num){
                       Toast("找到匹配的用户");
                        flag=1;
                   }
                   if(flag !=1){
                       Toast("未找到匹配的用户");
                   }
               }
               break;
           case 100:
               //存client
               Client client =(Client)msg.obj;
               dataList.add(client);
               break;
           case 101:
               mMsg=(String)msg.obj;
               Toast("收到了101: "+mMsg);
               isMsgCome = true;
               break;
           case 102:
               Toast("收到了102");
               mMsg = (String)msg.obj;

       }
    }


    public void Toast(String content){
        Toast.makeText(mContext ,content ,Toast.LENGTH_SHORT).show();
    }

}
