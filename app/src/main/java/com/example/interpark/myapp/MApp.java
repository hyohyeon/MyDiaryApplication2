package com.example.interpark.myapp;

import android.app.Application;
import android.content.Context;

/**
 * Created by hyohyeon on 2018-02-12.
 */

public class MApp extends Application{
    private Context currentContext;


    private String userId;
    private String userName;
    private String userPhone;

    public Context getCurrentContext() {
        return currentContext;
    }

    public void setCurrentContext(Context currentContext) {
        this.currentContext = currentContext;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
}
