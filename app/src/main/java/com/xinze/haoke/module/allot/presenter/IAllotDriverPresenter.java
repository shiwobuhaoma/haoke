package com.xinze.haoke.module.allot.presenter;

import com.xinze.haoke.module.allot.view.IAllotDriverView;
import com.xinze.haoke.mvpbase.BasePresenter;

public interface IAllotDriverPresenter extends BasePresenter<IAllotDriverView> {
    void getMyTruckDrivers(int pageNum, int pageSize, String bool);

    void truckAllotDriver(String truckId, String driverId, String rightFlag, String id);
}
