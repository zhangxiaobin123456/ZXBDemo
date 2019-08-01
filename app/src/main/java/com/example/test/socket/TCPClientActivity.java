package com.example.test.socket;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.test.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TCPClientActivity extends AppCompatActivity {

    @BindView(R.id.tv_msg_container)
    TextView mTvMsgContainer;
    @BindView(R.id.et_msg)
    EditText mEtMsg;
    @BindView(R.id.btn_send)
    Button mBtnSend;
    private Intent mIntent;
    private Socket mClientSocket;
    private static final int MESSAGE_RECEIVE_NEW_MSG = 1;
    private static final int MESSAGE_SOCKET_CONNECTED = 2;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_RECEIVE_NEW_MSG: {
                    mTvMsgContainer.setText(mTvMsgContainer.getText() + (String) msg.obj);
                    break;
                }
                case MESSAGE_SOCKET_CONNECTED: {
                    mBtnSend.setEnabled(true);
                    break;
                }
                default:
                    break;
            }
        }
    };
    private PrintWriter mPrintWriter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcpclient);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        mIntent = new Intent(this, TCPServerService.class);
        startService(mIntent);

        new Thread(new Runnable() {
            @Override
            public void run() {
                //连接servc
                connectTCPServer();
            }
        }).start();
    }

    private void connectTCPServer() {
        Socket socket = null;
        while (socket == null) {
            try {
                socket = new Socket("localhost", 8686);
                mClientSocket = socket;

                mPrintWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                mHandler.sendEmptyMessage(MESSAGE_SOCKET_CONNECTED);

                //用于接收服务端消息
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while (!TCPClientActivity.this.isFinishing()){
                    String serverMsg = bufferedReader.readLine();
                    if (serverMsg != null) {
                        String time = formatDateTime(System.currentTimeMillis());

                        String msg = "servermsg  ____ " + time +" : "+serverMsg +"\n";
                        mHandler.obtainMessage(MESSAGE_RECEIVE_NEW_MSG,msg).sendToTarget();
                    }
                }

                bufferedReader.close();
                mPrintWriter.close();
                socket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        stopService(mIntent);
        if (mClientSocket != null) {
            try {
                mClientSocket.shutdownInput();
                mClientSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }

    @OnClick(R.id.btn_send)
    public void onViewClicked() {
        new Thread(new Runnable() {
            @Override
            public void run() {
               final String msg = mEtMsg.getText().toString();
                if (!TextUtils.isEmpty(msg) && mPrintWriter != null) {
                    mPrintWriter.println(msg);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mEtMsg.setText("");
                            String time = formatDateTime(System.currentTimeMillis());

                            String showMsg = "clientmsg  ____ " + time +" : "+msg +"\n";
                            mTvMsgContainer.setText(mTvMsgContainer.getText() + showMsg);
                        }
                    });

                }
            }
        }).start();

    }

    @SuppressLint("SimpleDateFormat")
    private String formatDateTime(long time) {
        return new SimpleDateFormat("(HH:mm:ss)").format(new Date(time));
    }
}
