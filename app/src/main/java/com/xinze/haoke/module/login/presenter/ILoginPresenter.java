package com.xinze.haoke.module.login.presenter;


import com.xinze.haoke.module.login.view.ILoginView;
import com.xinze.haoke.mvpbase.BasePresenter;

/**
 *@author lxf
 * Created by Administrator on 2018/1/4.
 */

public interface ILoginPresenter extends BasePresenter<ILoginView> {
    void login(String name, String pwd,String userType);
}
