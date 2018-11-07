package com.xinze.haoke.module.receive.presenter;

import com.xinze.haoke.module.receive.view.IReceiverBillDetailsListView;
import com.xinze.haoke.mvpbase.BasePresenter;

public interface IReceiverBillDetailsListPresenter extends BasePresenter<IReceiverBillDetailsListView> {
    void getBillOrderListForOwner(int pageNo,int pageSize,String waybillId);
}
