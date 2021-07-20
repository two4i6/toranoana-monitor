package com.luobo.toranoana_monitor.util;

import lombok.extern.slf4j.Slf4j;
import java.awt.*;
import java.net.URI;

@Slf4j
public class BrowserOpener{
    private static final BrowserOpener browserOpener = new BrowserOpener();
    /**
     * 使用默认浏览器打开
     * @param url 要打开的网址
     */
    public void openBrowse(String url){
        try {
            Desktop desktop = Desktop.getDesktop();
            URI oURL = new URI(url);
            desktop.browse(oURL);
        } catch (Exception e) {
            log.info("弹窗错误" + e.getMessage());
        }
    }

    public static BrowserOpener getBrowserOpener() {
        return browserOpener;
    }
}
