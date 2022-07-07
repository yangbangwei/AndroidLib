package com.android.common.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.common.R;
import com.android.common.utils.DisplayUtils;

/**
 * 自定义标题栏
 * Created by yangbangwei on 2016/10/27.
 * Email：bangweiyang@gmail.com
 */
public class NormalTitleBar extends RelativeLayout {

    private ImageView ivRight;
    private TextView tvTitle, tvRight;
    private ImageView ivBack;
    private RelativeLayout rlCommonTitle;
    private Context context;

    public NormalTitleBar(Context context) {
        super(context, null);
        this.context = context;
    }

    public NormalTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        View.inflate(context, R.layout.bar_normal, this);
        ivBack = findViewById(R.id.iv_back);
        tvTitle = findViewById(R.id.tv_title);
        tvRight = findViewById(R.id.tv_right);
        ivRight = findViewById(R.id.image_right);
        rlCommonTitle = findViewById(R.id.common_title);
        //setHeaderHeight();
    }

    public void setHeaderHeight() {
        rlCommonTitle.setPadding(0, DisplayUtils.getStatusBarHeight(context), 0, 0);
        rlCommonTitle.requestLayout();
    }

    /**
     * 管理返回按钮
     */
    public void setBackVisibility(boolean visible) {
        if (visible) {
            ivBack.setVisibility(View.VISIBLE);
        } else {
            ivBack.setVisibility(View.GONE);
        }
    }

    /**
     * 设置标题栏左侧字符串
     *
     * @param visiable
     */
    public void setTvLeftVisiable(boolean visiable) {
        if (visiable) {
            ivBack.setVisibility(View.VISIBLE);
        } else {
            ivBack.setVisibility(View.GONE);
        }
    }


    /**
     * 管理标题
     */
    public void setTitleVisibility(boolean visible) {
        if (visible) {
            tvTitle.setVisibility(View.VISIBLE);
        } else {
            tvTitle.setVisibility(View.GONE);
        }
    }

    public void setTitleText(String string) {
        tvTitle.setText(string);
    }

    public void setTitleText(int string) {
        tvTitle.setText(string);
    }

    public void setTitleColor(int color) {
        tvTitle.setTextColor(color);
    }

    /**
     * 右图标
     */
    public void setRightImagVisibility(boolean visible) {
        ivRight.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void setRightImagSrc(int id) {
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(id);
    }

    /**
     * 获取右按钮
     *
     * @return
     */
    public View getRightImage() {
        return ivRight;
    }

    /**
     * 左图标
     *
     * @param id
     */
    public void setLeftImagSrc(int id) {
        ivBack.setImageResource(id);
    }

    /**
     * 右标题
     */
    public void setRightTitleVisibility(boolean visible) {
        tvRight.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void setRightTitle(String text) {
        if (!TextUtils.isEmpty(text)) {
            tvRight.setText(text);
            tvRight.setVisibility(VISIBLE);
        }
    }

    public TextView getRightTitle() {
        return tvRight;
    }

    /*
     * 点击事件
     */
    public void setOnBackListener(OnClickListener listener) {
        ivBack.setOnClickListener(listener);
    }

    public void setOnRightImagListener(OnClickListener listener) {
        ivRight.setOnClickListener(listener);
    }

    public void setOnRightTextListener(OnClickListener listener) {
        tvRight.setOnClickListener(listener);
    }

    /**
     * 标题背景颜色
     *
     * @param color
     */
    public void setBackGroundColor(int color) {
        rlCommonTitle.setBackgroundColor(color);
    }

    public Drawable getBackGroundDrawable() {
        return rlCommonTitle.getBackground();
    }

}
