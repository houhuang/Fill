package com.jd.fill.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.jd.fill.R;
import com.jd.fill.manager.DataManager;
import com.jd.fill.view.GameView;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnHint;
    private GameView mGameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mBtnHint = (Button)findViewById(R.id.btn_hint);
        mGameView = (GameView)findViewById(R.id.gameView);

        mBtnHint.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_hint:
            mGameView.hint();
            break;
            default:
                break;
        }
    }
}
