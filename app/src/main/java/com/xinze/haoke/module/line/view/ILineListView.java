package com.xinze.haoke.module.line.view;

import com.xinze.haoke.mvpbase.BaseView;

public interface ILineListView extends BaseView {
    void getRegularRouteListSuccess(String msg);

    void getRegularRouteListFailed(String msg);

    void delRegularRouteSuccess(String msg);

    void delRegularRouteFailed(String msg);

}
