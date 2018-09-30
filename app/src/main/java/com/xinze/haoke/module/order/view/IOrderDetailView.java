package com.xinze.haoke.module.order.view;

import com.xinze.haoke.mvpbase.BaseView;

public interface IOrderDetailView extends BaseView{
    void getOrderDetailSuccess(String msg);
    void getOrderDetailFailed(String msg);
    void revokeSuccess(String message,String orderStatus);
    void revokeFailed(String message);

    void  uploadImagesSuccess(String msg);
    void  uploadImagesFailed(String msg);
}
