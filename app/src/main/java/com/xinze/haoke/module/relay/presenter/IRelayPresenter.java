package com.xinze.haoke.module.relay.presenter;

import com.xinze.haoke.module.relay.view.IRelayView;
import com.xinze.haoke.mvpbase.BasePresenter;

public interface IRelayPresenter extends BasePresenter<IRelayView> {
    void getBillInfoForTrans(String id);

    void  getDriverCounts();


}
