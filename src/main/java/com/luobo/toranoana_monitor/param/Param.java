package com.luobo.toranoana_monitor.param;

import com.luobo.toranoana_monitor.util.HttpHeader;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

/**
 * param类存储着一些程序需要用的设置变量
 * 单例模式生成param
 */
public class Param {
    private static Param param = new Param();
    private HttpHeader httpHeader = HttpHeader.getHttpHeader();

    private int port = 8080;
    private int waitingTime = 300; //间隔延迟
    private int suspendTime = 300000;
    private int threadLimit = 200;
    private String urlStr = "https://ec.toranoana.jp/joshi_r/ec/item/";
    private String imgUrlStr = "https://ecdnimg.toranoana.jp/ec/img/";
    private String invalidFileName = "invalid_links.csv";
    private String validFileName = "valid_links.csv";
    private String FilteredFileName = "filtered_lines.csv";
    private String idA = "040030";

    //一些可以在前端设置的参数
    private boolean beeping = true;
    private boolean popups = true;
    private boolean debug = false;

    private boolean passwordScan;
    private boolean banned;
    private String searchKeys;
    private boolean searchKey;
    private boolean pause; //实现暂停功能

    public void SetupParam(boolean passwordScan, String searchKeys, boolean searchKey){
        this.passwordScan = passwordScan;
        this.searchKeys = searchKeys;
        this.searchKey = searchKey;
    }

    public static Param getParam() {
        return param;
    }
}
