package com.xinze.haoke.module.transport.presenter;

import com.xinze.haoke.module.transport.view.ITransportDetailsView;
import com.xinze.haoke.mvpbase.BasePresenter;

public interface ITransportDetailsPresenter extends BasePresenter<ITransportDetailsView> {
    void getCarryOrderRight(String userId);
    void getBillDetail(String id);
    void backBill(String id);
}
