package com.xinze.haoke.module.select.cartype.presenter;

import android.content.Context;

import com.xinze.haoke.http.RetrofitFactory;
import com.xinze.haoke.http.config.HeaderConfig;
import com.xinze.haoke.http.entity.BaseEntity;
import com.xinze.haoke.http.observer.BaseObserver;
import com.xinze.haoke.module.select.cartype.modle.CarType;
import com.xinze.haoke.module.select.cartype.view.ISelectCarTypeView;
import com.xinze.haoke.module.select.cartype.view.SelectCarTypeActivity;
import com.xinze.haoke.mvpbase.BasePresenterImpl;

import java.util.HashMap;
import java.util.List;
/**
 * @author lxf
 */
public class SelectCarTypePresenterImp extends BasePresenterImpl<ISelectCarTypeView> implements ISelectCarTypePresenter {


    private SelectCarTypeActivity activity;

    public SelectCarTypePresenterImp(ISelectCarTypeView mPresenterView, Context mContext) {
        super(mPresenterView, mContext);
        activity = (SelectCarTypeActivity) mPresenterView;
    }

    @Override
    public void getCarType() {

        HashMap<String, String> headers = HeaderConfig.getHeaders();
        RetrofitFactory.getInstence().Api().getCarType(headers)
                .compose(this.setThread())
                .subscribe(new BaseObserver<List<CarType>>(mContext) {
            @Override
            protected void onSuccess(BaseEntity<List<CarType>> t) throws Exception {
                if (t != null){
                    if (t.isSuccess()){
                        activity.setData(t.getData());
                    }else {
                        activity.getCarTypeFailed(t.getMsg());
                    }
                }
            }

            @Override
            protected void onFailure(String msg) throws Exception {
                activity.getCarTypeFailed(msg);
            }
        });
    }


}
