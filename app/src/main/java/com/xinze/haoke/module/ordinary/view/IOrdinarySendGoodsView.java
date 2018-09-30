package com.xinze.haoke.module.ordinary.view;

import com.xinze.haoke.mvpbase.BaseView;

public interface IOrdinarySendGoodsView extends BaseView {
    void getAreaListSuccess(String msg);

    void getAreaListFailed(String msg);

    void releaseTheBillOfGoodsSuccess(String msg);

    void releaseTheBillOfGoodsFailed(String msg);
}
