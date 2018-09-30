package com.xinze.haoke.module.change.view;

import com.xinze.haoke.mvpbase.BaseView;

public interface IChangePassWordView extends BaseView {
    void changePassWordSuccess(String msg);
    void changePassWordFailed(String msg);
}
