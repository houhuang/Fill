package com.jd.fill.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jd.fill.R;
import com.jd.fill.config.Config;

/**
 * Created by houhuang on 18/3/14.
 */
public class WinFragment extends Fragment implements View.OnClickListener {

    private Button mHomeButton;
    private Button mShareButton;
    private Button mPlayButton;

    private TextView mLevelText;
    private ImageView mHintView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_win, container, false);
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
        mPlayButton.setOnClickListener(this);

        updateContent();
    }

    public void updateContent()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Level - ").append(Config.mChooseLevel + 1);
        mLevelText.setText(builder.toString());

        if ((Config.mChooseLevel == Config.mCurrentLevel) &&
                ((Config.mCurrentLevel - 1) % 5 == 0))
        {
            mHintView.setVisibility(View.VISIBLE);
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
                break;
            case R.id.btn_fragment_share:
                break;
            case R.id.btn_fragment_play:
                break;
            default:
                break;
        }
    }
}
