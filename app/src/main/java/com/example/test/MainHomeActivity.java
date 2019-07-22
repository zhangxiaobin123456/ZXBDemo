package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.test.bean.User;
import com.example.test.process.OneActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainHomeActivity extends AppCompatActivity {

    @BindView(R.id.tv_cardview)
    TextView mTvCardview;
    @BindView(R.id.tv_toolbar)
    TextView mTvToolbar;
    private Unbinder mUnbinder;

    private static final String TAG = "MainHomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
        mUnbinder = ButterKnife.bind(this);

        User.mUserName = "聪明鬼";
        Log.e(TAG,User.mUserName);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @OnClick({R.id.tv_cardview, R.id.tv_toolbar,R.id.tv_process})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cardview:
                startActivity(new Intent(MainHomeActivity.this,MainActivity.class));


                Intent intent = new Intent(MainHomeActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                break;
            case R.id.tv_toolbar:
                startActivity(new Intent(MainHomeActivity.this,ToolBarActivity.class));
                break;
            case R.id.tv_process:
                startActivity(new Intent(MainHomeActivity.this,OneActivity.class));
                break;
        }
    }
}
