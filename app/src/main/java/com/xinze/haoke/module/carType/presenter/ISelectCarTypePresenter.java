package com.xinze.haoke.module.carType.presenter;

import com.xinze.haoke.module.carType.view.ISelectCarTypeView;
import com.xinze.haoke.mvpbase.BasePresenter;

public interface ISelectCarTypePresenter extends BasePresenter<ISelectCarTypeView> {
    void getCarType();
}
