package com.example.test.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.test.utils.Constants;

/**
 * Description:
 */

public class MessengerService extends Service {

    private static final String TAG = "MessengerService";

    private static class MessengerHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case Constants.MSGCLIENT:
                    Log.e(TAG, "handleMessage: clientmessage"+msg.getData().getString("msg"));

                    Messenger messenger = msg.replyTo;
                    Message replyMessage = Message.obtain();
                    replyMessage.what = Constants.MSGSERVICE;
                    Bundle bundle = new Bundle();
                    bundle.putString("reply","收到了你的消息，但我就是不想理你");
                    replyMessage.setData(bundle);

                    try {
                        messenger.send(replyMessage);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }


                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }

    private Messenger mMessenger = new Messenger(new MessengerHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}
