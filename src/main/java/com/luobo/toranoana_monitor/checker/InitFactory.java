package com.luobo.toranoana_monitor.checker;

import com.luobo.toranoana_monitor.dao.UrlData;
import com.luobo.toranoana_monitor.framework.Checker;
import com.luobo.toranoana_monitor.framework.CheckerFactory;

public class InitFactory extends CheckerFactory {
    @Override
    protected Checker createChecker(UrlData urlData) {
        return new InitChecker(urlData);
    }
}
