package com.xinze.haoke.module.relay.view;

import com.xinze.haoke.mvpbase.BaseView;

public interface IRelayView extends BaseView {
    void getBillInfoForTransSuccess(String msg);

    void getBillInfoForTransFailed(String msg);
}
