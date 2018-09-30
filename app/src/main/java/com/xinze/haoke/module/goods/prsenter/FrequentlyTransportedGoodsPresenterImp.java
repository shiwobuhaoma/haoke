package com.xinze.haoke.module.goods.prsenter;

import android.content.Context;

import com.xinze.haoke.http.RetrofitFactory;
import com.xinze.haoke.http.config.HeaderConfig;
import com.xinze.haoke.http.entity.BaseEntity;
import com.xinze.haoke.http.observer.BaseObserver;
import com.xinze.haoke.module.goods.bean.Goods;
import com.xinze.haoke.module.goods.view.FrequentlyTransportedGoodsActivity;
import com.xinze.haoke.module.goods.view.IFrequentlyTransportedGoodsView;
import com.xinze.haoke.mvpbase.BasePresenterImpl;

import java.util.HashMap;
import java.util.List;

public class FrequentlyTransportedGoodsPresenterImp extends BasePresenterImpl<IFrequentlyTransportedGoodsView> implements IFrequentlyTransportedGoodsPresenter {
    private FrequentlyTransportedGoodsActivity mFrequentlyTransportedGoodsActivity;

    public FrequentlyTransportedGoodsPresenterImp(IFrequentlyTransportedGoodsView mPresenterView, Context mContext) {
        super(mPresenterView, mContext);
        mFrequentlyTransportedGoodsActivity = (FrequentlyTransportedGoodsActivity) mPresenterView;
    }


    @Override
    public void getMyUsualCargo(int pageSize, int pageNum) {
        HashMap<String, String> headers = HeaderConfig.getHeaders();
        RetrofitFactory.getInstence().Api().getMyUsualCargo(headers,pageSize,pageNum).compose(this.<BaseEntity<List<Goods>>>setThread()).subscribe(new BaseObserver<List<Goods>>(mContext) {
            @Override
            protected void onSuccees(BaseEntity<List<Goods>> t) throws Exception {
                if (t != null){
                    if (t.isSuccess()){
                        mFrequentlyTransportedGoodsActivity.setData(t.getData());
                    }else {
                        mFrequentlyTransportedGoodsActivity.myUsualCargoFailed(t.getMsg());
                    }
                }
            }

            @Override
            protected void onFailure(String msg) throws Exception {
                mFrequentlyTransportedGoodsActivity.myUsualCargoFailed(msg);
            }
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public void getMyUsualCargoAdd(String str) {
        HashMap<String, String> headers = HeaderConfig.getHeaders();

        RetrofitFactory.getInstence().Api().getMyUsualCargoAdd(headers,str).compose(this.<BaseEntity>setThread()).subscribe(new BaseObserver(mContext) {
            @Override
            protected void onSuccees(BaseEntity t) throws Exception {
                if (t != null){
                    if (t.isSuccess()){
                        mFrequentlyTransportedGoodsActivity.myUsualCargoAddSuccess(t.getMsg());
                    }else {
                        mFrequentlyTransportedGoodsActivity.myUsualCargoAddFailed(t.getMsg());
                    }
                }
            }

            @Override
            protected void onFailure(String msg) throws Exception {
                mFrequentlyTransportedGoodsActivity.myUsualCargoAddFailed(msg);
            }
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public void getMyUsualCargoDel(String str) {
        HashMap<String, String> headers = HeaderConfig.getHeaders();

        RetrofitFactory.getInstence().Api().getMyUsualCargoDel(headers,str).compose(this.<BaseEntity>setThread()).subscribe(new BaseObserver(mContext) {
            @Override
            protected void onSuccees(BaseEntity t) throws Exception {
                if (t != null){
                    if (t.isSuccess()){
                        mFrequentlyTransportedGoodsActivity.myUsualCargoDelSuccess(t.getMsg());
                    }else {
                        mFrequentlyTransportedGoodsActivity.myUsualCargoDelFailed(t.getMsg());
                    }
                }
            }

            @Override
            protected void onFailure(String msg) throws Exception {
                mFrequentlyTransportedGoodsActivity.myUsualCargoAddFailed(msg);
            }
        });
    }
}
