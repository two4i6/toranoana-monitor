package com.luobo.toranoana_monitor.util;

import com.luobo.toranoana_monitor.param.Param;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Util {
    private static final Util util = new Util();

    public String getFullId(int idB) {
        return Param.getParam().getIdA() + idB;
    }

    public String getUrl(int id){
        return Param.getParam().getUrlStr() + getFullId(id);
    }

    public String getImgUrl(int id){
        String idStr = getFullId(id) + "";
        return Param.getParam().getImgUrlStr() + idStr.substring(0,2)+"/"+idStr.substring(2,6)+"/"
                +idStr.substring(6,8) + "/"+idStr.substring(8,10)+"/" + idStr + "-1p.jpg";
    }

    public String getTime() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(d);
    }

    public static Util getUtil(){
        return util;
    }
}
