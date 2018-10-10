package com.xinze.haoke.module.send.presenter;

import android.content.Context;

import com.xinze.haoke.App;
import com.xinze.haoke.config.AppConfig;
import com.xinze.haoke.config.OrderConfig;
import com.xinze.haoke.http.RetrofitFactory;
import com.xinze.haoke.http.config.HeaderConfig;
import com.xinze.haoke.http.entity.BaseEntity;
import com.xinze.haoke.http.observer.BaseObserver;
import com.xinze.haoke.module.main.modle.OrderItem;
import com.xinze.haoke.module.send.fragment.AbstractBillFragment;
import com.xinze.haoke.module.send.fragment.DirectionalBillFragment;
import com.xinze.haoke.module.send.view.IBillView;
import com.xinze.haoke.mvpbase.BasePresenterImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BillPresenterImp extends BasePresenterImpl<IBillView> implements IBillPresenter {

    private AbstractBillFragment mBillFragment;
    public BillPresenterImp(IBillView mPresenterView, Context mContext) {
        super(mPresenterView, mContext);
        mBillFragment = (AbstractBillFragment) mPresenterView;
    }

    @Override
    public void getBillList(int wlBilltype, int pageNum, int pageSize,String remarks) {
        Map<String, String> headers = new HashMap<>(2);
        if (wlBilltype == OrderConfig.DIRECTIONALBILL){
            headers.put("sessionid", App.mUser.getSessionid());
            headers.put("userid",App.mUser.getId());
        }

        RetrofitFactory.getInstence().Api().getBillList(headers,wlBilltype,pageNum,pageSize,remarks).compose(this.<BaseEntity<List<OrderItem>>>setThread()).subscribe(new BaseObserver<List<OrderItem>>(){

            @Override
            protected void onSuccess(BaseEntity<List<OrderItem>> t) throws Exception {
                if (t != null){
                    if (t.isSuccess()){
                        List<OrderItem> data = t.getData();
                        if (data != null){
                            mBillFragment.setData(data);
                            mBillFragment.getBillsSuccess(t.getMsg());
                        }else{
                            mBillFragment.getBillsSuccess(t.getMsg());
                            mBillFragment.shotToast(AppConfig.LOAD_INFO_FINISH);
                        }
                    }else{
                        mBillFragment.getBillsFailed(t.getMsg());
                    }
                }
            }

            @Override
            protected void onFailure(String msg) throws Exception {
                mBillFragment.getBillsFailed(msg);
            }
        } );
    }

    @Override
    public void searchRouteList(String fromAreaId,String toAreaId,int pageNo,int pageSize) {
        HashMap<String, String> headers = HeaderConfig.getHeaders();
        RetrofitFactory.getInstence().Api().searchRoute(headers,fromAreaId,toAreaId,pageNo,pageSize).compose(this.<BaseEntity<List<OrderItem>>>setThread()).subscribe(new BaseObserver<List<OrderItem>>() {
            @Override
            protected void onSuccess(BaseEntity<List<OrderItem>> t) throws Exception {
                if (t != null){
                    if (t.isSuccess()){
                        List<OrderItem> data = t.getData();
                        mBillFragment.setOrderItemData(data);
                        mBillFragment.searchRouteListSuccess(t.getMsg());
                    }else {
                        mBillFragment.searchRouteListFailed(t.getMsg());
                    }
                }
            }

            @Override
            protected void onFailure(String msg) throws Exception {
                mBillFragment.searchRouteListFailed(msg);
            }
        });
    }
}
