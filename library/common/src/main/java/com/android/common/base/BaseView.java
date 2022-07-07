package com.android.common.base;

/**
 * 基类View
 * Created by yangbangwei on 2016/10/27.
 * Email：bangweiyang@gmail.com
 */
public interface BaseView {
    void stopLoading();
    void showEmptyTip();
    void showErrorTip(String msg);
}
