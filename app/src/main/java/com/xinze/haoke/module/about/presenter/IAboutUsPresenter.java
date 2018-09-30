package com.xinze.haoke.module.about.presenter;

import com.xinze.haoke.module.about.view.IAboutUsView;
import com.xinze.haoke.mvpbase.BasePresenter;

public interface IAboutUsPresenter extends BasePresenter<IAboutUsView> {
    void getAboutUs(String aboutAsType);
    void getProtocolByType(String protocolType);
}
