package com.android.common.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.android.common.R;

/*
 * 自定义Dialog
 */
public class CustomDialog extends Dialog {
    private static int defaultWidth = 160; // 默认宽度
    private static int defaultHeight = 120;// 默认高度

    public CustomDialog(Context context, int layout) {
        this(context, layout, defaultWidth, defaultHeight);
    }

    public CustomDialog(Context context, int layout, int width, int height) {
        super(context, R.style.Dialog);
        init(layout, width, height, Gravity.CENTER);
        setCanceledOnTouchOutside(true);// 点击对话框外部取消对话框显示
    }

    public CustomDialog(Context context, int style, int layout, int width, int height) {
        super(context, style);
        init(layout, width, height, Gravity.CENTER);
        setCanceledOnTouchOutside(true);// 点击对话框外部取消对话框显示
    }

    public CustomDialog(Context context, int style, int layout, int width, int height, int gravity) {
        super(context, style);
        init(layout, width, height, gravity);
        setCanceledOnTouchOutside(true);// 点击对话框外部取消对话框显示
    }

    public CustomDialog(Context context, int layout, int width, int height, boolean cancel, boolean touchOut) {
        super(context, R.style.Dialog);
        init(layout, width, height, Gravity.CENTER);
        setCanceledOnTouchOutside(touchOut);// 点击对话框外部取消对话框显示
        setCancelable(cancel);
    }

    private void init(int layout, int width, int height, int gravity) {
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = width;
        params.height = height;
        params.gravity = gravity;
        window.setAttributes(params);

    }
}