package com.example.fadingedgedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = findViewById(R.id.text);
        tv.setText("fadingEdgeLength表示阴影部分的高度,requiresFadingEdge表示阴影的方向。方向可以是水平的，也可以是垂直的。水平效果我一会再说。当然，如果我们只是在xml文件中这样写，TextView还是无法滚动起来，还需要在Activity中添加如下一行代码，TextView才能滚动起来，如下：");
        tv.setMovementMethod(new ScrollingMovementMethod());
    }
}
