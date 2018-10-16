package com.xinze.haoke.module.receive.view;

import com.xinze.haoke.mvpbase.BaseView;

public interface IReceiverBillDetailsView extends BaseView {
    void getBillOrderListForOwnerSuccess(String msg);

    void getBillOrderListForOwnerFailed(String msg);
}
