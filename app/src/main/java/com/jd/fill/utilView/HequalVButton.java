package com.jd.fill.utilView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by houhuang on 18/3/14.
 */
public class HequalVButton extends Button {
    public HequalVButton(Context context) {
        super(context);
    }

    public HequalVButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
