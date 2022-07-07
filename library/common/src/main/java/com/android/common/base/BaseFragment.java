package com.android.common.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.common.R;
import com.android.common.baserx.RxManager;
import com.android.common.helper.TUtil;
import com.android.common.widget.LoadingTip;
import com.android.common.widget.NormalTitleBar;

import butterknife.ButterKnife;

/**
 * 基类Fragment
 * Created by yangbangwei on 2016/10/27.
 * Email：bangweiyang@gmail.com
 */

/***************
 * 使用例子
 *********************/
//1.mvp模式
//public class SampleFragment extends BaseFragment<NewsChanelPresenter, NewsChannelModel>implements NewsChannelContract.View {
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
//public class SampleFragment extends BaseFragment {
//    @Override
//    public int getLayoutResource() {
//        return R.layout.activity_news_channel;
//    }
//
//    @Override
//    public void initView() {
//    }
//}
public abstract class BaseFragment<T extends BasePresenter, E extends BaseModel> extends Fragment {
    protected View rootView;
    public T mPresenter;
    public RxManager mRxManager;
    public Activity mContext;
    public NormalTitleBar mTitleBar;
    public LoadingTip mLoadingTip;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutResource(), container, false);
        }
        mRxManager = new RxManager();
        ButterKnife.bind(this, rootView);
        mPresenter = TUtil.getT(this, 0);
        if (mPresenter != null) {
            mPresenter.mContext = mContext;
        }

        if (this instanceof BaseView) {
            mPresenter.setVM(this, TUtil.getT(this, 1));
        }

        mTitleBar = ButterKnife.findById(rootView, R.id.bar_normal);
        if (mTitleBar != null) {
            mTitleBar.setOnBackListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    back();
                }
            });
        }
        mLoadingTip = ButterKnife.findById(rootView, R.id.loading_tip);

        //状态错误，重新加载
        if (null != mLoadingTip) {
            mLoadingTip.setOnReloadListener(new LoadingTip.onReloadListener() {
                @Override
                public void reload() {
                    onReload();
                }
            });
        }

        initView();

        return rootView;
    }

    /**
     * 获取布局文件
     *
     * @return
     */
    protected abstract int getLayoutResource();

    /**
     * 初始化view
     */
    protected abstract void initView();

    /**
     * 重新加载
     */
    public void onReload() {

    }

    protected void showNext(Fragment fragment, int id) {
        showNext(fragment, id, null, true);
    }

    protected void showNext(Fragment fragment, int id, Bundle b) {
        showNext(fragment, id, b, true);
    }

    protected void showNext(Fragment fragment, int id, Bundle b, boolean isAddBackStack) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
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

    /**
     * 回退
     */
    protected void back() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.popBackStackImmediate();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        /**初始化常用数据**/
        this.mContext = activity;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        mRxManager.clear();
    }


}
