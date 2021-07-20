package com.luobo.toranoana_monitor.util;

import lombok.extern.slf4j.Slf4j;

import java.awt.*;

@Slf4j
public class BeepMaker {
    private static final BeepMaker beepMaker = new BeepMaker();
    public void beeping(){
        try {
            Toolkit.getDefaultToolkit().beep();
        }catch (Exception e){
            log.info(" 提示音错误" + e.getMessage());
        }
    }
    public static BeepMaker getBeepMaker() {
        return beepMaker;
    }
}
