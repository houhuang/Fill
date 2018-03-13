package com.jd.fill.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by houhuang on 18/3/13.
 */
public class MapItemLayout extends RelativeLayout {

    public MapItemLayout(Context context)
    {
        super(context);
    }

    public MapItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
