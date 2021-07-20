package com.luobo.toranoana_monitor.framework;

import com.luobo.toranoana_monitor.dao.UrlData;
import com.luobo.toranoana_monitor.param.Param;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public abstract class CheckerFactory {
    protected ExecutorService exe = Executors.newFixedThreadPool(Param.getParam().getThreadLimit());
    public final Checker create(UrlData urlData){
        Checker checker = createChecker(urlData);
        runChecker(registerChecker(checker));
        return checker;
    }

    protected abstract Checker createChecker(UrlData urlData);

    protected Thread registerChecker(Checker checker){
        return new Thread(checker);
    }

    protected void runChecker(Thread thread) {
        try {
            exe.execute(thread);
            Thread.sleep(new Random().nextInt(Param.getParam().getWaitingTime())); //TODO 移到checker类中执行
        } catch (Exception x){
            shutDown();
            log.info( "新建线程故障, 关闭线程池... 请重新开始搜索");
        }
    }
    /**
     * 停止所有线程 并清空线程池
     */
    public void shutDown(){
        exe.shutdown(); //线程池结束
        log.info(" 尝试关闭所有线程...");
        while (true) {
            if (exe.isTerminated())
                break;
        }
        log.info(" 全部线程已结束，线程池清空");
    }

    /**
     * 用于检查线程库是否终结
     */
    public Boolean isRunning(){
        try{
            return !exe.isTerminated();
        }catch(Exception e){
            log.info(" 线程池在使用中");
            return false;
        }
    }
}
