package com.xinze.haoke.module.line.view;

import com.xinze.haoke.mvpbase.BaseView;

public interface ILineEditView extends BaseView {
    void addRegularRouteSuccess(String msg);

    void addRegularRouteFailed(String msg);

    void getAreaListSuccess(String msg);

    void getAreaListFailed(String msg);
}
