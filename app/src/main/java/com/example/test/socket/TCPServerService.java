package com.example.test.socket;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * Description:
 */

public class TCPServerService extends Service{
    private boolean serverRun = true;
    private static final String TAG = "TCPServerService";

    private String[] messages = new String[]{
            "天王盖地虎，小鸡炖蘑菇。",
            "宝塔镇河妖，蘑菇放辣椒。",
            "河妖用大招，二楼弯下腰。",
            "莫愁不开怀，二楼出人才。",
            "钛合金狗眼，贞操已用完。",
            "糗百无女神，屌丝一大群。",
            "屌丝变色狼，色狼变流氓。",
            "宅男寻腐女，腐女已有郎。",
            "想富卖切糕，木耳爱香蕉。",
            "恨未逢时生，前路很渺茫。",
            "寻她千百度，千里聚一趟。",
            "玉帝和王母，雪碧两块五。",
            "只因梦太短，起来洗把脸。"
    };
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new TCPServer()).start();
    }

    @Override
    public void onDestroy() {
        serverRun = false;
        super.onDestroy();
    }

    private class TCPServer implements Runnable{

        @Override
        public void run() {

            ServerSocket serverSocket = null;
            try {
                //监听8686端口
                serverSocket = new ServerSocket(8686);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }


            while (serverRun){
                try {
                    final Socket socket = serverSocket.accept();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                responseClient(socket);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void responseClient(Socket socket) throws IOException {
        //用于接收客户端消息
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //用于向客户端发送消息
        PrintWriter printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
        printWriter.println("欢迎来到大型交友平台");

        while (serverRun){
            String msg = bufferedReader.readLine();
            Log.e(TAG, "responseClient: receive msg  _:" +msg);
            if (msg == null) {
                break;
            }
            int i = new Random().nextInt(messages.length);
            String replyMsg = messages[i];
            printWriter.println(replyMsg);
            Log.e(TAG, "responseClient: send msg _:" +replyMsg);
        }
        bufferedReader.close();
        printWriter.close();
        socket.close();
    }
}
