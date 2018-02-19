package com.example.interpark.myapp;

/**
 * Created by hyohyeon on 2018-02-12.
 */

public class DiaryVO {
    /*dno     INTEGER PRIMARY KEY AUTOINCREMENT,
    ddate   TEXT,
    dtitle TEXT,
    dimgpath TEXT,
    dcontent text,
    idpasswd TEXT,
    phone TEXT,
    id TEXT,
    FOREIGN KEY(id) REFERENCES user(id)*/
    private String ddate;
    private String dtitle;
    private String dimgpath;
    private String dcontent;
    private String id;
    private String no;

    public DiaryVO() {
        this.ddate = ddate;
        this.dtitle = dtitle;
        this.dimgpath = dimgpath;
        this.dcontent = dcontent;
        this.id = id;
        this.no = no;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getDdate() {
        return ddate;
    }

    public void setDdate(String ddate) {
        this.ddate = ddate;
    }

    public String getDtitle() {
        return dtitle;
    }

    public void setDtitle(String dtitle) {
        this.dtitle = dtitle;
    }

    public String getDimgpath() {
        return dimgpath;
    }

    public void setDimgpath(String dimgpath) {
        this.dimgpath = dimgpath;
    }

    public String getDcontent() {
        return dcontent;
    }

    public void setDcontent(String dcontent) {
        this.dcontent = dcontent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
