package com.example.retrifitdemo1;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends Activity {

    /**
     * AsyncTask的规则：
     * 1、AsyncTask实例必须在UI线程中创建。
     * 2、execute(Params... params)方法必须在UI线程中调用。
     * 3、不要手动调用onPreExecute，doInBackground，onProgressUpdate，onPostExecute这些方法
     * 4、一个AsyncTask实例只能执行一次（只能调用一次execute方法），如果执行第二次将会抛出异常
     * */



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //使用AsynTast登陆
        final UserLoginTask userLoginTask = new UserLoginTask("04161068","nuobingyu123");
        userLoginTask.execute();
    }
}
