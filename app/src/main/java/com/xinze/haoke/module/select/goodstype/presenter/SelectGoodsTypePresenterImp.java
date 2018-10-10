package com.xinze.haoke.module.select.goodstype.presenter;

import android.content.Context;

import com.xinze.haoke.http.RetrofitFactory;
import com.xinze.haoke.http.config.HeaderConfig;
import com.xinze.haoke.http.entity.BaseEntity;
import com.xinze.haoke.http.observer.BaseObserver;
import com.xinze.haoke.module.goods.bean.Goods;
import com.xinze.haoke.module.select.goodstype.view.ISelectGoodsTypeView;
import com.xinze.haoke.module.select.goodstype.view.SelectGoodsTypeActivity;
import com.xinze.haoke.mvpbase.BasePresenterImpl;

import java.util.HashMap;
import java.util.List;

/**
 * @author lxf
 */
public class SelectGoodsTypePresenterImp extends BasePresenterImpl<ISelectGoodsTypeView> implements ISelectGoodsTypePresenter {
    private SelectGoodsTypeActivity mActivity;

    public SelectGoodsTypePresenterImp(ISelectGoodsTypeView mPresenterView, Context mContext) {
        super(mPresenterView, mContext);
        this.mActivity = (SelectGoodsTypeActivity) mPresenterView;
    }

    @Override
    public void getUsualGoodsType(int pageSize,int pageNum) {
        HashMap<String, String> headers = HeaderConfig.getHeaders();
        RetrofitFactory.getInstence().Api().getMyUsualCargo(headers,pageSize,pageNum)
                .compose(this.<BaseEntity<List<Goods>>>setThread())
                .subscribe(new BaseObserver<List<Goods>>(mContext) {
                    @Override
                    protected void onSuccess(BaseEntity<List<Goods>> t) throws Exception {
                        if (t != null){
                            if (t.isSuccess()){
                                mActivity.setData(t.getData());
                            }else {
                                mActivity.getUsualGoodsTypeFailed(t.getMsg());
                            }
                        }
                    }

                    @Override
                    protected void onFailure(String msg) throws Exception {
                        mActivity.getUsualGoodsTypeFailed(msg);
                    }
                });
    }
}
