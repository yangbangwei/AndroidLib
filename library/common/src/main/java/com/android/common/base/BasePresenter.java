package com.android.common.base;

import android.content.Context;

import com.android.common.baserx.RxManager;

/**
 * 基类Presenter
 * Created by yangbangwei on 2016/10/27.
 * Email：bangweiyang@gmail.com
 */
public abstract class BasePresenter<T, E> {
    public Context mContext;
    public E mModel;
    public T mView;
    public RxManager mRxManager = new RxManager();

    public void setVM(T v, E m) {
        this.mView = v;
        this.mModel = m;
    }

    public void onDestroy() {
        mRxManager.clear();
    }
}
