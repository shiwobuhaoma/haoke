package com.xinze.haoke.module.select.carType.view;

import com.xinze.haoke.mvpbase.BaseView;

public interface ISelectCarTypeView extends BaseView {
    void getCarTypeSuccess(String msg);
    void getCarTypeFailed(String msg);

}
