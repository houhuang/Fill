package com.jd.fill.manager;

import android.content.Context;

import com.jd.fill.bean.GameItemInfo;
import com.jd.fill.util.FileUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by houhuang on 18/3/12.
 */
public class DataManager {

    private List<GameItemInfo> mGameInfo = new ArrayList<GameItemInfo>();

    private static class ManagerHolder
    {
        private static final DataManager instence = new DataManager();
    }
    private DataManager(){};

    public static final DataManager getInstance()
    {
        return ManagerHolder.instence;
    }

    public void initData(Context context)
    {
        mGameInfo = FileUtil.readItemInfoFromJson(context);
    }

    public List<GameItemInfo> getmGameInfo()
    {
        return mGameInfo;
    }

}
