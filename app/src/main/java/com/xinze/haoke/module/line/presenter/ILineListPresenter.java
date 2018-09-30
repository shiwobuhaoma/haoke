package com.xinze.haoke.module.line.presenter;

import com.xinze.haoke.module.line.view.ILineListView;
import com.xinze.haoke.mvpbase.BasePresenter;

public interface ILineListPresenter extends BasePresenter<ILineListView> {
    void getRegularRouteList();
    void delRegularRoute(String id);
}
