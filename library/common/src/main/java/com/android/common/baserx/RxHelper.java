package com.android.common.baserx;

import com.android.common.base.BaseRespose;
import com.android.common.utils.LogUtils;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 对服务器返回数据成功和失败处理
 * Created by yangbangwei on 2016/10/27.
 * Email：bangweiyang@gmail.com
 */

/**************
 * 使用例子
 ******************/
/*_apiService.login(mobile, verifyCode)
        .compose(RxSchedulersHelper.io_main())
        .compose(RxResultHelper.handleResult())
        .//省略*/
public class RxHelper {
    /**
     * 对服务器返回数据进行预处理
     *
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<BaseRespose<T>, T> handleResult() {
        return new Observable.Transformer<BaseRespose<T>, T>() {
            @Override
            public Observable<T> call(Observable<BaseRespose<T>> tObservable) {
                return tObservable.flatMap(new Func1<BaseRespose<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(BaseRespose<T> result) {
                        LogUtils.logd("result from api : " + result);
                        if (result.success()) {
                            return createData(result.datas);
                        } else {
                            return Observable.error(new ServerException(result.code, result.msg));
                        }
                    }
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    /**
     * 创建成功的数据
     *
     * @param datas
     * @param <T>
     * @return
     */
    private static <T> Observable<T> createData(final T datas) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(datas);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });

    }
}
