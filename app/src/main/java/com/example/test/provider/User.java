package com.example.test.provider;

import java.io.Serializable;

/**
 * Description:
 */

public class User implements Serializable{
    private int userId;
    private String userName;
    private int age;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName == null ? "" : userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return  age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
