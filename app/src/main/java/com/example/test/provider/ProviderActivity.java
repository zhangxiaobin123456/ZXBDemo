package com.example.test.provider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.test.R;
import com.example.test.aidl.Book;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProviderActivity extends AppCompatActivity {

    private static final String TAG = "ProviderActivity";
    @BindView(R.id.tv_add)
    TextView mTvAdd;
    @BindView(R.id.tv_del)
    TextView mTvDel;
    @BindView(R.id.tv_update)
    TextView mTvUpdate;
    @BindView(R.id.tv_query)
    TextView mTvQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);
        ButterKnife.bind(this);


        initData();
    }

    private void initData() {
        Uri bookUri = Uri.parse("content://com.example.test.provider.BookContentProvider" + "/book");

        ContentValues values = new ContentValues();
        values.put("name", "西游记");
        values.put("price", "199");
        //将西游记这本书添加到数据库中
        getContentResolver().insert(bookUri, values);
        Cursor bookCursor = getContentResolver().query(bookUri, new String[]{"_id", "name", "price"}, null, null, null);
        while (bookCursor.moveToNext()) {
            Book book = new Book();
            book.setBookId(bookCursor.getInt(0));
            book.setBookName(bookCursor.getString(1));
            book.setPrice(bookCursor.getString(2));

            Log.e(TAG, "Book: bookName ————" + book.getBookName() + "|||  bookId ————" + book.getBookId() + "  |||  price ————" + book.getPrice());
        }
        bookCursor.close();


        Uri userUri = Uri.parse("content://com.example.test.provider.BookContentProvider" + "/user");
        ContentValues userValues = new ContentValues();
        userValues.put("_id",1);
        userValues.put("name", "孙悟空");
        userValues.put("age", 18);
        //将孙悟空这个用户添加到数据库中
        getContentResolver().insert(userUri, userValues);
        Cursor userCursor = getContentResolver().query(userUri, new String[]{"_id", "name", "age"}, null, null, null);
        while (userCursor.moveToNext()) {
            User user = new User();

            user.setUserId(userCursor.getInt(0));
            user.setUserName(userCursor.getString(1));
            user.setAge(userCursor.getInt(2));

            Log.e(TAG, "User: userName ————" + user.getUserName() + "  |||  age ————" + user.getAge() + "|||  userId ————" + user.getUserId());
        }
        userCursor.close();

    }

    @OnClick({R.id.tv_add, R.id.tv_del, R.id.tv_update, R.id.tv_query})
    public void onViewClicked(View view) {
        Uri userUri = Uri.parse("content://com.example.test.provider.BookContentProvider" + "/user");

        switch (view.getId()) {
            case R.id.tv_add:
                ContentValues userValues = new ContentValues();
                userValues.put("_id",2);
                userValues.put("name", "沙和尚");
                userValues.put("age", 20);
                //沙和尚
                getContentResolver().insert(userUri, userValues);

                break;
            case R.id.tv_del:
                //将id为2的这个用户删除
                getContentResolver().delete(userUri,"_id=?", new String[]{String.valueOf(2)});
                break;
            case R.id.tv_update:
                ContentValues values = new ContentValues();
                values.put("age", 30);
                //将id为2的这个用户修改年龄为30
                getContentResolver().update(userUri,values,"_id=?", new String[]{String.valueOf(2)});
                break;
            case R.id.tv_query:
                Cursor userCursor = getContentResolver().query(userUri, new String[]{"_id", "name", "age"}, null, null, null);
                while (userCursor.moveToNext()) {
                    User user = new User();
                    user.setUserId(userCursor.getInt(0));
                    user.setUserName(userCursor.getString(1));
                    user.setAge(userCursor.getInt(2));

                    Log.e(TAG, "User: userName ————" + user.getUserName() + "  |||  age ————" + user.getAge() + "|||  userId ————" + user.getUserId());
                }
                userCursor.close();
                break;
        }
    }
}
