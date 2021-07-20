package com.luobo.toranoana_monitor.checker;

import com.luobo.toranoana_monitor.dao.UrlData;
import com.luobo.toranoana_monitor.framework.Checker;
import com.luobo.toranoana_monitor.util.BrowserOpener;
import com.luobo.toranoana_monitor.util.ElementChecker;
import lombok.extern.slf4j.Slf4j;

import java.net.HttpURLConnection;

@Slf4j
public class InitChecker extends Checker {
    public InitChecker(UrlData urlData) {
        super(urlData);
    }

    @Override
    protected void connectionProcess(HttpURLConnection connection) {
        super.connectionProcess(connection);
        ElementChecker elementChecker = new ElementChecker(connection);
        if (ResponseCode == 200) {
            log.info(urlData.getUrlStr() + " 有效");
            urlData.setValid(true);
            if (elementChecker.isPassword()) {
                urlData.setPassword(true);
                log.info(urlData.getUrlStr() + " 发现密码");
            }
            if (param.isPasswordScan()) {
                if (elementChecker.isKey(param.getSearchKeys())) {
                    urlData.setKey(true);
                    log.info(urlData.getUrlStr() + " 发现tag");
                }
            }
        }
        add2List(urlData);
        cleanMem(connection);
    }
}
