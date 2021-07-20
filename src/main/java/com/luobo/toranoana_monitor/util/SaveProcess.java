package com.luobo.toranoana_monitor.util;

import com.luobo.toranoana_monitor.dao.UrlDataDao;

import lombok.extern.slf4j.Slf4j;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SaveProcess {
    private static final SaveProcess saveProcess = new SaveProcess();
    /**
     * 读取CSV文档
     * @return 一个包含处理过的数据的ArrayList
     */
    public List<String[]> loadSaveFile(File file) {
        String tempStr;
        List<String[]> tempStrArrayList = new ArrayList<>();
        try (BufferedReader read = new BufferedReader(new FileReader(file))) {
            while ((tempStr = read.readLine()) != null) {  // 将文件中每一行的文字赋值给临时变量tempStr
                // 去除csv文件读入时的双引号，并将数据以逗号进行分割，封装成数组
                String[] tmpStrArray = tempStr.replace("\"", "").split(",", -1);
                tempStrArrayList.add(tmpStrArray);
            }
            tempStrArrayList.remove(0);
        } catch (IOException e) {
            log.info("没有检测到存档文件\t" + e.getMessage());
        }
        return tempStrArrayList;
    }


    /**
     * 读取CSV存档
     * @param save 存储存档的数组
     */
    public void loadSave(List<String[]> save){
        log.info(" 正在检查" + save.size() + "条无效链接！");
        for(String[] strings : save){
            UrlDataDao.getUrlDataDao().add(Integer.parseInt(strings[1]));
        }
    }

    /**
     * TODO 文档格式检验
     * @return true 存档格式争取
     */
    public boolean saveFormatCheck(List<String[]> save) {
        log.info(" 正在检查存档文件格式！");
        return true;
    }

    public static SaveProcess getSaveProcess(){
        return saveProcess;
    }
}
