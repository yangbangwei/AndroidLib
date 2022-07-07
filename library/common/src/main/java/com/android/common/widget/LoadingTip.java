package com.android.common.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.android.common.R;

/**
 * 加载页面内嵌提示
 * Created by yangbangwei on 2016/10/27.
 * Email：bangweiyang@gmail.com
 */
public class LoadingTip extends LinearLayout {

    private AppCompatImageView mIvLogo;
    private AppCompatTextView mTvTips;
    private AppCompatButton mBtnRefresh;
    private AppCompatImageView mIvLoad;
    private RotateAnimation mRotate;
    private onReloadListener onReloadListener;

    public LoadingTip(Context context) {
        super(context);
        initView(context);
    }

    public LoadingTip(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LoadingTip(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LoadingTip(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    //分为服务器失败，网络加载失败、数据为空、加载中、完成四种状态
    public enum LoadStatus {
        sereverError, error, empty, loading, finish
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.dialog_loading_tip, this);
        LinearLayout llContent = (LinearLayout) findViewById(R.id.ll_content);
        llContent.setOnClickListener(null);
        mIvLogo = (AppCompatImageView) findViewById(R.id.iv_logo);
        mTvTips = (AppCompatTextView) findViewById(R.id.tv_tips);
        mBtnRefresh = (AppCompatButton) findViewById(R.id.btn_refresh);
        mIvLoad = (AppCompatImageView) findViewById(R.id.iv_load);
        //重新尝试
        mBtnRefresh.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onReloadListener != null) {
                    onReloadListener.reload();
                }
            }
        });
        mRotate = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotate.setDuration(1000);
        mRotate.setFillAfter(true);
        mRotate.setRepeatCount(-1);
        LinearInterpolator lin = new LinearInterpolator();
        mRotate.setInterpolator(lin);
        setVisibility(View.GONE);
    }

    public void setTips(String tips) {
        if (mTvTips != null) {
            mTvTips.setText(tips);
        }
    }

    /**
     * 根据状态显示不同的提示
     *
     * @param loadStatus
     */
    public void setLoadingTip(LoadStatus loadStatus) {
        switch (loadStatus) {
            case empty://空布局
                setVisibility(View.VISIBLE);
                mIvLogo.setVisibility(View.VISIBLE);
                mBtnRefresh.setVisibility(View.VISIBLE);
                mTvTips.setVisibility(VISIBLE);
                mTvTips.setText(R.string.empty);
                mTvTips.setTextSize(18);
                mIvLoad.setVisibility(GONE);
                mIvLoad.clearAnimation();
                break;
            case sereverError://服务器返回错误
                setVisibility(View.VISIBLE);
                mIvLogo.setVisibility(View.VISIBLE);
                mBtnRefresh.setVisibility(View.VISIBLE);
                mTvTips.setVisibility(VISIBLE);
                mTvTips.setText(R.string.net_error);
                mTvTips.setTextSize(14);
                mIvLoad.setVisibility(GONE);
                mIvLoad.clearAnimation();
                break;
            case error://网络访问错误
                setVisibility(View.VISIBLE);
                mIvLogo.setVisibility(View.VISIBLE);
                mBtnRefresh.setVisibility(View.VISIBLE);
                mTvTips.setVisibility(VISIBLE);
                mTvTips.setText(R.string.no_net);
                mTvTips.setTextSize(14);
                mIvLoad.setVisibility(GONE);
                mIvLoad.clearAnimation();
                mIvLoad.clearAnimation();
                break;
            case loading://加载中
                setVisibility(View.VISIBLE);
                mIvLoad.startAnimation(mRotate);
                mIvLogo.setVisibility(View.GONE);
                mBtnRefresh.setVisibility(View.GONE);
                mTvTips.setVisibility(VISIBLE);
                mTvTips.setText(R.string.loading);
                mTvTips.setTextSize(14);
                mIvLoad.setVisibility(VISIBLE);
                break;
            case finish://结束
                setVisibility(View.GONE);
                mIvLoad.clearAnimation();
                break;
        }
    }

    public void setOnReloadListener(onReloadListener onReloadListener) {
        this.onReloadListener = onReloadListener;
    }

    /**
     * 重新尝试接口
     */
    public interface onReloadListener {
        void reload();
    }


}

