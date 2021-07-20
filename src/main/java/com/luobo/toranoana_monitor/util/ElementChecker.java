package com.luobo.toranoana_monitor.util;

import lombok.extern.slf4j.Slf4j;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

@Slf4j
public class ElementChecker {
    private final String result;  // 返回结果字符串

    public ElementChecker(HttpURLConnection connection){
        this.result = getResult(connection);
    }

    public String getResult(HttpURLConnection connection){
        try {
            InputStream is = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            StringBuilder sbf = new StringBuilder();
            String temp;
            while ((temp = br.readLine()) != null) {
                sbf.append(temp);
            }
            ClearMem(br, is);
            return sbf.toString();
        }catch (Exception e){
            //log.info(e.getMessage());
        }
        return null;
    }



    public boolean isPassword(){
        return !contains("<div class=\"ccm-page page-type-commodity-detail page-template-commodity-detail\"> ");
    }

    public boolean isKey(String key){
        return contains(key);
    }

    public boolean contains(String str){
        return result.contains(str);
    }

    /**
     * 释放内存
     * @param br bufferReader
     * @param is inputStream
     */
    private void ClearMem(BufferedReader br, InputStream is){
        if (null != br) {
            try {
                br.close();
            } catch (IOException e) {
                log.info("检索内容错误2: " + e.getMessage());
            }
        }
        if (null != is) {
            try {
                is.close();
            } catch (IOException e) {
                log.info("检索内容错误2: " + e.getMessage());
            }
        }
    }
}
