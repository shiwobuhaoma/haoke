package com.xinze.haoke.module.select.goodstype.view;

import com.xinze.haoke.mvpbase.BaseView;
/**
 * @author lxf
 */
public interface ISelectGoodsTypeView extends BaseView {
    void getUsualGoodsTypeSuccess(String msg);
    void getUsualGoodsTypeFailed(String msg);
}
