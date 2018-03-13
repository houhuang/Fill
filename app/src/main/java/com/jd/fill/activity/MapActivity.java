package com.jd.fill.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.jd.fill.R;
import com.jd.fill.adapter.MapAdapter;
import com.jd.fill.config.Config;
import com.jd.fill.manager.DataManager;
import com.jd.fill.util.StatusBarUtil;

public class MapActivity extends AppCompatActivity {

    private RecyclerView mRecycleView;
    private MapAdapter mMapAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        StatusBarUtil.setWindowStatusBarColor(this, R.color.color_bg_white);
        StatusBarUtil.StatusBarLightMode(this);

        bindView();
    }

    private void bindView()
    {

        mMapAdapter = new MapAdapter(this, DataManager.getInstance().getmGameInfo().size(), Config.mCurrentLevel);
        mRecycleView = (RecyclerView)findViewById(R.id.map_recycle);
        mRecycleView.setLayoutManager(new GridLayoutManager(this, 5));
        mRecycleView.setAdapter(mMapAdapter);

        View header = LayoutInflater.from(this).inflate(R.layout.map_header, mRecycleView, false);
        mMapAdapter.setHeaderView(header);

        View footer = LayoutInflater.from(this).inflate(R.layout.map_footer, mRecycleView, false);
        mMapAdapter.setFooterView(footer);

        mMapAdapter.setOnMapItemClickListener(new MapAdapter.OnMapItemClickListener() {
            @Override
            public void OnClickItem(View view, int position) {
                Toast.makeText(getBaseContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void OnClickHeaderOrFooter(View view, boolean isHeader) {

            }
        });
    }
}
