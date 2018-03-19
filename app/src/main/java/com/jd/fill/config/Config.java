package com.jd.fill.config;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.widget.StackView;

import com.jd.fill.R;

/**
 * Created by houhuang on 18/3/12.
 */
public class Config extends Application {

    public static SharedPreferences mSp;

    public static int mCurrentLevel;

    public static boolean mSoundsIsOpen;

    public static int mChooseLevel = 0;

    public static int mHintNum;

    public static String SP_DATA_STORGE = "SP_DATA_STORGE";
    public static String KEY_CURRENT_LEVEL = "KEY_CURRENT_LEVEL";
    public static String KEY_SHOUNDS_ISOPEN = "KEY_SHOUNDS_ISOPEN";
    public static String KEY_HINT_NUM = "KEY_HINT_NUM";

    public static String KEY_RATEUS_COUNT = "KEY_RATEUS_COUNT";
    public static String KEY_ALREADY_RATEUS = "KEY_ALREADY_RATEUS";

    public static MediaPlayer mMediaplayer;
    public static SoundPool mSoundPool;

    public static final int[] mSoundResource = {R.raw.crash, R.raw.win2};

    public static boolean mIsRateus;
    public static int mRateUsCount;

    @Override
    public void onCreate() {
        super.onCreate();

        mSp = getSharedPreferences(SP_DATA_STORGE, 0);
        mCurrentLevel = mSp.getInt(KEY_CURRENT_LEVEL, 0);
        mSoundsIsOpen = mSp.getBoolean(KEY_SHOUNDS_ISOPEN, true);
        mHintNum = mSp.getInt(KEY_HINT_NUM, 20);
//        mHintNum = 1000;

        mIsRateus = mSp.getBoolean(KEY_ALREADY_RATEUS, false);
        mRateUsCount = mSp.getInt(KEY_RATEUS_COUNT, 0);
    }

    public static void initMusic(Context context)
    {
        mMediaplayer = MediaPlayer.create(context, R.raw.bg1);
        mMediaplayer.setLooping(true);

        mSoundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 100);
    }

    public static void playMusic()
    {
        mMediaplayer.start();
    }

    public static void pauseMusic()
    {
        if (mMediaplayer.isPlaying())
            mMediaplayer.pause();
    }

    public static void stopMusic()
    {
        mMediaplayer.stop();
    }

    public static void playSounds(int id)
    {
        if (!mSoundsIsOpen) return;

        mSoundPool.play(mSoundResource[id], 1, 1, 1, 0, 1);
    }

    public static void saveConfigInfo()
    {
        SharedPreferences.Editor editor = mSp.edit();
        editor.putInt(KEY_CURRENT_LEVEL, mCurrentLevel);
        editor.putBoolean(KEY_SHOUNDS_ISOPEN, mSoundsIsOpen);
        editor.putInt(KEY_HINT_NUM, mHintNum);

        editor.putBoolean(KEY_ALREADY_RATEUS, mIsRateus);
        editor.putInt(KEY_RATEUS_COUNT, mRateUsCount);
        editor.commit();
    }

}
