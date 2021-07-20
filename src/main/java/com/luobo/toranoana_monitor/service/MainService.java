package com.luobo.toranoana_monitor.service;

import com.luobo.toranoana_monitor.dao.UrlDataDao;
import com.luobo.toranoana_monitor.param.Param;
import com.luobo.toranoana_monitor.util.SaveProcess;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
@Setter
@Slf4j
public class MainService {
    RunnerService runnerService;

    /**
     * 通过前端开启
     * @param startPoint 6为起点id
     * @param range 范围
     */
    @Async
    public void runService(int startPoint, int range){
        runnerService = new RunnerService();
        log.info(" 开始生成Url类 id范围: "+ startPoint + " ~ "+ (startPoint+range));
        for (int i = 0; i < range; i++) {
            UrlDataDao.getUrlDataDao().add(startPoint + i);
        }
        runnerService.initScan();
    }

    /**
     * 通过存档开启
     * @param file 存档文件
     */
    @Async
    public void runService(File file){
        runnerService = new RunnerService();
        log.info(" 开始读取存档 "+ file.getName());
        List<String[]> save = SaveProcess.getSaveProcess().loadSaveFile(file);
        if(!save.isEmpty()){
            log.info(" 发现现有存档，其中可能包含 " + save.size() +" 条无效链接");
            if(SaveProcess.getSaveProcess().saveFormatCheck(save)) {
                SaveProcess.getSaveProcess().loadSave(save);
                runnerService.Scan();
            }else {
                log.info(" 文档格式错误！");
            }
        }else{
            log.info(" 存档为空");
        }
    }

    /**
     * 确认是否有扫描在运行 是则关闭扫描
     */
    public void shutDown(){
        try{
            if(runnerService.isRunning()){
                log.info("关闭线程中...");
                runnerService.shutDown();
            }
        }catch (Exception e){
            log.info(" 目前没有扫描在运行");
        }
    }
}
