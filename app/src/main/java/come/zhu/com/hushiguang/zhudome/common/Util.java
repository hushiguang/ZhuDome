package come.zhu.com.hushiguang.zhudome.common;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by office on 2015/12/7.
 */
public class Util {


    /**
     * 当前日期加减若干天 后的日期
     *
     * @param m 格式 如：yyyy年MM月dd日 ，yyyy-MM-dd
     * @return
     */
    public static String GetDateAdd(int m) {

        Date date = new Date();
        SimpleDateFormat h = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String n = h.format(date);
        Timestamp time = Timestamp.valueOf(n);
        // 在天数上加（减）天数
        long l = time.getTime() - 24 * 60 * 60 * m * 1000;
        time.setTime(l);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        // 获取当前时间并格式化之
        String newDate = sdf.format(time);

        Date lastDate = new Date(l);
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        c.setTime(lastDate);
        String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if ("1".equals(mWay)) {
            mWay = "日";
        } else if ("2".equals(mWay)) {
            mWay = "一";
        } else if ("3".equals(mWay)) {
            mWay = "二";
        } else if ("4".equals(mWay)) {
            mWay = "三";
        } else if ("5".equals(mWay)) {
            mWay = "四";
        } else if ("6".equals(mWay)) {
            mWay = "五";
        } else if ("7".equals(mWay)) {
            mWay = "六";
        }

        return newDate + "," + "星期" + mWay;
    }


    /**
     * 时间戳10位转时间
     *
     * @param time
     * @return
     */
    public static String timestampToString(Integer time) {
        //int转long时，先进行转型再进行计算，否则会是计算结束后在转型
        long temp = (long) time * 1000;
        Timestamp ts = new Timestamp(temp);
        String tsStr = "";
        DateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");
        try {
            //方法一
            tsStr = dateFormat.format(ts);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tsStr;
    }


    /***
     * 获取底部的导航栏的高度
     *
     * @param mActivity
     * @return
     */
    public static int getNavigationBarHeight(Activity mActivity) {
        Resources resources = mActivity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        Log.v("dbw", "Navi height:" + height);
        return height;
    }


    /***
     * 获取本机deviceId
     */
    public static String getPhoneDeviceId(Context mContext) {
        final TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, tmSerial, tmPhone, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(mContext.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        return deviceUuid.toString();
    }


    public static SweetAlertDialog showDialog(Context mContext) {
        final SweetAlertDialog pDialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        return pDialog;
    }

}
