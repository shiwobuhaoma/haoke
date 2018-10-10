package com.xinze.haoke.module.select.cartype.view;

import com.xinze.haoke.mvpbase.BaseView;
/**
 * @author lxf
 */
public interface ISelectCarTypeView extends BaseView {
    void getCarTypeSuccess(String msg);
    void getCarTypeFailed(String msg);

}
