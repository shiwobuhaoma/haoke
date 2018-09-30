package com.xinze.haoke.mvpbase;

import android.content.Context;

import com.xinze.haoke.http.listener.LifeCycleListener;

/**
 * P层的基类
 *
 * @author lxf
 */

public interface BasePresenter<T extends BaseView> extends LifeCycleListener {

    void attachView(T t);
    void detachView();
    void attachActivity(Context context);
    void detachActivity();
}
