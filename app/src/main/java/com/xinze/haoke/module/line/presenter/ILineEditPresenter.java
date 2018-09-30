package com.xinze.haoke.module.line.presenter;

import com.xinze.haoke.module.line.view.ILineEditView;
import com.xinze.haoke.mvpbase.BasePresenter;

public interface ILineEditPresenter extends BasePresenter<ILineEditView> {
    void addRegularRoute(String fromAreaId, String toAreaId);

    void editRegularRoute(String id, String fromAreaId, String toAreaId);

    void getAreaList(String extId);
}
