package com.example.lts;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Sever {

    private Socket socket;
    private Context mContext;
    private List<Socket> socketList = new ArrayList<>();

    public Sever(Context context){
        mContext = context;
        new Thread(new SeverThread()).start();
    }

    class SeverThread implements Runnable {

        @Override
        public void run() {
            try {
                ServerSocket ss = new ServerSocket(10001);
                socket = ss.accept();
                socketList.add(socket);
                Log.i("sever",socket.getLocalAddress()+"上线了~");
                Log.i("sever","个数："+socketList.size());
                InputStream is = socket.getInputStream( );

                byte[] b = new byte[1024];
                while (is.read(b) != -1) {
                    String str = b.toString( );
                    String strs[] = str.split("//");
//                    //群发
//                    for(Socket s : socketList)
//                    {
//                        OutputStream os = s.getOutputStream( );
//                        os.write(b, 0, b.length);
//                    }


//                    OutputStream os =socket.getOutputStream();
//                    os.write(b);
                }

            } catch (IOException e) {
                e.printStackTrace( );
            }
        }
    }
}
