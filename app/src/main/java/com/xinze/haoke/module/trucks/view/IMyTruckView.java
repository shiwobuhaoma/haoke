package com.xinze.haoke.module.trucks.view;

import com.xinze.haoke.mvpbase.BaseView;

public interface IMyTruckView extends BaseView {
    void deleteMyTruckSuccess(String msg);
    void deleteMyTruckFailed(String msg);
}
