package com.luobo.toranoana_monitor.checker;

import com.luobo.toranoana_monitor.dao.UrlData;
import com.luobo.toranoana_monitor.framework.Checker;
import com.luobo.toranoana_monitor.util.BeepMaker;
import com.luobo.toranoana_monitor.util.BrowserOpener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NormalChecker extends Checker {

    public NormalChecker(UrlData urlData) {
        super(urlData);
    }

    @Override
    protected void add2List(UrlData urlData){
        super.add2List(urlData);
        if(urlData.isValid()) {
            if(param.isBeeping())
                BeepMaker.getBeepMaker().beeping();
            if(param.isPopups())
                BrowserOpener.getBrowserOpener().openBrowse(urlData.getUrlStr());
            BeepMaker.getBeepMaker().beeping();
        }
    }
}
