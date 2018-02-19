package com.example.interpark.myapp;

/**
 * Created by hyohyeon on 2018-02-12.
 */

public class UserVO {
    //id TEXT PRIMARY KEY, name TEXT, passwd TEXT, phone TEXT);
    private String id;
    private String name;
    private String passwd;
    private String phone;

    public UserVO() {
        this.id = id;
        this.name = name;
        this.passwd = passwd;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
