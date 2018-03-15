package com.jd.fill.activity;

import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jd.fill.R;
import com.jd.fill.config.Config;
import com.jd.fill.fragment.WinFragment;
import com.jd.fill.manager.DataManager;
import com.jd.fill.util.GeneralUtil;
import com.jd.fill.util.StatusBarUtil;
import com.jd.fill.view.GameView;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mHint;
    private ImageView mHome;
    private ImageView mVedio;
    private GameView mGameView;

    private Button mBackButton;

    private FragmentTransaction mTransaction;
    private WinFragment mWinFragment;
    private FrameLayout mFragmentParent;

    private TextView mLevelText;
    private TextView mHintText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        StatusBarUtil.setWindowStatusBarColor(this, R.color.status_bar_map_color);
        StatusBarUtil.StatusBarLightMode(this);

        bindView();
    }

    private void bindView()
    {
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

            @Override
            public void OnHintSucc() {
                mHintText.setText("" + Config.mHintNum);
            }
        });

        mBackButton = (Button)findViewById(R.id.game_back);
        mBackButton.setOnClickListener(this);

        mHint = (ImageView) findViewById(R.id.game_button_hint);
        mHome = (ImageView)findViewById(R.id.game_button_home);
        mVedio = (ImageView)findViewById(R.id.game_button_vedio);
        mHint.setOnClickListener(this);
        mHome.setOnClickListener(this);
        mVedio.setOnClickListener(this);

        mHintText = (TextView)findViewById(R.id.game_hint_num);
        mLevelText = (TextView)findViewById(R.id.game_level_text);

        StringBuilder builder = new StringBuilder();
        builder.append("Level - ").append(Config.mChooseLevel + 1);
        mLevelText.setText(builder.toString());

        mHintText.setText("" + Config.mHintNum);

        FragmentManager fragmentManager = getSupportFragmentManager();
        mTransaction = fragmentManager.beginTransaction();

        mWinFragment = new WinFragment();
        mTransaction.add(R.id.win_fragment_parent, mWinFragment);
        mTransaction.commit();

        mFragmentParent = (FrameLayout)findViewById(R.id.win_fragment_parent);
        mFragmentParent.setForegroundGravity(10);
        hideWinFragment();

        mWinFragment.setOnWinFragmentListent(new WinFragment.OnWinFragmentListener() {
            @Override
            public void OnPlay() {
                if (Config.mCurrentLevel <= DataManager.getInstance().getmGameInfo().size())
                    nextLevel();
            }

            @Override
            public void OnHome() {
                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

            @Override
            public void OnShare() {
                GeneralUtil.shareText(getApplicationContext());
            }
        });

    }

    private void nextLevel()
    {
        mHintText.setText("" + Config.mHintNum);
        Config.mChooseLevel = Config.mCurrentLevel;
        mGameView.nextGame();
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
            case R.id.game_back:
                finish();
                break;
            case R.id.game_button_hint:
                mGameView.hint();
                break;

            case R.id.game_button_home:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.game_button_vedio:
                break;
            default:
                break;
        }
    }


}
