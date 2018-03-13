package com.jd.fill.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.jd.fill.R;
import com.jd.fill.config.Config;
import com.jd.fill.manager.DataManager;
import com.jd.fill.util.FileUtil;
import com.jd.fill.util.StatusBarUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnPlay;
    private Button mBtnSounds;
    private Button mBtnShare;

    private ImageView mSoundImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataManager.getInstance().initData(this);

        StatusBarUtil.setWindowStatusBarColor(this, R.color.transpatent);
        StatusBarUtil.StatusBarLightMode(this);

        bindView();
    }

    private void bindView()
    {
        mBtnPlay = (Button)findViewById(R.id.btn_play);
        mBtnShare = (Button)findViewById(R.id.btn_share);
        mBtnSounds = (Button)findViewById(R.id.btn_sound);
        mBtnPlay.setOnClickListener(this);
        mBtnShare.setOnClickListener(this);
        mBtnSounds.setOnClickListener(this);

        mSoundImage = (ImageView)findViewById(R.id.image_sounds);
        updateSoundState();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_play:
                Intent intent = new Intent(this, MapActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_share:
                break;
            case R.id.btn_sound:
                Config.mSoundsIsOpen = !Config.mSoundsIsOpen;

                updateSoundState();
                break;
            default:
                break;
        }
    }

    public void updateSoundState()
    {
        if (Config.mSoundsIsOpen)
        {
            mSoundImage.setImageBitmap(FileUtil.getBitmapFromDrawable(this, R.drawable.sounds));
        }else
        {
            mSoundImage.setImageBitmap(FileUtil.getBitmapFromDrawable(this, R.drawable.sounds_c));
        }

        Config.saveConfigInfo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
