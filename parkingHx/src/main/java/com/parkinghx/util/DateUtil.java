package com.parkinghx.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    /**
     * 获取当前时间的字符串
     * @return
     */
    public static String getNowTime(){
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return time.plusHours(0).format(df);
    }

    public static String getYYYYMMDDHHMMSSNowTime(){
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return time.plusHours(0).format(df);
    }

    /**
     * 根据时间字符串快进一小时
     * @param time
     * @return
     */
    public static String getFastOneTime(String time){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ldTime = LocalDateTime.parse(time,df);
        return ldTime.plusHours(1).format(df);
    }

    /**
     * 获取2个字符串时间差（小时）
     * @param startTime
     * @param endTime
     * @return
     */
    public static String getTimeDifference(String startTime,String endTime){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Duration duration = Duration.between(LocalDateTime.parse(startTime,df),LocalDateTime.parse(endTime,df));
        return duration.toHours()+"小时";
    }
}
