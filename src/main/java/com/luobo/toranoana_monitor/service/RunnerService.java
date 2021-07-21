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
        checkerFactory.shutDown();
        log.info(" 初次扫描结束 发现有效: " + UrlDataDao.getUrlDataDao().getAllValid().size()
                + " 无效: " + UrlDataDao.getUrlDataDao().getAllInvalid().size());
        FileProcess.getFileProcess().exportAllUrl();
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
            int size = UrlDataDao.getUrlDataDao().getAllInvalid().size();
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
            if(size == UrlDataDao.getUrlDataDao().getAllInvalid().size())
                FileProcess.getFileProcess().exportAllUrl(); //如果无变更则不写入
        }
        shutDown();
    }

    //TODO 好像又没有bug了？
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
