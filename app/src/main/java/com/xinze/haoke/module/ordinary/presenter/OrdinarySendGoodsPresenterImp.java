package com.xinze.haoke.module.ordinary.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.xinze.haoke.http.RetrofitFactory;
import com.xinze.haoke.http.config.HeaderConfig;
import com.xinze.haoke.http.entity.BaseEntity;
import com.xinze.haoke.http.observer.BaseObserver;
import com.xinze.haoke.module.ordinary.modle.Bill;
import com.xinze.haoke.module.ordinary.view.IOrdinarySendGoodsView;
import com.xinze.haoke.module.ordinary.view.OrdinarySendGoodsActivity;
import com.xinze.haoke.mvpbase.BasePresenterImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.qqtheme.framework.entity.Province;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class OrdinarySendGoodsPresenterImp extends BasePresenterImpl<IOrdinarySendGoodsView> implements IOrdinarySendGoodsPresenter {
    private OrdinarySendGoodsActivity mActivity;
    public OrdinarySendGoodsPresenterImp(IOrdinarySendGoodsView mPresenterView, Context mContext) {
        super(mPresenterView, mContext);
        mActivity = (OrdinarySendGoodsActivity)mPresenterView;

    }


    @Override
    public void getAreaList(String extId) {
        HashMap<String, String> headers = HeaderConfig.getHeaders();
        RetrofitFactory.getInstence().Api().getAreaList(headers,extId)
                .compose(this.<BaseEntity<List<Province>>>setThread())
                .subscribe(new BaseObserver<List<Province>>(){

            @Override
            protected void onSuccess(BaseEntity<List<Province>> t) throws Exception {
                if (t != null){
                    if (t.isSuccess()){
                        ArrayList<Province> data = (ArrayList<Province>) t.getData();
                        if (data != null){
                            mActivity.setAreaList(data);
                        }
                        mActivity.getAreaListSuccess(t.getMsg());
                    }else {
                        mActivity.getAreaListFailed(t.getMsg());
                    }
                }

            }

            @Override
            protected void onFailure(String msg) throws Exception {

            }
        });
    }
    @SuppressWarnings("unchecked")
    @Override
    public void releaseTheBillOfGoods(Bill bill) {
        HashMap<String, String> headers = HeaderConfig.getHeaders();
        Gson gson = new Gson();
        String json = gson.toJson(bill);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),json);
        RetrofitFactory.getInstence().Api().releaseTheBillOfGoods(headers,requestBody)
                .compose(this.<BaseEntity>setThread())
                .subscribe(new BaseObserver(){

            @Override
            protected void onSuccess(BaseEntity t) throws Exception {
                if (t != null){
                    if (t.isSuccess()){
                        mActivity.releaseTheBillOfGoodsSuccess(t.getMsg());
                    }else {
                        mActivity.releaseTheBillOfGoodsFailed(t.getMsg());
                    }
                }
            }

            @Override
            protected void onFailure(String msg) throws Exception {
                mActivity.releaseTheBillOfGoodsFailed(msg);
            }
        });
    }
}
