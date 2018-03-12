package com.jd.fill.util;

import android.content.Context;
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
}
