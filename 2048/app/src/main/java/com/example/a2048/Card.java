package com.example.a2048;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Card extends android.support.v7.widget.AppCompatTextView{

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    private int num;

    public Card(Context context) {
        super(context);
    }

    public Card(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Card(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
