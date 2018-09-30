package com.xinze.haoke.module.login.view;


import com.xinze.haoke.mvpbase.BaseView;

/**
 * @author lxf
 * 登陆业务的提示信息方法
 * Created by Administrator on 2018/1/4.
 */

public interface ILoginView extends BaseView {
    /**
     * 登陆成功
     */
    void loginSuccess(String msg);
    /**
     * 登陆失败
     */
    void loginFailed(String msg);
}
