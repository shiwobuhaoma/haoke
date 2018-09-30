package com.xinze.haoke.module.ordinary.presenter;

import com.xinze.haoke.module.ordinary.modle.Bill;
import com.xinze.haoke.module.ordinary.view.IOrdinarySendGoodsView;
import com.xinze.haoke.mvpbase.BasePresenter;

public interface IOrdinarySendGoodsPresenter extends BasePresenter<IOrdinarySendGoodsView> {
    void getAreaList(String extId);
    void releaseTheBillOfGoods(Bill bill);
}
