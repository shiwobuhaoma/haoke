package com.xinze.haoke.module.receive.presenter;

import com.xinze.haoke.module.receive.view.IReceiverBillDetailsView;
import com.xinze.haoke.mvpbase.BasePresenter;

public interface IReceiverBillDetailsPresenter extends BasePresenter<IReceiverBillDetailsView> {
    void getBillOrderListForOwner(int pageNo,int pageSize,String waybillId);
}
