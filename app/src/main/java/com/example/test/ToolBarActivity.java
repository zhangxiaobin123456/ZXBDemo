package com.example.test;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ToolBarActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawer)
    DrawerLayout mDrawer;
    @BindView(R.id.tv_left)
    TextView mTvLeft;
    private Unbinder mUnbinder;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_bar);
        mUnbinder = ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        //标题的文字需在setSupportActionBar之前，不然会无效
        mToolbar.setTitle("ToolBar");
        setSupportActionBar(mToolbar);

        mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar,
                R.string.abc_action_bar_home_description,
                R.string.abc_action_bar_home_description);
        mDrawerToggle.syncState();
        mDrawer.setDrawerListener(mDrawerToggle);


        // 菜单的监听可以在toolbar里设置，
        // 也可通过Activity的onOptionsItemSelected回调方法来处理
        mToolbar.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_settings:
                                Toast.makeText(ToolBarActivity.this,
                                        "action_settings", Toast.LENGTH_SHORT).show();
                                break;

                            case R.id.action_share:
                                Toast.makeText(ToolBarActivity.this,
                                        "action_share", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }
                        return true;
                    }
                });

        //空格
        //再次添加
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @OnClick(R.id.tv_left)
    public void onViewClicked() {
        mDrawer.closeDrawer(Gravity.LEFT);
    }
}
