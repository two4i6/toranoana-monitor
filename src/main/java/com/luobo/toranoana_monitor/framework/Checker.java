package com.luobo.toranoana_monitor.framework;

import com.luobo.toranoana_monitor.dao.UrlData;
import com.luobo.toranoana_monitor.dao.UrlDataDao;
import com.luobo.toranoana_monitor.param.Param;
import com.luobo.toranoana_monitor.util.ElementChecker;
import com.luobo.toranoana_monitor.util.ImgCache;
import lombok.extern.slf4j.Slf4j;

import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public abstract class Checker implements Runnable{
    protected Param param = Param.getParam();
    protected UrlData urlData;
    protected int ResponseCode;

    public Checker(UrlData urlData){
        this.urlData = urlData;
    }

    @Override
    public void run(){
        String SUBMIT_METHOD_GET = "GET";
        HttpURLConnection connection;
        try {
            URL url = new URL(urlData.getUrlStr()); // 创建远程url连接对象
            connection = (HttpURLConnection) url.openConnection(); // 通过远程url连接对象打开一个连接，强转成httpURLConnection类
            connection.setRequestMethod(SUBMIT_METHOD_GET); // 设置连接方式：GET
            connection.setConnectTimeout(15000); // 设置连接主机服务器的超时时间：15000毫秒
            connection.setReadTimeout(60000); // 设置读取远程返回的数据时间：60000毫秒
            // 模拟浏览器行为
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "close");
            connection.setRequestProperty("user-agent", param.getHttpHeader().getHeader());
            connectionProcess(connection);
        }catch (Exception e){
            log.info(e.getMessage());
        }
    }

    protected void connectionProcess(HttpURLConnection connection){
        try{
            connection.connect();
            ResponseCode = connection.getResponseCode();
            if(ResponseCode == 403){
                log.info(ResponseCode
                        + "id: " + urlData.getId()
                        + " 被ban！此线程将暂停5分钟！");
                suspendProcess(connection, param.getSuspendTime());
            }else if(ResponseCode == 404){
                urlData.setValid(false);
                if(param.isDebug())
                    log.info(urlData.getUrlStr() + " 无效");
            }
            ElementChecker elementChecker = new ElementChecker(connection);
            if (ResponseCode == 200) {
                log.info(urlData.getUrlStr() + " 有效");
                urlData.setValid(true);
                if (elementChecker.isPassword()) {
                    urlData.setPassword(true);
                    log.info(urlData.getUrlStr() + " 发现密码");
                }
                if (param.isPasswordScan()) {
                    if (elementChecker.isKey(param.getSearchKeys())) {
                        urlData.setKey(true);
                        log.info(urlData.getUrlStr() + " 发现tag");
                    }
                }
            }
            add2List(urlData);
            cleanMem(connection);
        }catch (Exception e){
            log.info(e.getMessage()
                    +"id: " +  urlData.getId()
                    +" 网络开小差！此线程将暂停5分钟！");
            suspendProcess(connection, param.getSuspendTime()/2);
        }
    }

    /**
     * 处理特殊情况 如403或网络错误
     * @param connection HTTP链接
     * @param suspendTime 等待时间
     */
    protected void suspendProcess(HttpURLConnection connection, int suspendTime){
        cleanMem(connection); //释放内存
        try{
            Thread.sleep(suspendTime);
            log.info(" 等待结束 \t网页id:" + urlData.getId() + " 重新开始\t");
        }catch (Exception e){
            log.info(e.getMessage()
                    + "id: " + urlData.getId()
                    + " 暂停操作出现故障！");
        }
    }

    /**
     * 把新的urlData加入列表
     * @param urlData 新生成的urlData
     */
    protected void add2List(UrlData urlData){
        if(urlData.isValid()){
            UrlDataDao.getUrlDataDao().update(urlData);
            new Thread(new ImgCache(urlData.getImgUrlStr(), urlData.getId())).start();
        }
    }

    /**
     * 关闭链接
     * @param connection HTTP链接
     */
    protected void cleanMem(HttpURLConnection connection){
        connection.disconnect();
    }

}
