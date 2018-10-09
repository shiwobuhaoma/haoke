package com.xinze.haoke.module.select.car.presenter;

import com.xinze.haoke.module.select.car.view.ISelectCarView;
import com.xinze.haoke.module.transport.module.Car;
import com.xinze.haoke.mvpbase.BasePresenter;

import java.util.List;

public interface ISelectCarPresenter extends BasePresenter<ISelectCarView> {
    void getCarryTruckList(String id);
    void getProtocolByType(String protocolType);
    void createBillOrder(String wayBillid, List<Car> list);
}
