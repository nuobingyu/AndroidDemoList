package com.example.lts;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import android.app.Activity;
import android.content.Context;
import android.os.*;
import android.support.annotation.Nullable;

public class Client{

    private String name;
    private Socket socket;
    private Context mContext;
    private int id;
    private static int count = 0;

    public Client(String name, Context context){
        this.name = name;
        mContext = context;
        count ++;
        this.id = count;
        new Thread(new ClientReadThread()).start();
    }

    public Client(Context context){
        this.mContext = context;
        count ++;
        this.id = count;
        new Thread(new ClientReadThread()).start();
    }



    class ClientReadThread implements Runnable {

        @Override
        public void run() {
            try {

                socket = new Socket(InetAddress.getLocalHost() ,10001);
                OutputStream os = socket.getOutputStream();
                InputStream is = socket.getInputStream();
                int l;
                byte b[] = new byte[1024];
                while((l=is.read(b))!=-1){
                    MyHandler handler = new MyHandler(mContext);
                    Message msg = Message.obtain();
                    msg.what = 101;
                    msg.obj = b.toString();
                    handler.handleMessage(msg);
                }

            } catch (IOException e) {
                e.printStackTrace( );
            }
        }
    }

    class ClientWriteThread implements Runnable{
        private String msg;
        public ClientWriteThread(String msg){
            this.msg = msg;
            new Thread(new ClientReadThread()).start();
        }

        @Override
        public void run() {
            try {
                OutputStream os = socket.getOutputStream();
                os.write(msg.getBytes() ,0,msg.getBytes().length);

            } catch (IOException e) {
                e.printStackTrace( );
            }

        }
    }

    public void AsClientSendMsg(String msg){
        new Thread(new ClientWriteThread(msg)).start();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
