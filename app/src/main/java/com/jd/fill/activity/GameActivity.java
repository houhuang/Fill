package com.jd.fill.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.jd.fill.R;
import com.jd.fill.manager.DataManager;
import com.jd.fill.view.GameView;

public class GameActivity extends AppCompatActivity {

    private GameView mGameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DataManager.getInstance().initData(this);
        setContentView(R.layout.activity_game);

    }
}
