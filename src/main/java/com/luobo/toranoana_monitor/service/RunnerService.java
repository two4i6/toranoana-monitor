package com.luobo.toranoana_monitor.service;

import com.luobo.toranoana_monitor.checker.NormalFactory;
import com.luobo.toranoana_monitor.checker.QuietFactory;
import com.luobo.toranoana_monitor.dao.UrlData;
import com.luobo.toranoana_monitor.dao.UrlDataDao;
import com.luobo.toranoana_monitor.framework.CheckerFactory;
import com.luobo.toranoana_monitor.param.Param;
import com.luobo.toranoana_monitor.util.FileProcess;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Setter
@Slf4j
public class RunnerService {
    private boolean isShutDown;
    private CheckerFactory checkerFactory;

    public void initScan(){
        checkerFactory = new QuietFactory();
        for (UrlData urlData : UrlDataDao.getUrlDataDao().getAllInvalid()) {
            if (isShutDown)
                break;
            if (!urlData.isValid())
                checkerFactory.create(urlData);
            if (Param.getParam().isDebug())
                log.info("启动线程id: " + (urlData.getIndex() + 1)
                        + " 无效: " + UrlDataDao.getUrlDataDao().getAllInvalid().size()
                        + " 有效: " + UrlDataDao.getUrlDataDao().getAllValid().size());
        }
        try {
            if (Param.getParam().isDebug())
                log.info("轮次完成 进入等待...");
            FileProcess.getFileProcess().exportAllUrl(); //写入存档
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            log.info("轮次等待错误");
        }
        shutDown();
        log.info(" 初次扫描结束 发现有效: " + UrlDataDao.getUrlDataDao().getAllValid().size()
                + " 无效: " + UrlDataDao.getUrlDataDao().getAllInvalid().size());
        Scan();
    }

    /**
     *
     */
    public void Scan() {
        checkerFactory = new NormalFactory();
        setShutDown(false);
        log.info("开始扫描 " + UrlDataDao.getUrlDataDao().getAllInvalid().size() + " 条无效链接");
        here:
        while (!UrlDataDao.getUrlDataDao().allValid()) {
            for (UrlData urlData : UrlDataDao.getUrlDataDao().getAllInvalid()) {
                if (isShutDown)
                    break here;
                if (!urlData.isValid())
                    checkerFactory.create(urlData);
                if (Param.getParam().isDebug())
                    log.info("启动线程id: " + (urlData.getIndex() + 1)
                            + " 无效: " + UrlDataDao.getUrlDataDao().getAllInvalid().size()
                            + " 有效: " + UrlDataDao.getUrlDataDao().getAllValid().size());
            }
            try {
                if (Param.getParam().isDebug())
                    log.info("轮次完成 进入等待...");
                FileProcess.getFileProcess().exportAllUrl(); //写入存档
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                log.info("轮次等待错误");
            }
        }
        shutDown();
    }

    public void shutDown() {
        try{
            setShutDown(true);
            checkerFactory.shutDown();
        }catch (Exception e){
            log.info(" 目前没有扫描在运行");
        }
    }

    public boolean isRunning(){
        return checkerFactory.isRunning();
    }
}
