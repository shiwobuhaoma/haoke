package com.xinze.haoke.module.main.view;

import com.xinze.haoke.module.main.modle.OrderItem;
import com.xinze.haoke.mvpbase.BaseView;

import java.util.List;

public interface IOrderView extends BaseView {
    void getOrderListSuccess();
    void getOrderListFailed();
    void upData(List<OrderItem> data);
    void clearData();
}
