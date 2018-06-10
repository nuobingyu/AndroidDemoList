package com.example.rxjavademo;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TimeUtils;


import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    /**
     * Observable:被观察者
     * Observer:观察者
     * subscribe:订阅操作 为：被观察者订阅观察者
     *
     * 我们可以这样：
     * 1.创建一个Observable：Observable.create(new Ob....) //创建方法还可以just,from。
     * 2.订阅：然后在上一个创建完的分号前.subscribe(new Subscriber(){...})
     * 我们也可以这样：
     * 一个观察者被多个被观察者这订阅
     * 先创建一个观察者observer，然后创建多个被观察者observer去订阅它
     * */

    public static final String TAG1 = "Observer";
    public String Tag = "调试信息";
    private Observer<String> observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //创建观察者
        observer = new Observer<String>( ) {
            @Override
            public void onCompleted() {
                Log.e(Tag, Thread.currentThread( ).getName( ));
                Log.e(TAG1, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(Tag, Thread.currentThread( ).getName( ));
                Log.e(TAG1, "onError");
            }

            @Override
            public void onNext(String s) {
                Log.e(Tag, Thread.currentThread( ).getName( ));
                Log.e(TAG1, "onNext");
                Log.e(TAG1, s);
            }
        };
        flatMapFun();
    }
    //创建被观察者的三种方法
    public void createFun1(){
        Observable.just("1","2","3").subscribe(observer);
    }

    public void createFun2(){
        String[] words = {"aa","bb,","cc"};
        Observable observable2 = Observable.from(words);
        observable2.subscribe(new Subscriber( ) {
            @Override
            public void onCompleted() {
                Log.i(TAG1,"completed");
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG1,"onerror" ) ;
            }

            @Override
            public void onNext(Object o) {
                Log.i(TAG1,(String)o);
            }
        });
    }

    public void createFun3(){
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("Hi");
                subscriber.onNext("Aloha");
                subscriber.onCompleted();
            }
        }).subscribe(new Action1<String>( ) {
            @Override
            public void call(String s) {
                Log.e(TAG1 ,s);
            }
        });
    }

    //调度线程 即发生线程和接收线程的调度
    public void xianchengdiaodu(){
        new Thread(new Runnable( ) {
            @Override
            public void run() {
                final Handler handler = new Handler();
                Observable.just("one", "two", "three", "four", "five")
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<String>() {

                            @Override
                            public void onCompleted() {
                                Log.e(Tag ,"调试结束");
                                Log.e(Tag,Thread.currentThread().getName());
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(Tag,"调试出错");
                                Log.e(Tag,Thread.currentThread().getName());
                            }

                            @Override
                            public void onNext(String s) {
                                Log.e(Tag,"调试内容："+s);
                                Log.e(Tag,Thread.currentThread().getName());
                            }
                        });
            }
        },"custom-thread-1").start();
    }

    //交换-------map
    //特点：一对一交换
    public void mapFun(){
        Observable.just("1","2","3").map(new Func1<String, String>( ) {
            @Override
            public String call(String s) {
                String xiushihou = s +"map";
                Log.e("xiushihou",xiushihou);

                return s;
            }
        }).subscribe(new Action1<String>( ) {
            @Override
            public void call(String s) {
                Log.e("shengqi",s);
            }
        });
    }

    //交换-------flatMap
    //特点：无序输出，一旦发生错误舍弃改链
    //     提供一种铺平序列的方式，合并这些 Observables发射的数据，合并后作为最终的Observable
    public void flatMapFun(){
        final String[] ss = {"1","2","3","4"};
        final String[] s1={"a","b","c"};
        Observable.from(ss)
                //.take(3)//执行3次
                .flatMap(new Func1<String, Observable<?>>( ) {
                    @Override
                    public Observable<?> call(String s) {
                        Log.e("post",s);
                        return Observable.from(s1).delay((long)(Math.random() *2 +0.5)
                        , TimeUnit.SECONDS) ;
                    }
                }).subscribe(new Action1<Object>( ) {
            @Override
            public void call(Object o) {
                Log.e("call","call执行了"+(String)o);
            }
        });
    }

    //交换-------concatMap
    //特点：可以一对多，多对多，一对一（除一对一 一般用from/just进行一一分发）
    //     onNext方法执行顺序不同，允许交叉传递，并顺序输出（有一种连续的铺平函数）
    public void concatMap(){

    }

    //交换--------flatMaplterable()
    //特点：和flatMap()很像。仅有的本质不同是它将源数据生成 Iterable，而不是原始数据项和生成的 Observables。

    //交换--------switchMap()
    //特点：switchMap() 和 flatMap() 很像，除了一点：每当源 Observable 发射一个新的数据项（Observable）时，它将取消订阅并停止监视之前那个数据项产生的 Observable，并开始监视当前发射的这一个。

}
