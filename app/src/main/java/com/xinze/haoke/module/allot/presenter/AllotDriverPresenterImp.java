package com.xinze.haoke.module.allot.presenter;

import android.content.Context;

import com.xinze.haoke.App;
import com.xinze.haoke.http.RetrofitFactory;
import com.xinze.haoke.http.config.HeaderConfig;
import com.xinze.haoke.http.entity.BaseEntity;
import com.xinze.haoke.http.observer.BaseObserver;
import com.xinze.haoke.module.allot.view.AllotDriverActivity;
import com.xinze.haoke.module.allot.view.IAllotDriverView;
import com.xinze.haoke.module.invite.model.TruckownerDriverVO;
import com.xinze.haoke.mvpbase.BasePresenterImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllotDriverPresenterImp extends BasePresenterImpl<IAllotDriverView> implements IAllotDriverPresenter {
    private AllotDriverActivity mActivity;
    public AllotDriverPresenterImp(IAllotDriverView mPresenterView, Context mContext) {
        super(mPresenterView, mContext);
        mActivity = (AllotDriverActivity)mPresenterView;
    }


    @Override
    public void getMyTruckDrivers(int pageNum, int pageSize, String verifyFlag) {
        HashMap<String, String> headers = HeaderConfig.getHeaders();
        RetrofitFactory.getInstence().Api().getMyTrucksList(headers, pageNum, pageSize, verifyFlag)
                .compose(this.<BaseEntity<List<TruckownerDriverVO>>>setThread()).subscribe(new BaseObserver<List<TruckownerDriverVO>>(){

            @Override
            protected void onSuccess(BaseEntity<List<TruckownerDriverVO>> t) throws Exception {
                if (t != null){
                    if (t.isSuccess()){
                        mActivity.setData(t.getData());
//                        mActivity.getMyTruckDriversSuccess(t.getMsg());
                    }
                }
            }

            @Override
            protected void onFailure(String msg)throws Exception {
                mActivity.getMyTruckDriversFailed(msg);
            }
        });
    }

    @Override
    public void truckAllotDriver(String truckId, String driverId, String rightFlag, String id) {

    }
}
