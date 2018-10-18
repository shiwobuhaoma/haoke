package com.xinze.haoke.module.relay.presenter;

import android.content.Context;

import com.xinze.haoke.http.RetrofitFactory;
import com.xinze.haoke.http.config.HeaderConfig;
import com.xinze.haoke.module.relay.view.IRelayView;
import com.xinze.haoke.mvpbase.BasePresenterImpl;

import java.util.HashMap;

public class RelayPresenterImp extends BasePresenterImpl<IRelayView> implements IRelayPresenter {
    public RelayPresenterImp(IRelayView mPresenterView, Context mContext) {
        super(mPresenterView, mContext);
    }

    @Override
    public void getBillInfoForTrans(String id) {
        HashMap<String, String> headers = HeaderConfig.getHeaders();
        RetrofitFactory.getInstence().Api().getBillInfoForTrans(headers,id);
    }
}
