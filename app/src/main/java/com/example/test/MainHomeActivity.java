package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
        mUnbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @OnClick({R.id.tv_cardview, R.id.tv_toolbar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cardview:
                startActivity(new Intent(MainHomeActivity.this,MainActivity.class));
                break;
            case R.id.tv_toolbar:
                startActivity(new Intent(MainHomeActivity.this,ToolBarActivity.class));
                break;
        }
    }
}
