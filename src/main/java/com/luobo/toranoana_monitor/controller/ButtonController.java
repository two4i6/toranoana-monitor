package com.luobo.toranoana_monitor.controller;

import com.luobo.toranoana_monitor.param.Param;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class ButtonController {
    @RequestMapping(value = {"/button"})
    public String handlePost(@RequestParam String action) {
        switch (action){
            case"停止扫描":
                //checkerFactory.shutDown(param);
                break;
            case"返回主页":
                return "redirect:/";
            case"返回结果":
                return "redirect:/result/valid";
            case"自动刷新":
                return "redirect:/result/valid/auto";
            case"手动刷新":
                return "redirect:/result/valid";
            case"过滤列表":
                return "redirect:/result/filtered";
            case"debug":
                if(!Param.getParam().isDebug()) {
                    log.info(" debug模式开启");
                    Param.getParam().setDebug(true);
                }else {
                    log.info(" debug模式关闭");
                    Param.getParam().setDebug(false);
                }
                return "redirect:/";
            default:
                return null;
        }
        return "redirect:/result/valid";
    }
}
