package com.example.test.process;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.test.R;
import com.example.test.service.MessengerService;
import com.example.test.utils.Constants;

/**
 * messenger
 */
public class MessengerActivity extends AppCompatActivity {
    private static final String TAG = "MessengerActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        initData();
    }
    private Messenger mReplyMessenger = new Messenger(new MessengerActivityHandler ());
    private static class MessengerActivityHandler  extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case Constants.MSGSERVICE:
                    Log.e(TAG, "handleMessage: servicemessage"+msg.getData().getString("reply"));
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    };



    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //创建Messenger
            Messenger messenger = new Messenger(service);
            //创建Message
            Message message = Message.obtain();
            //标识
            message.what = Constants.MSGCLIENT;
            //创建Bundle
            Bundle bundle = new Bundle();
            //保存数据
            bundle.putString("msg","你好啊，送你一朵小发发");
            //赋值
            message.setData(bundle);


            /**
             * 重要  将mReplyMessenger给回去
             */
            message.replyTo = mReplyMessenger;
            try {
                //发送数据
                messenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private void initData() {
        Intent intent = new Intent(this, MessengerService.class);
        bindService(intent,mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        unbindService(mServiceConnection);
        super.onDestroy();
    }
}
