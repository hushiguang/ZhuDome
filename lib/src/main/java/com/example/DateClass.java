package com.example;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by office on 2015/12/8.
 */
public class DateClass {

    public static void main(String[] args) {
//        SimpleDateFormat format =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
//        //1449577825107  1449573226
//        Long time=new Long(1449573226);


        System.out.println(timestampToString(1449573226));
    }

    /**
     * 时间戳10位转时间
     *
     * @param time
     * @return
     */
    public static String timestampToString(Integer time){
        //int转long时，先进行转型再进行计算，否则会是计算结束后在转型
        long temp = (long)time*1000;
        Timestamp ts = new Timestamp(temp);
        String tsStr = "";
        DateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");
        try {
            //方法一
            tsStr = dateFormat.format(ts);
            System.out.println(tsStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tsStr;
    }
}
