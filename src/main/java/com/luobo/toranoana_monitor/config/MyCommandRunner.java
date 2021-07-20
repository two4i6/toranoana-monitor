package com.luobo.toranoana_monitor.config;

import com.luobo.toranoana_monitor.param.Param;
import com.luobo.toranoana_monitor.util.BrowserOpener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

/**
 * spring boot 容器加载后自动监听
 */
@Configuration
@Slf4j
public class MyCommandRunner implements CommandLineRunner {
    @Override
    public void run(String... args) {
        log.info("启动成功： 即将自动开启开启web页面！如未成功开启请访问 http://localhost:"+ Param.getParam().getPort());
        BrowserOpener.getBrowserOpener().openBrowse("http://localhost:"+ Param.getParam().getPort());
    }
}