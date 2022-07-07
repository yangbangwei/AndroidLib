package com.android.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 禁止滚动的Listview
 * Created by yangbangwei on 2016/10/27.
 * Email：bangweiyang@gmail.com
 */
public class NoScrollListview extends ListView {
    public NoScrollListview(Context context) {
        super(context);
    }

    public NoScrollListview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}