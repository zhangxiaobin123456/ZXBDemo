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
    private Messenger mReplyMessenger = new Messenger(new MessengerHandler());
    private static class MessengerHandler extends Handler{
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
            Messenger messenger = new Messenger(service);

            Message message = Message.obtain();
            message.what = Constants.MSGCLIENT;
            Bundle bundle = new Bundle();
            bundle.putString("msg","hello , my name is client");
            message.setData(bundle);


            /**
             * 重要  将mReplyMessenger给回去
             */
            message.replyTo = mReplyMessenger;
            try {
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
