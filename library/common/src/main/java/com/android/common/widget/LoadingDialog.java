package com.android.common.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;

import com.android.common.R;

/**
 * 弹出浮动加载进度条
 * Created by yangbangwei on 2016/10/27.
 * Email：bangweiyang@gmail.com
 */
public class LoadingDialog {
    /**
     * 加载数据对话框
     */
    private Dialog mLoadingDialog;
    private Context mContext;
    private AppCompatImageView mIvLoad;

    public LoadingDialog(Context context) {
        mContext = context;
    }

    public void show() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_loading, null);
        TextView loadingText = (TextView) view.findViewById(R.id.id_tv_loading_dialog_text);
        mIvLoad = (AppCompatImageView) view.findViewById(R.id.iv_load);
        loadingText.setText("加载中...");
        RotateAnimation rotate = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(1000);
        rotate.setFillAfter(true);
        rotate.setRepeatCount(-1);
        LinearInterpolator lin = new LinearInterpolator();
        rotate.setInterpolator(lin);
        mIvLoad.startAnimation(rotate);
        mLoadingDialog = new Dialog(mContext, R.style.CustomProgressDialog);
        mLoadingDialog.setCancelable(true);
        mLoadingDialog.setCanceledOnTouchOutside(false);
        mLoadingDialog.setContentView(view);
        if (null != mLoadingDialog
                && !((Activity) mContext).isFinishing()) {
            mLoadingDialog.show();
        }
    }

    /**
     * 显示加载对话框
     *
     * @param msg        对话框显示内容
     * @param cancelable 对话框是否可以取消
     */
    public void show(String msg, boolean cancelable) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_loading, null);
        TextView loadingText = view.findViewById(R.id.id_tv_loading_dialog_text);
        mIvLoad = view.findViewById(R.id.iv_load);
        loadingText.setText(msg);
        RotateAnimation rotate = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(1000);
        rotate.setFillAfter(true);
        rotate.setRepeatCount(-1);
        LinearInterpolator lin = new LinearInterpolator();
        rotate.setInterpolator(lin);
        mIvLoad.startAnimation(rotate);
        mLoadingDialog = new Dialog(mContext, R.style.CustomProgressDialog);
        mLoadingDialog.setCancelable(cancelable);
        mLoadingDialog.setCanceledOnTouchOutside(false);
        mLoadingDialog.setContentView(view);
        if (null != mLoadingDialog
                && !((Activity) mContext).isFinishing()) {
            mLoadingDialog.show();
        }
    }

    /**
     * 判断是否显示
     *
     * @return
     */
    public boolean isShowing() {
        return mLoadingDialog.isShowing();
    }

    /**
     * 关闭对话框
     */
    public void cancel() {
        if (null != mLoadingDialog
                && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }
}
