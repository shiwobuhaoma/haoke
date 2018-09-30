package com.xinze.haoke.module.distributive.presenter;

import com.xinze.haoke.module.distributive.view.IDistributiveDriverView;
import com.xinze.haoke.mvpbase.BasePresenter;

public interface IDistributiveDriverPresenter extends BasePresenter<IDistributiveDriverView> {
    void appointDriver4Truck(String truckId, String driverId, String rightFlag, String id);
    void getMyTruckDrivers(int pageNum, int pageSize,String inviteFlag);
}
