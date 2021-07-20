package com.luobo.toranoana_monitor.controller;

import com.luobo.toranoana_monitor.dao.UrlDataDao;
import com.luobo.toranoana_monitor.param.Param;
import com.luobo.toranoana_monitor.service.MainService;
import com.luobo.toranoana_monitor.util.Util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;

@Slf4j
@Controller
public class IndexController {

    @Autowired
    MainService mainService;

    /**
     * 主页
     * @param model 返回前端的一些字符串
     * @return menu.html
     */
    @RequestMapping(value = {"/","/index.html"})
    public String index(Model model) {
        if (UrlDataDao.getUrlDataDao().isEmpty()) {
            model.addAttribute("Msg", "扫描进行中");
            model.addAttribute("errorMsg", "扫描进行中");
        }
        if (Param.getParam().isDebug())
            model.addAttribute("errorMsg", "debug模式");
        return "menu";
    }

    /**
     * 获取搜索主要参数
     * @param startPoint 起始点
     * @param range 范围
     * @param tag   标签
     * @param delay 延迟
     * @param model 返回前端的数据
     * @return  重新导向到 /result/valid_auto页面
     */
    @RequestMapping(value = {"/result/process"})
    public String result(@RequestParam("startPoint") String startPoint,
                         @RequestParam("range") String range,
                         @RequestParam("tag") String tag,
                         @RequestParam("delay") String delay,
                         Model model) {

        mainService.shutDown();
        setParam(tag);
        setDelay(delay);
        if(isLoadSave(startPoint,range)){
            return "redirect:/result/valid/auto";
        }else if(paramChecker(startPoint, range)){
            return "redirect:/result/valid/auto";
        }else{ //输入错误
            model.addAttribute("errorMsg", "输入错误！请检查！");
        }
        return "redirect:/";
    }

    /**
     * 设置搜索标签tag
     * @param tag 用户输入的标签
     */
    private void setParam(String tag){
        if(!tag.isEmpty()){
            log.info(" 搜素标签: "+tag);
            Param.getParam().setSearchKeys(tag);
            Param.getParam().setSearchKey(true);
        }
    }

    /**
     * 设置延迟参数
     * @param delay 用户输入的延迟
     */
    private void setDelay(String delay){
        if(!delay.isEmpty()){
            try {
                Param.getParam().setWaitingTime(Integer.parseInt(delay));
                log.info(" 延迟设置为:\t" + Param.getParam().getWaitingTime() + "ms");
            }catch (Exception e){
                log.info("请检查输入的延迟是否为数字" + e.getMessage());
            }
        }
    }

    /**
     * 决定是否读取存档
     * @param startPoint 起始值
     * @param range 范围值
     * @return 如果都为控则读取存档
     */
    private boolean isLoadSave(String startPoint, String range){
        if(startPoint.isEmpty() && range.isEmpty()) {
            mainService.runService(new File(Param.getParam().getInvalidFileName()));
            return true;
        }
        return false;
    }

    /**
     * 检测范围与起始是否有效
     * @param startPoint 起始点
     * @param range 范围
     * @return 有效返回true 无效返回false
     */
    private boolean paramChecker(String startPoint, String range){
        try{
            if(Util.getUtil().idFormatCheck(Integer.parseInt(startPoint)) || Integer.parseInt(range) > 100000 ){
                log.info(" 请检查并重新输入起始id！");
                return false;
            }else{
                mainService.runService(Integer.parseInt(startPoint), Integer.parseInt(range));
            }
        }catch (Exception e){
            log.info("请检查输入的起点与范围是否为数字" + e.getMessage());
        }
        return true;
    }
}
