package com.xinze.haoke.module.transport.view;

import com.xinze.haoke.mvpbase.BaseView;

public interface ITransportDetailsView extends BaseView {
    void getBillDetailSuccess(String msg);
    void getBillDetailFailed(String msg);

    void getCarryOrderRightSuccess(String msg);
    void getCarryOrderRightFailed(String msg);

    void backBillSuccess(String msg);
    void backBillFailed(String msg);
}
