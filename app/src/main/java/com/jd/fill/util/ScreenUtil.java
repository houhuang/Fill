package com.jd.fill.util;

import android.content.Context;
import android.content.res.Configuration;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by houhuang on 18/3/12.
 */
public class ScreenUtil {
    public static float getScreenDensity(Context context)
    {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics.density;
    }

    public static boolean isTablet(Context context)
    {
//        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
//        int type = telephonyManager.getPhoneType();
//        if (type == TelephonyManager.PHONE_TYPE_NONE)
//            return true;
//
//        return false;

        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
