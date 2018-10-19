package com.xinze.haoke.module.main.view;

import com.xinze.haoke.module.ordinary.modle.Bill;
import com.xinze.haoke.mvpbase.BaseView;

import java.util.List;

public interface IOrderView extends BaseView {
    void getOrderListSuccess();
    void getOrderListFailed();
    void upData(List<Bill> data);
    void clearData();
}
