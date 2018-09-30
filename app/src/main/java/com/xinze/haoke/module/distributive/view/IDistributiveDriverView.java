package com.xinze.haoke.module.distributive.view;

import com.xinze.haoke.mvpbase.BaseView;

public interface IDistributiveDriverView extends BaseView {
    void appointDriver4TruckSuccess(String msg);

    void appointDriver4TruckFailed(String msg);

    void getMyTruckDriversSuccess(String msg);

    void getMyTruckDriversFailed(String msg);
}
