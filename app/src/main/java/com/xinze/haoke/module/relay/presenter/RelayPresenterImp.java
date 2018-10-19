package com.xinze.haoke.module.relay.presenter;

import android.content.Context;

import com.xinze.haoke.http.RetrofitFactory;
import com.xinze.haoke.http.config.HeaderConfig;
import com.xinze.haoke.http.entity.BaseEntity;
import com.xinze.haoke.http.observer.BaseObserver;
import com.xinze.haoke.module.ordinary.modle.Bill;
import com.xinze.haoke.module.relay.view.IRelayView;
import com.xinze.haoke.module.relay.view.RelayActivity;
import com.xinze.haoke.mvpbase.BasePresenterImpl;

import java.util.HashMap;

public class RelayPresenterImp extends BasePresenterImpl<IRelayView> implements IRelayPresenter {
    private RelayActivity mActivity;

    public RelayPresenterImp(IRelayView mPresenterView, Context mContext) {
        super(mPresenterView, mContext);

        mActivity = (RelayActivity) mPresenterView;
    }

    @Override
    public void getBillInfoForTrans(String id) {
        HashMap<String, String> headers = HeaderConfig.getHeaders();
        RetrofitFactory.getInstence().Api().getBillInfoForTrans(headers, id)
                .compose(this.setThread())
                .subscribe(new BaseObserver<Bill>(mContext) {
                    @Override
                    protected void onSuccess(BaseEntity<Bill> t) throws Exception {
                        if (t != null) {
                            if (t.isSuccess()) {
                                mActivity.setData(t.getData());
                            } else {
                                mActivity.getBillInfoForTransFailed(t.getMsg());
                            }
                        }
                    }

                    @Override
                    protected void onFailure(String msg) throws Exception {
                        mActivity.getBillInfoForTransFailed(msg);
                    }
                });
    }
    @SuppressWarnings("unchecked")
    @Override
    public void getDriverCounts() {
        HashMap<String, String> headers = HeaderConfig.getHeaders();
        RetrofitFactory.getInstence().Api().getDriverCounts(headers)
                .compose(this.setThread())
                .subscribe(new BaseObserver(mContext) {
                    @Override
                    protected void onSuccess(BaseEntity t) throws Exception {
                        if (t != null) {
                            if (t.isSuccess()) {
                                mActivity.setNumData(t.getData());
                            } else {
                                mActivity.getBillInfoForTransFailed(t.getMsg());
                            }
                        }
                    }

                    @Override
                    protected void onFailure(String msg) throws Exception {
                        mActivity.getBillInfoForTransFailed(msg);
                    }
                });
    }
}
