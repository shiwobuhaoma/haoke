
package com.xinze.haoke.module.regular.presenter;

import android.content.Context;

import com.xinze.haoke.http.RetrofitFactory;
import com.xinze.haoke.http.config.HeaderConfig;
import com.xinze.haoke.http.entity.BaseEntity;
import com.xinze.haoke.http.observer.BaseObserver;
import com.xinze.haoke.module.main.modle.OrderItem;
import com.xinze.haoke.module.regular.view.RegularRunActivity;
import com.xinze.haoke.module.regular.modle.Route;
import com.xinze.haoke.module.regular.view.IRegularRouteView;
import com.xinze.haoke.mvpbase.BasePresenterImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegularRunPresenterImp extends BasePresenterImpl<IRegularRouteView> implements IRegularRunPresenter {
    private RegularRunActivity mRegularRunActivity;
    public RegularRunPresenterImp(IRegularRouteView mPresenterView, Context mContext) {
        super(mPresenterView, mContext);
        mRegularRunActivity = (RegularRunActivity)mPresenterView;
    }

    @Override
    public void getRegularRouteList() {
        HashMap<String, String> headers = HeaderConfig.getHeaders();
        RetrofitFactory.getInstence().Api().getRegularRouteList(headers).compose(this.<BaseEntity<List<Route>>>setThread()).subscribe(new BaseObserver<List<Route>>() {
            @Override
            protected void onSuccees(BaseEntity<List<Route>> t) throws Exception {
                if (t != null){
                    if (t.isSuccess()){
                        List<Route> data = t.getData();
                        mRegularRunActivity.setRouteData(data);
                    }else {
                        mRegularRunActivity.getRegularRouteListFailed(t.getMsg());
                    }
                }

            }

            @Override
            protected void onFailure(String msg) throws Exception {
                mRegularRunActivity.getRegularRouteListFailed(msg);
            }
        });
    }

    @Override
    public void searchRouteList(String fromAreaId,String toAreaId,int pageNo,int pageSize) {
        HashMap<String, String> headers = HeaderConfig.getHeaders();
        RetrofitFactory.getInstence().Api().searchRoute(headers,fromAreaId,toAreaId,pageNo,pageSize).compose(this.<BaseEntity<List<OrderItem>>>setThread()).subscribe(new BaseObserver<List<OrderItem>>() {
            @Override
            protected void onSuccees(BaseEntity<List<OrderItem>> t) throws Exception {
                if (t != null){
                    if (t.isSuccess()){
                        List<OrderItem> data = t.getData();
                        mRegularRunActivity.setOrderItemData(data);
//                        mRegularRunActivity.searchRouteListSuccess(t.getMsg());
                    }else {
                        mRegularRunActivity.searchRouteListFailed(t.getMsg());
                    }
                }
            }

            @Override
            protected void onFailure(String msg) throws Exception {
                mRegularRunActivity.searchRouteListFailed(msg);
            }
        });
    }

}
