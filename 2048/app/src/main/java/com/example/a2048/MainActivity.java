package com.example.a2048;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView scoreTxt;
    TextView reStartTxt;
    GridLayout gridLayout;
    int ScreenWidth,ScreenHeight,width,height;
    Card [][] cards = new Card[4][4] ;
    List<Point> emptyPoint = new ArrayList<>();
    int score = 0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(MainActivity.this, UIActivity.class);
        startActivity(intent);

        scoreTxt = findViewById(R.id.scoreTxt);
        reStartTxt = findViewById(R.id.reStartTxt);
        gridLayout = findViewById(R.id.gridlayout);
        init();

        reStartTxt.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                init();
            }
        });

        gridLayout.setOnTouchListener(new View.OnTouchListener( ) {
            float x1 = 0 ,y1 = 0 , sx = 0 ,sy = 0;

            //监听手势
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        x1 =  event.getX();
                        y1 =  event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        sx =  event.getX() - x1;
                        sy =  event.getY() - y1;
                        //Log.e("滑动距离","sx="+sx+" ,sy="+sy);
                        if(Math.abs(sx) < Math.abs(sy)){
                            if(sy > 5){
                                down();
                            }
                            if(sy<-5){
                                up();
                            }
                        }else if(Math.abs(sx) > Math.abs(sy)){
                            if(sx > 5){
                                right();
                            }
                            if(sx<-5){
                                left();
                            }
                        }
                        break;
                }
                return true;
            }
        });

    }

    //初始化地图
    @SuppressLint("ResourceAsColor")
    public void init(){
        gridLayout.removeAllViews();
        gridLayout.setPadding(0,10,10,0);
        emptyPoint.clear();
        score = 0;
        scoreTxt.setText(score+"");
        for(int i = 0 ; i<4 ; i++)
            for(int j = 0 ; j< 4 ; j++){

                Card card = new Card(this);

                // 获取屏幕宽 高
                WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
                DisplayMetrics dm = new DisplayMetrics();
                wm.getDefaultDisplay().getMetrics(dm);
                ScreenWidth = dm.widthPixels ;
                ScreenHeight = dm.heightPixels;
                int min = Math.min(ScreenWidth ,ScreenHeight);

                // 计算每一个card的宽高
                height = width = (min-10-dp2px(this,20))/4 -10;

                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width,height);
                card.setGravity(Gravity.CENTER);
                card.setTextSize(40);
                card.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                card.setBackgroundColor(getResources().getColor(R.color.card_bg));
                layoutParams.setMargins(10,0,0,10);
                card.setNum(0);
                gridLayout.addView(card ,layoutParams);
                cards[i][j] = card;
            }
        createCard();
        createCard();
    }

    //创建卡片
    @SuppressLint("ResourceAsColor")
    public void createCard(){

        emptyPoint.clear();
        //遍历添加现在地图上存在的空点
        for(int i = 0 ; i< 4 ; i++)
            for(int j = 0 ; j< 4 ; j++){
                if(cards[i][j].getNum() == 0)
                    emptyPoint.add(new Point(i,j));
            }
        // 随机选取一个点用来产生一个卡片
        Point p = emptyPoint.remove((int)(Math.random()*emptyPoint.size()));
        cards[p.x][p.y].setNum(Math.random()>0.1?2:4);
        if(cards[p.x][p.y].getNum() == 2)
            cards[p.x][p.y].setTextColor(getResources().getColor(R.color.card2));
        else
            cards[p.x][p.y].setTextColor(getResources().getColor(R.color.card4));
        showMap();
        isOver();
    }

    //刷新地图，展示地图
    public void showMap(){
        for(int i = 0 ; i <4 ; i++)
            for(int j = 0 ; j< 4 ; j++){
            if(cards[i][j].getNum() >0 ) {
                cards[i][j].setText(cards[i][j].getNum( ) + "");
                cards[i][j].setTextColor(getResources().getColor(getTextColorId(cards[i][j].getNum())));
            }else
                cards[i][j].setText("");
        }
        scoreTxt.setText(""+score );
    }

    //四个方向滑动逻辑处理
    public void up() {
        boolean hasChange = false;
        Toast.makeText(this, "up", Toast.LENGTH_SHORT).show( );

        for(int j = 0 ; j < 4 ; j++)
            for(int i = 0 ; i < 4 ; i++){
                for(int k = i+1;  k < 4; k++){

                    if(cards[k][j].getNum() !=0){
                        if(cards[i][j].getNum() == 0){
                            cards[i][j].setNum(cards[k][j].getNum());
                            cards[k][j].setNum(0);
                            hasChange = true;
                            i--;
                        }else if(cards[i][j].getNum() == cards[k][j].getNum()){
                            cards[i][j].setNum(cards[i][j].getNum() *2);
                            score +=cards[k][j].getNum();
                            cards[k][j].setNum(0);
                            hasChange = true;
                        }
                        showMap();
                        break;
                    }
                }
            }

        if(hasChange){
            createCard();
        }
    }

    public void down(){
        boolean hasChange = false;
        Toast.makeText(this,"down",Toast.LENGTH_SHORT ).show();

        for(int j = 0 ; j < 4 ; j++)
            for(int i = 3 ; i >= 0 ; i--){
                for(int k = i-1; k >=0 ; k--){
                    if(cards[k][j].getNum() !=0){
                        if(cards[i][j].getNum() == 0){
                            cards[i][j].setNum(cards[k][j].getNum());
                            cards[k][j].setNum(0);
                            hasChange = true;
                            i++;
                        }else if(cards[i][j].getNum() == cards[k][j].getNum()){
                            cards[i][j].setNum(cards[i][j].getNum() *2);
                            score +=cards[k][j].getNum();
                            cards[k][j].setNum(0);
                            hasChange = true;
                        }
                        showMap();
                        break;
                    }
                }
            }

        if(hasChange){
          createCard();
        }
    }

    public void left(){
        boolean hasChange = false;
        Toast.makeText(this,"left",Toast.LENGTH_SHORT ).show();
        for(int i = 0 ; i < 4 ; i++)
            for(int j = 0 ; j < 4 ; j++){
                for(int k = j+1;  k < 4; k++){
                    if(cards[i][k].getNum() !=0){
                        if(cards[i][j].getNum() == 0){
                            cards[i][j].setNum(cards[i][k].getNum());
                            cards[i][k].setNum(0);
                            hasChange = true;
                            j--;
                        }else if(cards[i][j].getNum() == cards[i][k].getNum()){
                            cards[i][j].setNum(cards[i][j].getNum() *2);
                            score +=cards[i][k].getNum();
                            cards[i][k].setNum(0);
                            hasChange = true;
                        }
                        showMap();
                        break;
                    }
                }
            }

        if(hasChange){
        createCard();
        }
    }

    public void right(){
        boolean hasChange = false;
        Toast.makeText(this,"right",Toast.LENGTH_SHORT ).show();
        for(int i = 0 ; i < 4 ; i++)
            for(int j = 3 ; j >= 0 ; j--){
                for(int k = j-1; k >=0 ; k--){
                    if(cards[i][k].getNum() !=0){
                        if(cards[i][j].getNum() == 0){
                            cards[i][j].setNum(cards[i][k].getNum());
                            cards[i][k].setNum(0);
                            hasChange = true;
                            j++;
                        }else if(cards[i][j].getNum() == cards[i][k].getNum()){
                            cards[i][j].setNum(cards[i][j].getNum() *2);
                            score +=cards[i][k].getNum();
                            cards[i][k].setNum(0);
                            hasChange = true;
                        }
                        showMap();
                        break;
                    }

                }
            }

        if(hasChange){
            createCard();
        }
    }

    public boolean isOver(){
        if(!emptyPoint.isEmpty())
            return false;
        for(int i = 0 ; i < 4 ; i++)
            for(int j = 0 ; j < 4; j++) {
                if (cards[i][j].getNum( ) == 2048) {
                    new AlertDialog.Builder(this)
                            .setTitle("2048")
                            .setMessage("游戏胜利！")
                            .setNegativeButton("再来一次", new DialogInterface.OnClickListener( ) {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    init( );
                                }
                            }).setPositiveButton("返回", new DialogInterface.OnClickListener( ) {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(MainActivity.this, UIActivity.class);
                            startActivity(intent);
                            finish( );
                        }
                    }).show( );
                    return false;
                }
            }

        if(emptyPoint.isEmpty()) {
            for (int i = 0; i < 4; i++)
                for (int j = 0; j < 4; j++) {
                    int x1, y1;
                    int next[][];
                    next = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
                    for (int k = 0; k < 4; k++) {
                        x1 = i + next[k][0];
                        y1 = j + next[k][1];
                        if (x1 > 3 || x1 < 0 || y1 > 3 || y1 < 0)
                            continue;
                        if (cards[i][j].getNum( ) == cards[x1][y1].getNum( )) {
                            return false;
                        }
                    }
                }
        }
        new AlertDialog.Builder(this)
                .setTitle("2048")
                .setMessage("游戏失败！")
                .setNegativeButton("再来一次", new DialogInterface.OnClickListener( ) {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        init();
                    }
                }).setPositiveButton("返回", new DialogInterface.OnClickListener( ) {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MainActivity.this,UIActivity.class);
                startActivity(intent);
                finish();
            }
        }).show();
        return true;
    }

    public int getTextColorId(int num){
        switch(num){
            case 2: return R.color.card2;
            case 4: return R.color.card4;
            case 8: return R.color.card8;
            case 16: return R.color.card16;
            case 32: return R.color.card32;
            case 64: return R.color.card64;
            case 128: return R.color.card128;
            case 256: return R.color.card256;
            case 512: return R.color.card512;
            case 1024: return R.color.card1024;
            case 2048: return R.color.card2048;
        }
        return -1;
    }
//2:#eee4da
//4: #ede0c8
//8: #f2b179
//16:#f59563
//32:#f67c5f
//64: #f65e3b
//128:#edcf72
//256:#edcc61
//512:#edc850
//1024:
//2048:

    private int px2dp(Context context,float pxValue){
        float scale=context.getResources().getDisplayMetrics().density;
        return (int)(pxValue/scale+0.5f);
    }

    private int dp2px(Context context,float dpValue){
        float scale=context.getResources().getDisplayMetrics().density;
        return (int)(dpValue*scale+0.5f);
    }
}
