package com.jd.fill.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jd.fill.R;
import com.jd.fill.adapter.MapAdapter;
import com.jd.fill.config.Config;
import com.jd.fill.manager.AdsManager;
import com.jd.fill.manager.DataManager;
import com.jd.fill.util.GeneralUtil;
import com.jd.fill.util.ScreenUtil;
import com.jd.fill.util.StatusBarUtil;

public class MapActivity extends AppCompatActivity {

    private RecyclerView mRecycleView;
    private MapAdapter mMapAdapter;
    private TextView mLevelText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        StatusBarUtil.setWindowStatusBarColor(this, R.color.status_bar_map_color);
        StatusBarUtil.StatusBarLightMode(this);

        bindView();
        showRateUs();
    }

    private void bindView()
    {
        mMapAdapter = new MapAdapter(this, DataManager.getInstance().getmGameInfo().size());
        mRecycleView = (RecyclerView)findViewById(R.id.map_recycle);
        mRecycleView.setLayoutManager(new GridLayoutManager(this, ScreenUtil.isTablet(this)? 7 : 5));
        mRecycleView.setAdapter(mMapAdapter);

        View header = LayoutInflater.from(this).inflate(R.layout.map_header, mRecycleView, false);
        mMapAdapter.setHeaderView(header);

        View footer = LayoutInflater.from(this).inflate(R.layout.map_footer, mRecycleView, false);
        mMapAdapter.setFooterView(footer);

        mMapAdapter.setOnMapItemClickListener(new MapAdapter.OnMapItemClickListener() {
            @Override
            public void OnClickItem(View view, int position) {
//                Toast.makeText(getBaseContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();

                if (Config.mCurrentLevel >= position)
                {
                    Config.mChooseLevel = position;
                    Intent intent = new Intent(MapActivity.this, GameActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void OnClickHeaderOrFooter(View view, boolean isHeader) {

            }
        });

        Button back = (Button)findViewById(R.id.map_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mLevelText = (TextView)findViewById(R.id.map_star_text);
        updateLevel();
    }

    private void showRateUs()
    {
        if (!Config.mIsRateus)
        {
            if (Config.mRateUsCount % 5 == 0)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Enjoy Our App?");
                builder.setMessage("Leave us a review to show your support!  " +
                        GeneralUtil.getEmojiString(0x2764) +
                        GeneralUtil.getEmojiString(0x1F338) +
                        GeneralUtil.getEmojiString(0x1F340));
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Config.mIsRateus = true;
                        Config.saveConfigInfo();
                        GeneralUtil.openGooglePlay(getApplicationContext());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        }
    }

    private void updateLevel()
    {
        StringBuilder builder = new StringBuilder();
        builder.append(Config.mCurrentLevel).append("/").append(DataManager.getInstance().getmGameInfo().size());
        mLevelText.setText(builder.toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapAdapter.notifyDataSetChanged();
        updateLevel();

        if (AdsManager.mEnableShowIntertital)
            AdsManager.showIntertitialAd();
        AdsManager.mEnableShowIntertital = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
