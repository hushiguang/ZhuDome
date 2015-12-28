package com.example;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by office on 2015/12/7.
 */
public class MainClasss {
    private static String mYear;
    private static String mMonth;
    private static String mDay;
    private static String mWay;

    public static void main(String[] args) {

//        System.out.printf(GetDateAdd(1).split(",")[0]);
    }


//
//    /**
//     * 当前日期加减若干天 后的日期
//     *
//     * @param m
//     *            格式 如：yyyy年MM月dd日 ，yyyy-MM-dd
//     * @return
//     */
//    public static String GetDateAdd(int m) {
//
//        Date date = new Date();
//        SimpleDateFormat h = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String n = h.format(date);
//        Timestamp time = Timestamp.valueOf(n);
//        // 在天数上加（减）天数
//        long l = time.getTime() - 24 * 60 * 60 * m * 1000;
//        time.setTime(l);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//        // 获取当前时间并格式化之
//        String newDate = sdf.format(time);
//
//        Date lastDate = new Date(l);
//        final Calendar c = Calendar.getInstance();
//        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
//        c.setTime(lastDate);
//        mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
//        if("1".equals(mWay)){
//            mWay ="日";
//        }else if("2".equals(mWay)){
//            mWay ="一";
//        }else if("3".equals(mWay)){
//            mWay ="二";
//        }else if("4".equals(mWay)){
//            mWay ="三";
//        }else if("5".equals(mWay)){
//            mWay ="四";
//        }else if("6".equals(mWay)){
//            mWay ="五";
//        }else if("7".equals(mWay)){
//            mWay ="六";
//        }
//
//        return newDate + "," + "星期" + mWay;
//    }

}
