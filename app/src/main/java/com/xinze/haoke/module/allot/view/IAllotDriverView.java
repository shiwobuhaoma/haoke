package com.xinze.haoke.module.allot.view;

import com.xinze.haoke.mvpbase.BaseView;

public interface IAllotDriverView extends BaseView {
    void getMyTruckDriversSuccess(String msg);

    void getMyTruckDriversFailed(String msg);


    void truckAllotDriverSuccess(String msg);

    void truckAllotDriverFailed(String msg);
}
