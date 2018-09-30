package com.xinze.haoke.module.send.view;

import com.xinze.haoke.mvpbase.BaseView;

public interface IBillView extends BaseView {
    void getBillsSuccess(String msg);
    void getBillsFailed(String msg);
    void searchRouteListSuccess(String msg);
    void searchRouteListFailed(String msg);
}
