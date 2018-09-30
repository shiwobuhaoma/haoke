package com.xinze.haoke.module.goods.prsenter;

import com.xinze.haoke.module.goods.view.IFrequentlyTransportedGoodsView;
import com.xinze.haoke.mvpbase.BasePresenter;

public interface IFrequentlyTransportedGoodsPresenter extends BasePresenter<IFrequentlyTransportedGoodsView> {
    void getMyUsualCargo(int pageSize,int pageNum);
    void getMyUsualCargoAdd(String str);
    void getMyUsualCargoDel(String str);
}
