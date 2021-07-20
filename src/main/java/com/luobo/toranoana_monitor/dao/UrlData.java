package com.luobo.toranoana_monitor.dao;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UrlData {
    private int index;
    private int id;
    private String date;
    private String urlStr;
    private String imgUrlStr;
    private boolean isValid;
    private boolean isKey;
    private boolean isPassword;

    public UrlData(int index, int id, String date, String urlStr, String imgUrlStr){
        this.index = index;
        this.id = id;
        this.urlStr = urlStr;
        this.imgUrlStr = imgUrlStr;
        this.date = date;
        isValid = false;
        isKey = false;
        isPassword = false;
    }
}
