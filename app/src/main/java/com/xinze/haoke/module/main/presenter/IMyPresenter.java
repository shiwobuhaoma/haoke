package com.xinze.haoke.module.main.presenter;

import com.xinze.haoke.module.main.view.IMyView;
import com.xinze.haoke.mvpbase.BasePresenter;

/**
 * 退出逻辑
 *
 * @author lxf
 */
public interface IMyPresenter extends BasePresenter<IMyView> {
    /**
     * 注销接口
     */
    void loginOut();

    /**
     * 查询数量接口
     */
    void getCount(String system);
}
