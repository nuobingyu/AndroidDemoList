package com.example.matrixcolordemo;

import android.database.MatrixCursor;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{

    private SeekBar seek1,seek2,seek3;
    ColorMatrix colorMatrix1,colorMatrix2 ,colorMatrix3;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = findViewById(R.id.img);
        seek1 = findViewById(R.id.seek1);
        seek2 = findViewById(R.id.seek2);
        seek3 = findViewById(R.id.seek3);
        seek1.setOnSeekBarChangeListener(this);
        seek2.setOnSeekBarChangeListener(this);
        seek3.setOnSeekBarChangeListener(this);
//        float[] arr = new float[]{0.33F,0.59F,0.11F,0,0,
//                0.33F,0.59F,0.11F,0,0,
//                0.33F,0.59F,0.11F,0,0,
//                0,0,0,1,0};
        colorMatrix1 = new ColorMatrix();
        colorMatrix2 = new ColorMatrix();
        colorMatrix3 = new ColorMatrix();



    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        //设置色相
        colorMatrix1.reset();
        colorMatrix1.setRotate(0,(seek1.getProgress() -128f) *1.0f / 128f* 180);
        colorMatrix1.setRotate(1,(seek1.getProgress() -128f) *1.0f / 128f* 180);
        colorMatrix1.setRotate(2,(seek1.getProgress() -128f) *1.0f / 128f* 180);

        //设置饱和度
        colorMatrix2.reset();
        colorMatrix2.setSaturation(seek2.getProgress() /128f);

        //设置亮度
        colorMatrix3.reset();
        colorMatrix3.setScale(seek3.getProgress() /128f,
                seek3.getProgress() /128f,
                seek3.getProgress() /128f,
                1);

        //效果叠加
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.reset();
        colorMatrix.postConcat(colorMatrix1);
        colorMatrix.postConcat(colorMatrix2);
        colorMatrix.postConcat(colorMatrix3);

        img.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }


}
