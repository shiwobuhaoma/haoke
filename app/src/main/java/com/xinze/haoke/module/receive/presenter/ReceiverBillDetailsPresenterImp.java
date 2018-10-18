package com.xinze.haoke.module.receive.presenter;

import android.content.Context;

import com.xinze.haoke.http.RetrofitFactory;
import com.xinze.haoke.http.config.HeaderConfig;
import com.xinze.haoke.http.entity.BaseEntity;
import com.xinze.haoke.http.observer.BaseObserver;
import com.xinze.haoke.module.receive.modle.ReceiverBill;
import com.xinze.haoke.module.receive.view.IReceiverBillDetailsView;
import com.xinze.haoke.module.receive.view.ReceiverBillDetailsActivity;
import com.xinze.haoke.mvpbase.BasePresenterImpl;

import java.util.HashMap;
import java.util.List;

public class ReceiverBillDetailsPresenterImp extends BasePresenterImpl<IReceiverBillDetailsView> implements IReceiverBillDetailsPresenter {

    private ReceiverBillDetailsActivity mActivity;

    public ReceiverBillDetailsPresenterImp(IReceiverBillDetailsView mPresenterView, Context mContext) {
        super(mPresenterView, mContext);
        mActivity = (ReceiverBillDetailsActivity) mPresenterView;
    }

    @Override
    public void getBillOrderListForOwner(int pageNo, int pageSize, String waybillId) {
        HashMap<String, String> headers = HeaderConfig.getHeaders();
        RetrofitFactory.getInstence().Api().getBillOrderListForOwner(headers,pageNo,pageSize,waybillId)
                .compose(this.<BaseEntity<ReceiverBill>>setThread())
                .subscribe(new BaseObserver<ReceiverBill>() {
                    @Override
                    protected void onSuccess(BaseEntity<ReceiverBill> t) throws Exception {
                        if (t != null){
                            if (t.isSuccess()){
                                ReceiverBill rb = t.getData();
                                List<ReceiverBill.WaybillOrderEntitiesBean> waybillOrderEntities = rb.getWaybillOrderEntities();

                                mActivity.setData(waybillOrderEntities);
                            }else {
                                mActivity.getBillOrderListForOwnerFailed(t.getMsg());
                            }
                        }

                    }

                    @Override
                    protected void onFailure(String msg) throws Exception {
                        mActivity.getBillOrderListForOwnerFailed(msg);
                    }
                });
    }
}
