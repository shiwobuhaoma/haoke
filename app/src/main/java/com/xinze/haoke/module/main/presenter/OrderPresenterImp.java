package com.xinze.haoke.module.main.presenter;

import android.content.Context;

import com.xinze.haoke.App;
import com.xinze.haoke.config.AppConfig;
import com.xinze.haoke.http.RetrofitFactory;
import com.xinze.haoke.http.config.HeaderConfig;
import com.xinze.haoke.http.entity.BaseEntity;
import com.xinze.haoke.http.observer.BaseObserver;
import com.xinze.haoke.module.main.fragment.OrderFragment;
import com.xinze.haoke.module.main.modle.OrderItem;
import com.xinze.haoke.module.main.view.IOrderView;
import com.xinze.haoke.mvpbase.BasePresenterImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 中介
 * @author lxf
 */
public class OrderPresenterImp extends BasePresenterImpl<IOrderView> implements IOrderPresenter {

    private int pageNo;
    private List<OrderItem> mData;

    public OrderPresenterImp(IOrderView mPresenterView, Context mContext) {
        super(mPresenterView, mContext);
    }

    @Override
    public void getOderList(int pageNo, int pageSize,String remark) {
        this.pageNo = pageNo;
        HashMap<String, String> headers = HeaderConfig.getHeaders();
        RetrofitFactory.getInstence().Api().getBillOrderList(headers,pageNo,pageSize,remark).compose(this.<BaseEntity<List<OrderItem>>>setThread()).subscribe(new BaseObserver<List<OrderItem>>() {
            @Override
            protected void onSuccess(BaseEntity<List<OrderItem>> t) throws Exception {
                if (t != null){
                    if (t.isSuccess()){
                        List<OrderItem> data = t.getData();
                        setData(data);
                        mPresenterView.getOrderListSuccess();
                    }else{
                        mPresenterView.getOrderListFailed();
                        mPresenterView.shotToast(t.getMsg());

                    }
                }
            }

            @Override
            protected void onFailure(String msg) throws Exception {
                mPresenterView.getOrderListFailed();
            }
        });
    }

    public void setData(final List<OrderItem> data) {
        if (pageNo == 1) {
            if (data != null && data.size() > 0) {
                this.mData = data;
                mPresenterView.upData(data);
            }else{
                mPresenterView.clearData();
            }

        } else {
            if (data != null && data.size() > 0) {
                this.mData.addAll(data);
                mPresenterView.upData(mData);
            }else{
                mPresenterView.shotToast(AppConfig.LOAD_INFO_FINISH);
            }

        }

    }
}
