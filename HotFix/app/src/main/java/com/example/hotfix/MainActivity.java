package com.example.hotfix;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import android.os.*;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.euler.andfix.patch.PatchManager;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyApplication";
    /**
     * apatch文件
     */
    private static final String APATCH_PATH = "/Dennis.apatch";

    private PatchManager mPatchManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        Button getButton = findViewById(R.id.getpathc);
        final TextView tv = findViewById(R.id.text);

        button.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                tv.setText("出现bug了");
               // tv.setText("修复bug了");
            }
        });

        getButton.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                mPatchManager = new PatchManager(MainActivity.this);
                mPatchManager.init("1.0"); // 版本号

                // 加载 apatch
                mPatchManager.loadPatch();

                //apatch文件的目录
                String patchFileString = Environment.getExternalStorageDirectory().getAbsolutePath() + APATCH_PATH;
                Log.i(TAG,"补丁路径："+patchFileString);
                File apatchPath = new File(patchFileString);

                if (apatchPath.exists()) {
                    Log.i(TAG, "补丁文件存在");
                    try {
                        //添加apatch文件
                        mPatchManager.addPatch(patchFileString);
                    } catch (IOException e) {
                        Log.i(TAG, "打补丁出错了");
                        e.printStackTrace();
                    }
                } else {
                    Log.i(TAG, "补丁文件不存在");
                }
            }
        });
    }
}
