package com.luobo.toranoana_monitor.controller;

import com.luobo.toranoana_monitor.dao.UrlData;
import com.luobo.toranoana_monitor.dao.UrlDataDao;
import com.luobo.toranoana_monitor.param.Param;
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
        if(Param.getParam().isSearchKey())
            return getModelAndView(UrlDataDao.getUrlDataDao().getAllFilteredValid().stream().toList());
        else
            return getModelAndView(UrlDataDao.getUrlDataDao().getAllValid().stream().toList());
    }

    @RequestMapping("/result/filtered")
    public ModelAndView filtered(){
        return getModelAndView(UrlDataDao.getUrlDataDao().getAllFiltered().stream().toList());
    }

    @RequestMapping("/result/invalid")
    public ModelAndView inValid(){
        return getModelAndView(UrlDataDao.getUrlDataDao().getAllInvalid().stream().toList());
    }

    private ModelAndView getModelAndView(List<UrlData> urlDataList){
        ModelAndView modelAndView = new ModelAndView();
        try{
            modelAndView.addObject("urls", urlDataList);
        }catch (Exception e){
            log.info("数据库为空的请开始新的扫描！");
        }
        modelAndView.setViewName("index");
        return modelAndView;
    }
}
