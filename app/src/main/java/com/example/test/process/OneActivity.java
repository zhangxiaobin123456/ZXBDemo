package com.example.test.process;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.test.MainHomeActivity;
import com.example.test.R;
import com.example.test.bean.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OneActivity extends AppCompatActivity {

    @BindView(R.id.tv_totwo)
    TextView mTvTotwo;
    private static final String TAG = "OneActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        ButterKnife.bind(this);

        Log.e(TAG,User.mUserName);
    }

    @OnClick(R.id.tv_totwo)
    public void onViewClicked() {
        startActivity(new Intent(OneActivity.this,TwoActivity.class));
    }
}
