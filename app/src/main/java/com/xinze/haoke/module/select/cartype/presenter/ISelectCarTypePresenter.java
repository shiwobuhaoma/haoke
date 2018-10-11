package com.xinze.haoke.module.select.cartype.presenter;

import com.xinze.haoke.module.select.cartype.view.ISelectCarTypeView;
import com.xinze.haoke.mvpbase.BasePresenter;
/**
 * @author lxf
 */
public interface ISelectCarTypePresenter extends BasePresenter<ISelectCarTypeView> {
    /**
     * 获取车辆类型
     */
    void getCarType();
}
