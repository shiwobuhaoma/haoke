package com.xinze.haoke.module.regular.presenter;

import com.xinze.haoke.module.regular.view.IRegularRouteView;
import com.xinze.haoke.mvpbase.BasePresenter;

public interface IRegularRunPresenter extends BasePresenter<IRegularRouteView> {
    void getRegularRouteList();
    void searchRouteList(String fromAreaId,String toAreaId,int pageNo,int pageSize);

}
