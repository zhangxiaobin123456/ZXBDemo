package com.example.test.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Description:
 */

public class DBSqlitOpenHelper extends SQLiteOpenHelper{

    private static String DBName = "book.db";
    public static final  String bookTableName = "book";
    public static final  String userTableName = "user";
    private static int mVersion = 1;



    private static DBSqlitOpenHelper instance = null;
    private DBSqlitOpenHelper(Context context) {
        super(context, DBName, null,mVersion);
    }
    public static DBSqlitOpenHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (DBSqlitOpenHelper.class) {
                if (instance == null)
                    instance = new DBSqlitOpenHelper(context);
            }
        } return instance;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String bookSql = "create table if not exists "+bookTableName+" (_id integer primary key autoincrement, name nchar,price nchar)";
        String userSql = "create table if not exists "+userTableName+" (_id integer primary key autoincrement, name nchar,age nchar)";
        db.execSQL(bookSql);
        db.execSQL(userSql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
