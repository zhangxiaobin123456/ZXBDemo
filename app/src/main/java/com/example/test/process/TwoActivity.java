package com.example.test.process;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.test.R;
import com.example.test.bean.User;
import com.example.test.utils.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class TwoActivity extends AppCompatActivity {
    private static final String TAG = "TwoActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        readFromFile();
    }

    /**
     * 从文件中读取
     */
    private void readFromFile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                User user = null;

                File userFile = new File(Constants.PATH+"user.txt");
                if (userFile.exists()) {
                    ObjectInputStream mObjectInputStream = null;

                    try {
                        mObjectInputStream = new ObjectInputStream(new FileInputStream(userFile));
                        user = (User) mObjectInputStream.readObject();
                        Log.e(TAG, "run:  name: "+user.mUserName+"     ---    age:  "+user.mAge);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        try {
                            mObjectInputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }
}
