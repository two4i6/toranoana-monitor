package com.luobo.toranoana_monitor.checker;

import com.luobo.toranoana_monitor.dao.UrlData;
import com.luobo.toranoana_monitor.framework.Checker;
import com.luobo.toranoana_monitor.framework.CheckerFactory;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class NormalFactory extends CheckerFactory {
    @Override
    protected Checker createChecker(UrlData urlData) {
        return new NormalChecker(urlData);
    }
}
