package com.example.test.binders;

import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.test.R;
import com.example.test.utils.Constants;

public class BinderPoolActivity extends AppCompatActivity {

    private static final String TAG = "BinderPoolActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder_pool);
        initData();
    }

    private void initData() {

        new Thread(new Runnable() {

            @Override
            public void run() {
                go();
            }
        }).start();


    }

    private void go() {
        BinderPool binderPool = BinderPool.getInstance(BinderPoolActivity.this);
        IBinder encryptionIBinder = binderPool.queryBinder(Constants.IENCRYPTIONCODE);
        IEncryption iEncryption = IEncryption.Stub.asInterface(encryptionIBinder);
        String mContent = "abcdefghijklmnopqrstuvwxyz";
        Log.e(TAG, "initData: 原始数据: "+mContent);
        try {
            String password = iEncryption.encrypt(mContent);
            Log.e(TAG, "initData: 转换后数据: "+password);

            String decryptContent = iEncryption.decrypt(password);
            Log.e(TAG, "initData: 解密后原始数据: "+decryptContent);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        IBinder computeIBinder = binderPool.queryBinder(Constants.ICOMPUTECODE);
        ICompute iCompute = ICompute.Stub.asInterface(computeIBinder);
        try {
            Log.e(TAG, "initData: 100 + 200 = "+iCompute.add(100,200));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
