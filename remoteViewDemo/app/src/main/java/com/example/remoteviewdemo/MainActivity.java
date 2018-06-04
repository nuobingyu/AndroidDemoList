package com.example.remoteviewdemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {

    public static ImageView imageView;
    @SuppressLint("HandlerLeak")
    public static  Handler myHander = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1){
                MainActivity.imageView.setImageBitmap((Bitmap) msg.obj);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Notification notification = new Notification();
//        notification.icon = R.drawable.ic_launcher_foreground;
//        notification.tickerText = "这是一则通知";
//        notification.when =System.currentTimeMillis();
//        notification.flags = Notification.FLAG_AUTO_CANCEL;
//        Intent intent = new Intent(this, AActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent ,PendingIntent.FLAG_UPDATE_CURRENT);
//        RemoteViews remoteViews = new RemoteViews(getPackageName() ,R.layout.layout_notification);
//        remoteViews.setTextViewText(R.id.msg,"chapter_5");
//        remoteViews.setImageViewResource(R.id.icon,R.drawable.ic_launcher_background );
//        PendingIntent openActivityIntent =PendingIntent.getActivity(this, 0,
//                new Intent(this,AActivity.class), PendingIntent.FLAG_UPDATE_CURRENT );
//        remoteViews.setOnClickPendingIntent(R.id.text,openActivityIntent);
//        notification.contentView = remoteViews;
//        notification.contentIntent = pendingIntent;
//        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        manager.notify(2,notification);

//        imageView = findViewById(R.id.main_img);
//        Intent intent1 = new Intent("com.example.remoteviewdemo.action.CLICK");
//        sendBroadcast(intent1);

    }
}
