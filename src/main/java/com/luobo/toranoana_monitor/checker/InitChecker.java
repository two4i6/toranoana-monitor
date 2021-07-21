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
}
