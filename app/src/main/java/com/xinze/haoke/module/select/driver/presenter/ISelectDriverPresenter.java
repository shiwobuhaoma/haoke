package com.xinze.haoke.module.select.driver.presenter;

import com.xinze.haoke.module.select.driver.view.ISelectDriverView;
import com.xinze.haoke.module.ordinary.modle.Bill;
import com.xinze.haoke.mvpbase.BasePresenter;

public interface ISelectDriverPresenter extends BasePresenter<ISelectDriverView> {
    void release(Bill bill);

    void getAlwaysDriver();
}
