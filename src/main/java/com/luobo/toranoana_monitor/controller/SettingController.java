package com.luobo.toranoana_monitor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SettingController {

    @RequestMapping("/setting")
    public String setting(){
        return "setting";
    }
}
