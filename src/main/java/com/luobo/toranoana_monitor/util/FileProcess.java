package com.luobo.toranoana_monitor.util;

import com.luobo.toranoana_monitor.dao.UrlData;
import com.luobo.toranoana_monitor.dao.UrlDataDao;
import com.luobo.toranoana_monitor.param.Param;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

@Slf4j
public class FileProcess {
    private static final FileProcess fileProcess = new FileProcess();
    private static final Param param = Param.getParam();
    private final StringBuilder sb = new StringBuilder();

    public void exportAllUrl(){
        exportValidUrl();
        exportFilteredUrl();
        exportInvalidUrl();
    }

    public void exportInvalidUrl() {
        exportCsvFile(new File(param.getInvalidFileName()),UrlDataDao.getUrlDataDao().getAllInvalid());
    }

    public void exportValidUrl() {
        exportCsvFile(new File(param.getValidFileName()),UrlDataDao.getUrlDataDao().getAllValid());
    }

    public void exportFilteredUrl() {
        exportCsvFile(new File(param.getFilteredFileName()),UrlDataDao.getUrlDataDao().getAllFiltered());
    }

    /**
     * 用于在制定文档后append新的行
     * @param file 指定csv文档名
     */
    public void appendUrl(File file, UrlData urlData) {
        try (BufferedWriter out = new BufferedWriter
                (new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            Write2File(out, MakeData(urlData));
        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }

    /**
     * 输出CSV文档
     * @param file 指定csv文档名
     */
    private void exportCsvFile(File file, Collection<UrlData> list){
        try (BufferedWriter out = new BufferedWriter
                (new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            Write2File(out, MakeHeader());
            for (UrlData urls : list) {
                Write2File(out, MakeData(urls));
            }
            if(param.isDebug())
                log.info(" 已输出 " + file.getName());
        }catch (IOException e) {
            log.info(" 输出存档出错: " + file.getName() + e.getMessage());
        }
    }

    /**
     * 工具类 用于制作表头
     * @return 返回一串表头字串
     */
    private String MakeHeader() {
        sb.delete(0, sb.length());
        sb.append("\"").append("date").append("\"").append(",")
                .append("\"").append("id").append("\"").append(",")
                .append("\"").append("link").append("\"").append(",")
                .append("\"").append("image_link").append("\"").append(",").append("\n");
        return sb.toString();
    }

    /**
     * 工具类 用于制作每行的数据
     * @return 返回一串表头字符串
     */
    private String MakeData(UrlData urlData) {
        sb.delete(0, sb.length());
        sb.append("\"").append(urlData.getDate()).append("\"").append(",")
                .append("\"").append(urlData.getId()).append("\"").append(",")
                .append("\"").append(urlData.getUrlStr()).append("\"").append(",")
                .append("\"").append(urlData.getImgUrlStr()).append("\"").append(",").append("\n");
        return sb.toString();
    }

    /**
     * 输出整个文档
     * @param out BufferWriter类
     * @param str 需要写入的字符串
     */
    private void Write2File(BufferedWriter out, String str) {
        try {
            out.append(str);
        }catch (IOException e){
            log.info(e.getMessage());
        }
    }

    public static FileProcess getFileProcess(){
        return fileProcess;
    }
}
