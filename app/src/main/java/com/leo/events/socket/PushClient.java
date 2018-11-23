package com.leo.events.socket;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by bighero on 2018/11/15.
 */
public class PushClient {
    Socket s = null;
    DataOutputStream dos = null;
    DataInputStream dis = null;
    private boolean bConnected = false;
    private Context mContext;
    private Handler mHandler;

    public interface Callback{
        void callback(String o);
    }

    public Callback callback;
    public void connect(final int port, Context context,Callback callback) {
        mContext = context;
        this.callback = callback;
        mHandler = new Handler(Looper.getMainLooper());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    s = new Socket("39.105.123.166", port);
                    dos = new DataOutputStream(s.getOutputStream());
                    dis = new DataInputStream(s.getInputStream());
                    bConnected = true;
                    send();
                    receiver();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    private void receiver() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String[] a = new String[1];
                while (bConnected) {
                    try {
                        String s = dis.readUTF();
                        System.out.println("客户端收到：" + s);
                        a[0] = s;
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
//                                Toast.makeText(mContext, a[0], Toast.LENGTH_SHORT).show();
                                if (callback!=null){
                                    callback.callback(a[0]);
                                }
                            }
                        });
                    } catch (IOException e) {
                        disconnect();
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void send() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    dos.writeUTF("开始连接");
                    dos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public void send(final String txt) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    dos.writeUTF(txt);
                    dos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public void disconnect() {
        bConnected = false;
        try {
            if (dos != null)
                dos.close();
            if (dis != null)
                dis.close();
            if (s != null)
                s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        PushClient pushClient = new PushClient();
//        pushClient.connect(6180);
//    }
}
