package com.xinze.haoke.module.select.goodstype.presenter;

import com.xinze.haoke.module.select.goodstype.view.ISelectGoodsTypeView;
import com.xinze.haoke.mvpbase.BasePresenter;
/**
 * @author lxf
 */
public interface ISelectGoodsTypePresenter extends BasePresenter<ISelectGoodsTypeView> {
    void getUsualGoodsType(int pageSize,int pageNum);
}
