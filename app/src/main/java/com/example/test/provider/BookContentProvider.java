package com.example.test.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Description:
 */

public class BookContentProvider extends ContentProvider {
    private static final String TAG = "BookContentProvider";
    private static final String authority = "com.example.test.provider.BookContentProvider";
    private static final Uri BOOKURI = Uri.parse("content://"+authority+"/book");
    private static final Uri USERURI = Uri.parse("content://"+authority+"/user");
    private static final int BOOKCODE = 0;
    private static final int USERCODE = 1;
    private static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        mUriMatcher.addURI(authority,"book",BOOKCODE);
        mUriMatcher.addURI(authority,"user",USERCODE);
    }

    private Context mContext;
    private SQLiteDatabase mDB;

    @Override
    public boolean onCreate() {
        mContext = getContext();
        Log.d(TAG, "onCreate: threadName ---"+Thread.currentThread().getName());
        initData();
        return true;
    }

    private void initData() {
        mDB = DBSqlitOpenHelper.getInstance(mContext).getWritableDatabase();
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.d(TAG, "query: threadName ---"+Thread.currentThread().getName());
        //查询数据
        String tableName = getTableName(uri);
        if (tableName == null) {
            throw new IllegalArgumentException("错误错误。Unsupported URI: "+uri);
        }
        return mDB.query(tableName,projection,selection,selectionArgs,null,null,sortOrder,null);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        Log.d(TAG, "getType: ");
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.d(TAG, "insert: ");
        //添加数据
        String tableName = getTableName(uri);
        if (tableName == null) {
            throw new IllegalArgumentException("错误错误。Unsupported URI: "+uri);
        }
        mDB.insert(tableName,null,values);
        mContext.getContentResolver().notifyChange(uri,null);
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG, "delete: ");
        //删除数据
        String tableName = getTableName(uri);
        if (tableName == null) {
            throw new IllegalArgumentException("错误错误。Unsupported URI: "+uri);
        }
        int count = mDB.delete(tableName, selection, selectionArgs);
        if (count > 0) {
            mContext.getContentResolver().notifyChange(uri,null);
        }
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG, "update: ");
        //修改数据
        String tableName = getTableName(uri);
        if (tableName == null) {
            throw new IllegalArgumentException("错误错误。Unsupported URI: "+uri);
        }
        int row = mDB.update(tableName, values,selection, selectionArgs);
        if (row > 0) {
            mContext.getContentResolver().notifyChange(uri,null);
        }
        return row;
    }

    public String getTableName(Uri uri){
        String tableName = null;
        switch (mUriMatcher.match(uri)){
            case BOOKCODE:
                tableName = DBSqlitOpenHelper.bookTableName;
                break;
            case USERCODE:
                tableName = DBSqlitOpenHelper.userTableName;
                break;
                default:
                    break;
        }
        return tableName;
    }
}
