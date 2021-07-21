package com.luobo.toranoana_monitor.util;

import com.luobo.toranoana_monitor.param.Param;
import lombok.extern.slf4j.Slf4j;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class ImgCache implements Runnable {
    private final String imgUrlStr;
    private Path path;

    public ImgCache(String imgUrlStr, int id){
        this.imgUrlStr = imgUrlStr;
        String fileName = id + ".jpg";
        String filePathStr = "cache/img/";
        path = Paths.get(filePathStr);
        DirExist(path);
        path = Paths.get(filePathStr + fileName);
    }

    /**
     * TODO 返回照片位置
     */
    public Path getImg(int id){
        return null;
    }

    @Override
    public void run() {
        if(!isFileExist(path)) { //如果文件已经存在则不在下载
            try {
                URLConnection connection = new URL(imgUrlStr).openConnection();
                InputStream inputStream = connection.getInputStream();
                FileOutputStream fileOutputStream = new FileOutputStream(path.toFile());
                int img;
                while ((img = inputStream.read()) != -1) {
                    fileOutputStream.write(img);
                }
                inputStream.close();
                fileOutputStream.close();
                if(Param.getParam().isDebug())
                    log.info(" 保存图片文件至 " + path.toString());
            } catch (Exception e) {
                log.info(" 无法下载图片 " + e.getMessage() + "稍后将重试");
                try{
                    Thread.sleep(Param.getParam().getSuspendTime()/2);
                    log.info(" 等待结束 重新下载图片");
                }catch (Exception exception){
                    log.info(" 暂停操作出现故障！" + exception.getMessage());
                }
            }
        }
    }

    /**
     * 检查文件是否存在
     * @param path 需要检查的文件路径
     * @return 当存在返回true 不存在返回false
     */
    public boolean isFileExist(Path path){
        try {
            return Files.exists(path);
        }catch (Exception e){
            log.info(" 文件不存在 " + e.getMessage());
        }
        return false;
    }

    public void DirExist(Path path){
        try {
            Files.createDirectories(path);
        }catch (Exception e){
            log.info(" 创建文件夹失败" + e.getMessage());
        }
    }
}
