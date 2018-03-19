package com.jd.fill.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jd.fill.R;
import com.jd.fill.config.Config;
import com.jd.fill.manager.DataManager;

/**
 * Created by houhuang on 18/3/14.
 */
public class WinFragment extends Fragment implements View.OnClickListener {

    private Button mHomeButton;
    private Button mShareButton;
    private Button mPlayButton;

    private TextView mLevelText;
    private ImageView mHintView;

    public OnWinFragmentListener listener;

    public interface OnWinFragmentListener
    {
        void OnPlay();
        void OnHome();
        void OnShare();

    }

    public void setOnWinFragmentListent(OnWinFragmentListener listent)
    {
        this.listener = listent;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_win, container, false);

        //屏蔽事件
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        bindView(view);
        return view;
    }

    private void bindView(View view)
    {
        mLevelText = (TextView)view.findViewById(R.id.fragment_level_text);
        mHintView = (ImageView)view.findViewById(R.id.fragment_hint);

        mHomeButton = (Button)view.findViewById(R.id.btn_fragment_home);
        mPlayButton = (Button)view.findViewById(R.id.btn_fragment_play);
        mShareButton = (Button)view.findViewById(R.id.btn_fragment_share);
        mHomeButton.setOnClickListener(this);
        mPlayButton.setOnClickListener(this);
        mShareButton.setOnClickListener(this);

        updateContent();
    }

    public void updateContent()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Level - ").append(Config.mChooseLevel + 1);

        if ((Config.mChooseLevel == Config.mCurrentLevel) && Config.mCurrentLevel >= DataManager.getInstance().getmGameInfo().size() - 1)
        {
            builder.append("- Max");
        }

        mLevelText.setText(builder.toString());

        if ((Config.mChooseLevel == Config.mCurrentLevel) &&
                ((Config.mCurrentLevel + 1) % 5 == 0))
        {
            mHintView.setVisibility(View.VISIBLE);
            Config.mHintNum ++;
            Config.saveConfigInfo();

        }else
        {
            mHintView.setVisibility(View.GONE);
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_fragment_home:
                if (listener != null)
                    listener.OnHome();
                break;
            case R.id.btn_fragment_share:
                if (listener != null)
                    listener.OnShare();
                break;
            case R.id.btn_fragment_play:
                if (listener != null)
                    listener.OnPlay();
                break;
            default:
                break;
        }
    }

}
