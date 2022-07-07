package com.android.common.base;


import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.common.R;
import com.android.common.app.AppManager;
import com.android.common.baserx.RxManager;
import com.android.common.daynight.ChangeModeController;
import com.android.common.helper.TUtil;
import com.android.common.utils.AdaptScreenUtils;
import com.android.common.utils.BarUtils;
import com.android.common.utils.LanguageUtils;
import com.android.common.widget.LoadingDialog;
import com.android.common.widget.LoadingTip;
import com.android.common.widget.NormalTitleBar;

import butterknife.ButterKnife;

/**
 * 基类Activity
 * Created by yangbangwei on 2016/10/27.
 * Email：bangweiyang@gmail.com
 */

/*****************
 * 使用例子
 *********************/
//1.mvp模式
//public class SampleActivity extends BaseActivity<NewsChanelPresenter, NewsChannelModel>implements NewsChannelContract.View {
//    @Override
//    public int getLayoutId() {
//        return R.layout.activity_news_channel;
//    }
//
//    @Override
//    public void initView() {
//    }
//}
//2.普通模式
//public class SampleActivity extends BaseActivity {
//    @Override
//    public int getLayoutId() {
//        return R.layout.activity_news_channel;
//    }
//
//    @Override
//    public void initView() {
//    }
//}
public abstract class BaseActivity<T extends BasePresenter, E extends BaseModel>
        extends AppCompatActivity {
    protected T mPresenter;
    protected Activity mContext;
    protected RxManager mRxManager;
    protected NormalTitleBar mTitleBar;
    protected LoadingTip mLoadingTip;
    protected LoadingDialog mLoadingDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRxManager = new RxManager();
        mContext = this;
        doBeforeSetcontentView();
        LanguageUtils.setLanguage(mContext);
        setContentView(getLayoutId());

        // 默认着色状态栏
        BarUtils.setColorNoTranslucent(this, ContextCompat.getColor(this, R.color.black));
//        LightStatusBarUtils.setLightStatusBar(mContext, true);
        ButterKnife.bind(this);
        mLoadingDialog = new LoadingDialog(mContext);
        mPresenter = TUtil.getT(this, 0);
        if (mPresenter != null) {
            mPresenter.mContext = this;
            if (this instanceof BaseView) {
                mPresenter.setVM(this, TUtil.getT(this, 1));
            }
        }

        mTitleBar = ButterKnife.findById(this, R.id.bar_normal);
        if (mTitleBar != null) {
            mTitleBar.setOnBackListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        mLoadingTip = ButterKnife.findById(this, R.id.loading_tip);
        if (null != mLoadingTip) {
            mLoadingTip.setOnReloadListener(new LoadingTip.onReloadListener() {
                @Override
                public void reload() {
                    onReload();
                }
            });
        }
        initView();
    }

    @Override
    public Resources getResources() {
        return AdaptScreenUtils.adaptHeight(super.getResources(), 1920);
    }

    /**
     * 设置layout前配置
     */
    private void doBeforeSetcontentView() {
        //设置昼夜主题
        initTheme();
        // 把actvity放到application栈中管理
        AppManager.getAppManager().addActivity(this);
        // 无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    /***************************** 子类实现*****************************/
    /**
     * 获取布局文件
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化view
     */
    protected abstract void initView();

    /**
     * 重新加载
     */
    public void onReload() {
    }

    /**
     * 设置主题
     */
    private void initTheme() {
        ChangeModeController.setTheme(mContext, R.style.DayTheme, R.style.NightTheme);
    }

    protected void showNext(Fragment fragment, int id) {
        showNext(fragment, id, null, true);
    }

    protected void showNext(Fragment fragment, int id, Bundle b) {
        showNext(fragment, id, b, true);
    }

    protected void showNext(Fragment fragment, int id, Bundle b, boolean isAddBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.screen_push_left_in, R.anim.screen_push_left_out);
        fragmentTransaction.replace(id, fragment);
        if (b != null) {
            fragment.setArguments(b);
        }
        if (isAddBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void showLoading() {
        if (mLoadingTip != null) {
            mLoadingTip.setLoadingTip(LoadingTip.LoadStatus.loading);
        }
    }

    public void stopLoading() {
        if (mLoadingTip != null) {
            mLoadingTip.setLoadingTip(LoadingTip.LoadStatus.finish);
        }
    }

    public void showEmptyTip() {
        if (mLoadingTip != null) {
            mLoadingTip.setLoadingTip(LoadingTip.LoadStatus.empty);
        }
    }

    public void showErrorTip(String msg) {
        if (mLoadingTip != null) {
            mLoadingTip.setLoadingTip(LoadingTip.LoadStatus.error);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        mLoadingDialog.cancel();
        mRxManager.clear();
        ButterKnife.unbind(this);
        AppManager.getAppManager().finishActivity(this);
    }
}