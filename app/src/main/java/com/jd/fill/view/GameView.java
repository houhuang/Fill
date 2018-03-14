package com.jd.fill.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import com.jd.fill.R;
import com.jd.fill.bean.GameItem;
import com.jd.fill.bean.GameItemInfo;
import com.jd.fill.config.Config;
import com.jd.fill.manager.DataManager;
import com.jd.fill.util.FileUtil;
import com.jd.fill.util.ScreenUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2018/3/11 0011.
 */

public class GameView extends GridLayout implements View.OnTouchListener {
    private GameItem[][] mGameMatrix;


    private int mGameVLines;
    private int mGameHLines;

    private int mStartX, mStartY;

    private List<GameItem> mAlreadyClickItem = new ArrayList<GameItem>();
    private GameItem mFirstItem;
    private GameItem mCurrentItem;
    private boolean isClickStartTarget = false;

    private int mNeedClickItem = 0;

    private GameItem clickItem;

    private GameItemInfo mItemInfo;

    private Context mContext;

    private Bitmap mRadishWhite;
    private Bitmap mRadishRed;

    private int mHintIndex = 1;

    private int[] mPathColor = {
            R.color.color_paht1,
            R.color.color_paht2,
            R.color.color_paht3,
            R.color.color_paht4,
            R.color.color_paht5,
            R.color.color_paht6,
            R.color.color_paht7,
            R.color.color_paht8,
            R.color.color_paht9,
            R.color.color_paht10};

    public GameView(Context context)
    {
        super(context);
        mContext = context;
        initGameMatrix();
    }

    public GameView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mContext = context;
        initGameMatrix();
    }

    public void startGame()
    {
//        initGameMatrix();;
//        initGameView(Config.mItemSize);
    }

    public void initGameMatrix()
    {
        mRadishWhite = FileUtil.getBitmapFromDrawable(mContext, R.drawable.radish2);
        mRadishRed = FileUtil.getBitmapFromDrawable(mContext, R.drawable.radish);


        mItemInfo = DataManager.getInstance().getmGameInfo().get(Config.mCurrentLevel);

        //初始化矩阵
        removeAllViews();


        mGameHLines = mItemInfo.getRow();
        mGameVLines = mItemInfo.getCol();
        mGameMatrix = new GameItem[mGameHLines][mGameVLines];

        setColumnCount(mGameVLines);
        setRowCount(mGameHLines);
        setOnTouchListener(this);

        //初始化View参数
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        display.getMetrics(metrics);

        int size = (int)(metrics.widthPixels - 20 * ScreenUtil.getScreenDensity(mContext)) / 6;


        initGameView(size);
    }

    private void initGameView(int cardSize)
    {
        boolean isTrue = false;

        Random random = new Random();
        int cIndex = random.nextInt(10);


        removeAllViews();
        GameItem card;
        for (int i = 0; i < mGameHLines; ++i)
        {
            for (int j = 0; j < mGameVLines; ++j)
            {

                if (mGameVLines % 2 == 0)
                {
                    if (j % 2 == 0 && i % 2 != 0 || j % 2 != 0 && i % 2 == 0)
                    {
                        isTrue = true;
                    }else
                    {
                        isTrue = false;
                    }
                }else
                {
                    if ((i * mGameVLines + j) % 2 == 0)
                    {
                        isTrue = true;
                    }else
                    {
                        isTrue = false;
                    }
                }

                card = new GameItem(getContext(), isTrue, mPathColor[cIndex], mRadishRed, mRadishWhite);
                addView(card, cardSize, cardSize);


                // 初始化GameMatrix全部为0 空格List为所有
                mGameMatrix[i][j] = card;
                mGameMatrix[i][j].setRow(i);
                mGameMatrix[i][j].setCol(j);

                int index = i * mGameVLines + j;
                if (mItemInfo.getState()[index] == 0)
                {
                    //stone
                    mGameMatrix[i][j].setItemTag(0, mRadishRed);
                }else if (mItemInfo.getState()[index] == 1)
                {
                    //radish
                    mGameMatrix[i][j].setItemTag(1, mRadishRed);
                    mNeedClickItem ++;
                }else if (mItemInfo.getState()[index] == 2)
                {
                    // start radish
                    mGameMatrix[i][j].setItemTag(2, mRadishRed);
                    mNeedClickItem ++;

                    mFirstItem = mGameMatrix[i][j];
                    mCurrentItem = mFirstItem;
                }


            }
        }

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
            {
                mStartX = (int)event.getX();
                mStartY = (int)event.getY();

                boolean is = isClickStartTarget(mStartX, mStartY, mCurrentItem);
                if (is)
                {
                    isClickStartTarget = true;
                    if (mAlreadyClickItem.size() == 0)
                    {
                        mAlreadyClickItem.add(mCurrentItem);
                    }
                }else
                {

                }
                clickItem = isClickAlreadyExitTarget(mStartX, mStartY);
            }
            break;

            case MotionEvent.ACTION_MOVE:
            {
                GameItem item = getClickItem((int)event.getX(), (int) event.getY());
                if (item != null && item != mCurrentItem && isClickStartTarget && item.getItemTag() != 0)
                {
                    if (item == mFirstItem)
                    {
                        for (int i = 0; i < mAlreadyClickItem.size(); ++i)
                        {
                            mAlreadyClickItem.get(i).clearPathFromDir();
                            mAlreadyClickItem.get(i).setRadishColor(true);
                        }
                        mAlreadyClickItem.clear();
                        mCurrentItem = mFirstItem;
                        mCurrentItem.clearPathFromDir();
                    }else
                    {
                        int index = -1;
                        for (int i = 0; i < mAlreadyClickItem.size(); ++i)
                        {
                            if (mAlreadyClickItem.get(i) == item)
                            {
                                index = i;
                            }
                        }

                        if (index != -1)
                        {
                            int count = mAlreadyClickItem.size() - index - 1;
                            for (int i = 0; i < count; ++i)
                            {
                                mAlreadyClickItem.get(mAlreadyClickItem.size() - 1).clearPathFromDir();

                                mAlreadyClickItem.get(mAlreadyClickItem.size() - 1).setRadishColor(true);
                                mAlreadyClickItem.remove(mAlreadyClickItem.size() - 1);
                            }
                            mCurrentItem = mAlreadyClickItem.get(mAlreadyClickItem.size() - 1);
                            mCurrentItem.clearPathNotFirst();

                        }else
                        {
                            int moveDir = isMoveDirFromNext(mCurrentItem, item);
                            int moveDirFormPre = isMoveDirFromPre(mCurrentItem, item);
                            if (moveDir > 0)
                            {
                                if (moveDirFormPre == 1)
                                {
                                    mCurrentItem.setDrawRight(true);
                                }else if (moveDirFormPre == 2)
                                {
                                    mCurrentItem.setDrawLeft(true);
                                }else if (moveDirFormPre == 3)
                                {
                                    mCurrentItem.setDrawTop(true);
                                }else if (moveDirFormPre == 4)
                                {
                                    mCurrentItem.setDrawBottom(true);
                                }
                                mCurrentItem.showPathFromDir();

                                mCurrentItem = item;
                                mCurrentItem.setRadishColor(false);
                                mAlreadyClickItem.add(mCurrentItem);

                                if (moveDir == 1)
                                {
                                    mCurrentItem.setDrawRight(true);
                                }else if (moveDir == 2)
                                {
                                    mCurrentItem.setDrawLeft(true);
                                }else if (moveDir == 3)
                                {
                                    mCurrentItem.setDrawTop(true);
                                }else if (moveDir == 4)
                                {
                                    mCurrentItem.setDrawBottom(true);
                                }
                                mCurrentItem.showPathFromDir();

                                if (isCompleted())
                                {
                                    completed();

                                }
                            }

                        }
                    }
                }
            }
            break;

            case MotionEvent.ACTION_UP:
            {
//                if (!isClickStartTarget)
                {
                    if (clickItem != null && Math.abs(event.getX() - mStartX) < 10 &&
                            Math.abs(event.getY() - mStartY ) < 10)
                    {
                        int index = -1;
                        for (int i = 0; i < mAlreadyClickItem.size(); ++i)
                        {
                            if (mAlreadyClickItem.get(i) == clickItem)
                            {
                                index = i;
                            }
                        }

                        if (index != -1)
                        {
                            int count = mAlreadyClickItem.size() - index;

                            for (int i = 0; i < count; ++i)
                            {
                                mAlreadyClickItem.get(mAlreadyClickItem.size() - 1).clearPathFromDir();

                                mAlreadyClickItem.get(mAlreadyClickItem.size() - 1).setRadishColor(true);
                                mAlreadyClickItem.remove(mAlreadyClickItem.size() - 1);
                            }

                            if (mAlreadyClickItem.size() == 0)
                            {
                                mCurrentItem = mFirstItem;
                                mCurrentItem.clearPathFromDir();
                            }else
                            {
                                mCurrentItem = mAlreadyClickItem.get(mAlreadyClickItem.size() - 1);
                            }

                            mCurrentItem.clearPathNotFirst();

                        }
                    }
                }


                isClickStartTarget = false;


            }
            break;
        }

        return true;
    }

    public void completed()
    {
        Config.mCurrentLevel ++;
        Config.saveConfigInfo();
        Toast.makeText(getContext(), "Completed!!!", Toast.LENGTH_LONG).show();
    }

    public void hint()
    {
        if (mHintIndex > 3 )
        {
            Toast.makeText(mContext, "Three hints a maximum of each level.", Toast.LENGTH_SHORT).show();
            return;
        }

        int count = mHintIndex * 5;
        if (count > mNeedClickItem)
            count = mNeedClickItem;

        for (int i = 0; i < mAlreadyClickItem.size(); ++i)
        {
            mAlreadyClickItem.get(i).clearPathFromDir();
            mAlreadyClickItem.get(i).setRadishColor(true);
        }
        mAlreadyClickItem.clear();
        mCurrentItem = mFirstItem;
        mCurrentItem.clearPathFromDir();

        mAlreadyClickItem.add(mCurrentItem);
        for (int i = 0; i < count; ++i)
        {
            int row =  mCurrentItem.getRow();
            int col = mCurrentItem.getCol();

            char dir = mItemInfo.getHint()[i];
            if (dir == 'w')
            {
                mCurrentItem.setDrawTop(true);
                mCurrentItem.showPathFromDir();
                mCurrentItem.setRadishColor(false);

                GameItem item = mGameMatrix[row - 1][col];
                item.setDrawBottom(true);
                mAlreadyClickItem.add(item);

                mCurrentItem = item;
            }else if (dir == 's')
            {
                mCurrentItem.setDrawBottom(true);
                mCurrentItem.showPathFromDir();
                mCurrentItem.setRadishColor(false);

                GameItem item = mGameMatrix[row + 1][col];
                item.setDrawTop(true);
                mAlreadyClickItem.add(item);

                mCurrentItem = item;

            }else if (dir == 'a')
            {
                mCurrentItem.setDrawLeft(true);
                mCurrentItem.showPathFromDir();
                mCurrentItem.setRadishColor(false);

                GameItem item = mGameMatrix[row][col - 1];
                item.setDrawRight(true);
                mAlreadyClickItem.add(item);

                mCurrentItem = item;

            }else if (dir == 'd')
            {
                mCurrentItem.setDrawRight(true);
                mCurrentItem.showPathFromDir();
                mCurrentItem.setRadishColor(false);

                GameItem item = mGameMatrix[row][col + 1];
                item.setDrawLeft(true);
                mAlreadyClickItem.add(item);

                mCurrentItem = item;
            }
        }

        mCurrentItem.showPathFromDir();
        mCurrentItem.setRadishColor(false);

        mHintIndex ++;

    }

    private boolean isCompleted()
    {
        if (mAlreadyClickItem.size() == mNeedClickItem )
            return true;
        return false;
    }

    private int isMoveDirFromNext(GameItem preItem, GameItem nexItem)
    {
        //0不能移动     1-Right      2-Left     3-Top   4-Bottom
        if ((preItem.getRow() == nexItem.getRow() && Math.abs(preItem.getCol() - nexItem.getCol()) == 1))
        {
            if (preItem.getCol() - nexItem.getCol() > 0)
            {
                return 1;
            }else
            {
                return 2;
            }
        }

        if ((preItem.getCol() == nexItem.getCol() && Math.abs(preItem.getRow() - nexItem.getRow()) == 1))
        {
            if (preItem.getRow() - nexItem.getRow() > 0)
            {
                return 4;
            }else
            {
                return 3;
            }
        }

        return 0;
    }

    private int isMoveDirFromPre(GameItem preItem, GameItem nexItem)
    {
        //0不能移动     1-Right      2-Left     3-Top   4-Bottom
        if ((preItem.getRow() == nexItem.getRow() && Math.abs(preItem.getCol() - nexItem.getCol()) == 1))
        {
            if (preItem.getCol() - nexItem.getCol() > 0)
            {
                return 2;
            }else
            {
                return 1;
            }
        }

        if ((preItem.getCol() == nexItem.getCol() && Math.abs(preItem.getRow() - nexItem.getRow()) == 1))
        {
            if (preItem.getRow() - nexItem.getRow() > 0)
            {
                return 3;
            }else
            {
                return 4;
            }
        }

        return 0;
    }

    private boolean isClickStartTarget(int posX, int posY, GameItem target)
    {
        if (posX >= target.getLeft() && posX <= target.getRight()
                && posY >= target.getTop() && posY <= target.getBottom())
        {
            return true;
        }
        return false;
    }

    private GameItem isClickAlreadyExitTarget(int posX, int posY)
    {


        for (GameItem item : mAlreadyClickItem)
        {
            if (posX >= item.getLeft() && posX <= item.getRight()
                    && posY >= item.getTop() && posY <= item.getBottom())
            {
                return item;
            }
        }

        return null;
    }

    private GameItem getClickItem(int pox, int poy)
    {
        for (int i = 0; i < mGameHLines; ++i)
        {
            for (int j = 0; j < mGameVLines; ++j)
            {
                if (mGameMatrix[i][j].getItemTag() != 0)
                {
                    if (isClickStartTarget(pox, poy, mGameMatrix[i][j]))
                    {
                        return mGameMatrix[i][j];
                    }
                }
            }
        }
        return null;
    }

}
