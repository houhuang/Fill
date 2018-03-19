package com.jd.fill.fragment;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jd.fill.R;
import com.jd.fill.config.Config;
import com.jd.fill.manager.DataManager;
import com.jd.fill.util.FileUtil;

import java.io.File;

/**
 * Created by Administrator on 2018/3/17 0017.
 */

public class AchievementFragment extends Fragment implements View.OnClickListener {

    private TextView mLevelText;
    private TextView mIqText;
    private Button mHomeButton;
    private Button mShareButton;

    private RelativeLayout mScreenHotLayout;

    private OnAchieveClickListener listener;

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE };

    public void setOnAchieveListener(OnAchieveClickListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_achievement, container, false);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        bindView(view);

        return view;
    }


    private void bindView(View view) {
        mHomeButton = (Button) view.findViewById(R.id.btn_home);
        mShareButton = (Button) view.findViewById(R.id.btn_share);
        mHomeButton.setOnClickListener(this);
        mShareButton.setOnClickListener(this);

        mLevelText = (TextView) view.findViewById(R.id.achi_star_text);
        StringBuilder builder = new StringBuilder();
        builder.append(Config.mCurrentLevel).append("/").append(DataManager.getInstance().getmGameInfo().size());
        mLevelText.setText(builder.toString());

        mIqText = (TextView) view.findViewById(R.id.iq_text);
        caculateIQNum();

        mScreenHotLayout = (RelativeLayout) view.findViewById(R.id.screenhot_layout);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_home:
                if (listener != null)
                    listener.OnHomeButton();
                break;
            case R.id.btn_share:
                shareImage();
                break;
            default:
                break;

        }


    }

    private void shareImage()
    {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScreenHotLayout.setDrawingCacheEnabled(true);
                mScreenHotLayout.buildDrawingCache();
                Bitmap bitmap = mScreenHotLayout.getDrawingCache();

                int writePermiss = ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (writePermiss != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(getActivity(), PERMISSIONS_STORAGE, 1);
                }else
                {
                    boolean isSucc = FileUtil.savePictureToSDCard(bitmap, "share.png");
                    if (isSucc)
                    {
                        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
                        {
                            Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.setType("image/png");//图片

                            File image = new File(Environment.getExternalStorageDirectory().getPath()+"/Fill/share.png");
                            Uri uri = Uri.fromFile(image);//图片路径

                            intent.putExtra(Intent.EXTRA_STREAM, uri);
                            getContext().startActivity(Intent.createChooser(intent,"分享图片"));
                        }


                    }
                }



                mScreenHotLayout.destroyDrawingCache();
            }
        }, 1);


    }

    private void caculateIQNum()
    {
        double iQNum = 0;
        for (int i = 0; i < Config.mCurrentLevel; ++i)
        {
            int stage = DataManager.getInstance().getmGameInfo().get(i).getCol() +
                    DataManager.getInstance().getmGameInfo().get(i).getRow();

            if (stage <= 10)
            {
                iQNum += 1.5;
            }else if (stage == 11)
            {
                iQNum += 0.5;
            }else if (stage == 12)
            {
                iQNum += 0.2;
            }else if (stage == 13)
            {
                iQNum += 0.1;
            }else if (stage == 14)
            {
                iQNum += 0.1;
            }
        }

        int iQResult = (int) iQNum;

        mIqText.setText("" + iQResult);
        if (iQResult <= 50)
        {
            mIqText.setTextColor(ContextCompat.getColor(getContext(), R.color.color_iq_text1));
        }else if ( iQResult <= 100)
        {
            mIqText.setTextColor(ContextCompat.getColor(getContext(), R.color.color_iq_text2));
        }else if (iQResult <= 150)
        {
            mIqText.setTextColor(ContextCompat.getColor(getContext(), R.color.color_iq_text3));
        }else if (iQResult <= 200)
        {
            mIqText.setTextColor(ContextCompat.getColor(getContext(), R.color.color_iq_text4));
        }else
        {
            mIqText.setTextColor(ContextCompat.getColor(getContext(), R.color.color_iq_text5));
        }

    }

    public interface OnAchieveClickListener
    {
        void OnHomeButton();
    }

}
