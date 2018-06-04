package com.example.remoteviewdemo;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Message;
import android.support.annotation.MainThread;
import android.util.Log;
import android.widget.RemoteViews;
import android.os.SystemClock;
import android.os.Handler;

public class MyAppWidgetProvider extends AppWidgetProvider{
    public static final String TAG ="MyAppWidgetProvider";
    public static final String CLICK_ACTION = "com.example.remoteviewdemo.action.CLICK";

    public MyAppWidgetProvider(){
        super();
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        super.onReceive(context, intent);

        Log.i(TAG,"onRecevice : action = " + intent.getAction());
        if(intent.getAction().equals(CLICK_ACTION)){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.a);
                    AppWidgetManager manager = AppWidgetManager.getInstance(context);
                    for(int i = 0 ; i< 37 ; i++){
                        float degree = (i * 10) % 360;
                        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
                        remoteViews.setImageViewBitmap(R.id.img1 ,rotateBitmap(context , bitmap, degree));
                        Log.i(TAG,bitmap.toString());
                        Intent intentClick = new Intent(CLICK_ACTION);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(context , 0, intentClick ,0);
                        remoteViews.setOnClickPendingIntent(R.id.img1,pendingIntent);
                        manager.updateAppWidget(new ComponentName(context , MyAppWidgetProvider.class), remoteViews);
                        SystemClock.sleep(30);
                    }
                }
            }).start();
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.i(TAG,"---------------------onUpdate-----------------------");
        final int counter = appWidgetIds.length;
        for(int i = 0 ; i< counter ; i++){
            int appWidgetId = appWidgetIds[i];
            onWidgetUpdate(context , appWidgetManager ,appWidgetId);
        }
    }

    private void onWidgetUpdate(Context context ,AppWidgetManager appWidgetManager ,int appWidgerid){
        Log.i(TAG,"appWidgetId = "+appWidgerid);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
        Intent intentClick = new Intent(CLICK_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context ,0,intentClick ,0);
        remoteViews.setOnClickPendingIntent(R.id.img1 ,pendingIntent);
        appWidgetManager.updateAppWidget(appWidgerid ,remoteViews);
    }

    private Bitmap rotateBitmap(Context context ,Bitmap srcBitmap,float degree){
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(degree);
        Bitmap temBitmap = Bitmap.createBitmap(srcBitmap ,0,0,
                srcBitmap.getWidth(),srcBitmap.getHeight() ,matrix ,true);

        //  Log.i(TAG,"旋转后的bitmap"+" degree:"+degree);
        Message msg = new Message();
        msg.what = 1;
        msg.obj = srcBitmap;
        MainActivity.myHander.sendMessage(msg);

        return temBitmap;
    }
}
