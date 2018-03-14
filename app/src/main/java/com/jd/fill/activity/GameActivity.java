package com.jd.fill.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.jd.fill.R;
import com.jd.fill.config.Config;
import com.jd.fill.fragment.WinFragment;
import com.jd.fill.manager.DataManager;
import com.jd.fill.view.GameView;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnHint;
    private GameView mGameView;

    private FragmentTransaction mTransaction;
    private WinFragment mWinFragment;
    private FrameLayout mFragmentParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mBtnHint = (Button)findViewById(R.id.btn_hint);
        mGameView = (GameView)findViewById(R.id.gameView);
        mGameView.setGameViewListener(new GameView.OnGameCompletedListener() {
            @Override
            public void OnCompleted() {

                showWinFragment();
                if (Config.mChooseLevel == Config.mCurrentLevel)
                {
                    Config.mCurrentLevel ++;
                    Config.saveConfigInfo();
                }

            }
        });

        mBtnHint.setOnClickListener(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        mTransaction = fragmentManager.beginTransaction();

        mWinFragment = new WinFragment();
        mTransaction.add(R.id.win_fragment_parent, mWinFragment);
        mTransaction.commit();

        mFragmentParent = (FrameLayout)findViewById(R.id.win_fragment_parent);
        mFragmentParent.setForegroundGravity(10);
        hideWinFragment();
    }

    private void hideWinFragment()
    {
       mFragmentParent.setVisibility(View.GONE);
    }

    private void showWinFragment()
    {
        mWinFragment.updateContent();
        mFragmentParent.setVisibility(View.VISIBLE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_hint:
                mGameView.hint();
                hideWinFragment();
            break;
            default:
                break;
        }
    }
}
