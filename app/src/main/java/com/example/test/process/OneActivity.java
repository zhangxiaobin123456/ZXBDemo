package com.example.test.process;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.test.R;
import com.example.test.aidl.AidlActivity;
import com.example.test.bean.User;
import com.example.test.utils.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OneActivity extends AppCompatActivity {

    @BindView(R.id.tv_totwo)
    TextView mTvTotwo;
    private static final String TAG = "OneActivity";
    @BindView(R.id.tv_messenger)
    TextView mTvMessenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        ButterKnife.bind(this);

        Log.e(TAG, User.mUserName);

        wiriteToFile();
    }

    /**
     * 写入文件
     */
    private void wiriteToFile() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                User user = new User("zxb", 18);
                File file = new File(Constants.PATH);
                if (!file.exists()) {
                    file.mkdirs();
                }
                File userFile = new File(Constants.PATH + "user.txt");
                ObjectOutputStream mObjectOutputStream = null;

                try {
                    mObjectOutputStream = new ObjectOutputStream(new FileOutputStream(userFile));
                    mObjectOutputStream.writeObject(user);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        mObjectOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


    }

    @OnClick({R.id.tv_totwo, R.id.tv_messenger,R.id.tv_aidl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_totwo:
                startActivity(new Intent(OneActivity.this, TwoActivity.class));

                break;
            case R.id.tv_messenger:
                startActivity(new Intent(OneActivity.this, MessengerActivity.class));
                break;
            case R.id.tv_aidl:
                startActivity(new Intent(OneActivity.this, AidlActivity.class));
                break;
        }
    }
}
