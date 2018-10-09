package com.xinze.haoke.module.select.driver.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.xinze.haoke.http.RetrofitFactory;
import com.xinze.haoke.http.config.HeaderConfig;
import com.xinze.haoke.http.entity.BaseEntity;
import com.xinze.haoke.http.observer.BaseObserver;
import com.xinze.haoke.module.select.driver.view.ISelectDriverView;
import com.xinze.haoke.module.select.driver.view.SelectDriverActivity;
import com.xinze.haoke.module.invite.model.OwnerDriverVO;
import com.xinze.haoke.module.ordinary.modle.Bill;
import com.xinze.haoke.mvpbase.BasePresenterImpl;

import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;


public class SelectDriverPresenterImp extends BasePresenterImpl<ISelectDriverView> implements ISelectDriverPresenter {

    private SelectDriverActivity mActivity;
    public SelectDriverPresenterImp(ISelectDriverView mPresenterView, Context mContext) {
        super(mPresenterView, mContext);
        mActivity = (SelectDriverActivity) mPresenterView;
    }
    @SuppressWarnings("unchecked")
    @Override
    public void release(Bill bill) {
        HashMap<String, String> headers = HeaderConfig.getHeaders();
        Gson gson = new Gson();
        String json = gson.toJson(bill);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),json);
        RetrofitFactory.getInstence().Api().releaseTheBillOfGoods(headers,requestBody)
                .compose(this.<BaseEntity>setThread())
                .subscribe(new BaseObserver(mActivity){

                    @Override
                    protected void onSuccees(BaseEntity t) throws Exception {
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

    @Override
    public void getAlwaysDriver() {
        HashMap<String, String> headers = HeaderConfig.getHeaders();
        RetrofitFactory.getInstence().Api().myCooperatedDrivers(headers)
                .compose(this.setThread())
                .subscribe(new BaseObserver<List<OwnerDriverVO>>(mContext) {
                    @Override
                    protected void onSuccees(BaseEntity<List<OwnerDriverVO>> t) throws Exception {
                        if (t != null){
                            if (t.isSuccess()){
                                mActivity.setData(t.getData());
                            }else {
                                mActivity.getAlwaysDriverFailed(t.getMsg());
                            }
                        }
                    }

                    @Override
                    protected void onFailure(String msg) throws Exception {
                        mActivity.getAlwaysDriverFailed(msg);
                    }
                });
    }
}
