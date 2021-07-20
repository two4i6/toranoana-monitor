package com.luobo.toranoana_monitor.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

@Slf4j
public class ImgCache implements Runnable {
    private final String imgUrlStr;
    private final String fileName;

    public ImgCache(String imgUrlStr, int id){
        this.imgUrlStr = imgUrlStr;
        this.fileName = id + ".jpg";
    }
    @Override
    public void run() {
        if(isExist(fileName)) {
            try {
                URLConnection connection = new URL(imgUrlStr).openConnection();
                InputStream inputStream = connection.getInputStream();
                String filePath = "img/";
                FileOutputStream fileOutputStream = new FileOutputStream(filePath + fileName);
                int j;
                while ((j = inputStream.read()) != -1) {
                    fileOutputStream.write(j);
                }
                inputStream.close();
                fileOutputStream.close();
            } catch (Exception e) {
                log.info(" 无法下载图片 " + e.getMessage());
            }
        }
    }

    /**
     * TODO 检查文件是否存在
     * @param fileName 需要检查的文件名称
     * @return 当存在返回true 不存在返回false
     */
    public boolean isExist(String fileName){
        try {
            File file = new File(fileName);
        }catch (Exception e){
            log.info(" 文件不存在 " + e.getMessage());
        }
        return false;
    }
}
