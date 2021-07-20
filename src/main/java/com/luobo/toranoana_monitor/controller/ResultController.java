package com.luobo.toranoana_monitor.controller;

import com.luobo.toranoana_monitor.dao.UrlData;
import com.luobo.toranoana_monitor.dao.UrlDataDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Slf4j
@Controller
public class ResultController {
    @RequestMapping("/result/valid")
    public ModelAndView result(){
        return getModelAndView(UrlDataDao.getUrlDataDao().getAllValid().stream().toList());
    }

    @RequestMapping("/result/valid/auto")
    public ModelAndView resultAuto(){
        return getModelAndView(UrlDataDao.getUrlDataDao().getAllValid().stream().toList());
    }

    @RequestMapping("/result/filtered")
    public ModelAndView filtered(){
        return getModelAndView(UrlDataDao.getUrlDataDao().getAllFiltered().stream().toList());
    }

    private ModelAndView getModelAndView(List<UrlData> urlDataList){
        ModelAndView modelAndView = new ModelAndView();
        try{
            modelAndView.addObject("urls", urlDataList);
        }catch (Exception e){
            log.info("数据库为空的请开始新的扫描！");
        }
        modelAndView.setViewName("result");
        return modelAndView;
    }
}
