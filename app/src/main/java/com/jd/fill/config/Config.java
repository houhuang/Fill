package com.jd.fill.config;

import android.app.Application;
import android.content.SharedPreferences;
import android.widget.StackView;

/**
 * Created by houhuang on 18/3/12.
 */
public class Config extends Application {

    public static SharedPreferences mSp;

    public static int mCurrentLevel;

    public static String SP_DATA_STORGE = "SP_DATA_STORGE";
    public static String KEY_CURRENT_LEVEL = "KEY_CURRENT_LEVEL";

    @Override
    public void onCreate() {
        super.onCreate();

        mSp = getSharedPreferences(SP_DATA_STORGE, 0);
        mCurrentLevel = mSp.getInt(KEY_CURRENT_LEVEL, 0);
    }
}
