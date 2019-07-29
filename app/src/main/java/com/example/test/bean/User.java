package com.example.test.bean;

import java.io.Serializable;

/**
 * Description:
 */

public class User implements Serializable{
    public static final long Uid = 1L;
    public static String mUserName = "大傻瓜";
    public static int mAge = 18;

    public User(String userName,int age) {
        mUserName = userName;
        mAge = age;
    }

}
