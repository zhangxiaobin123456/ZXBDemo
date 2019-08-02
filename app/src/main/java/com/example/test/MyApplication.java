package com.example.test;

import android.app.Application;
import android.os.Process;
import android.util.Log;

import com.example.test.binders.BinderPool;
import com.example.test.binders.BinderPoolActivity;
import com.example.test.utils.ProcessUtils;

/**
 * Description:
 */

public class MyApplication extends Application{
    private static final String TAG = "MyApplication";
    @Override
    public void onCreate() {
        super.onCreate();

        String processName = ProcessUtils.getProcessName(getApplicationContext(), Process.myPid());
        Log.e(TAG, "onCreate: "+processName);
//        BinderPool binderPool = BinderPool.getInstance(this);
    }
}
