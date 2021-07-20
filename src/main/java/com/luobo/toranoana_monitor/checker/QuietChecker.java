package com.luobo.toranoana_monitor.checker;

import com.luobo.toranoana_monitor.dao.UrlData;
import com.luobo.toranoana_monitor.framework.Checker;

/**
 * 安静扫描模式 不会发出声音与弹窗 用于初次扫描
 */
public class QuietChecker extends Checker {
    public QuietChecker(UrlData urlData) {
        super(urlData);
    }
}
