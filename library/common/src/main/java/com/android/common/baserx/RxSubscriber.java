package com.android.common.baserx;

import android.content.Context;

import com.android.common.R;
import com.android.common.base.BaseApplication;
import com.android.common.utils.LogUtils;
import com.android.common.utils.NetWorkUtils;
import com.android.common.utils.ToastUtils;
import com.android.common.widget.LoadingDialog;

import rx.Subscriber;

/**
 * 订阅封装,处理对话框
 * Created by yangbangwei on 2016/10/27.
 * Email：bangweiyang@gmail.com
 */

/*********************
 * 使用例子
 ********************/
/*_apiService.login(mobile, verifyCode)
        .//省略
        .subscribe(new RxSubscriber<User user>(mContext,false) {
@Override
public void _onNext(User user) {
        // 处理user
        }

@Override
public void _onError(String msg) {
        ToastUtils.showShort(mActivity, msg);
        });*/
public abstract class RxSubscriber<T> extends Subscriber<T> {

    private Context mContext;
    private String msg;
    private boolean showDialog = true;
    private LoadingDialog loadingDialog;

    public RxSubscriber(Context context) {
        this(context, BaseApplication.getAppContext().getString(R.string.loading), true);
    }

    public RxSubscriber(Context context, boolean showDialog) {
        this(context, BaseApplication.getAppContext().getString(R.string.loading), showDialog);
    }

    public RxSubscriber(Context context, String msg, boolean showDialog) {
        this.mContext = context;
        this.msg = msg;
        this.showDialog = showDialog;
        //是否显示弹窗
        if (showDialog) {
            loadingDialog = new LoadingDialog(mContext);
        }
    }

    @Override
    public void onCompleted() {
        if (showDialog) {
            loadingDialog.cancel();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (showDialog) {
            try {
                loadingDialog.show(msg, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onNext(T t) {
        if (showDialog) {
            loadingDialog.cancel();
        }
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        if (showDialog) {
            loadingDialog.cancel();
        }
        if (!NetWorkUtils.isNetConnected()) {
            //无网络
            onError(BaseApplication.getAppContext().getString(R.string.no_net));
            ToastUtils.showShort(mContext.getString(R.string.no_net));
        } else if (e instanceof ServerException) {
            //服务器返回的错误提醒
            ToastUtils.showShort(e.getMessage());
            onError(e.getMessage());
        } else {//其它
            onError(mContext.getString(R.string.net_error));
            ToastUtils.showShort(mContext.getString(R.string.net_error));
            LogUtils.logd("报错信息：" + e.toString());
        }
    }

    /**
     * 请求成功
     *
     * @param t
     */
    protected abstract void onSuccess(T t);

    /**
     * 请求失败
     *
     * @param message
     */
    protected abstract void onError(String message);

}
