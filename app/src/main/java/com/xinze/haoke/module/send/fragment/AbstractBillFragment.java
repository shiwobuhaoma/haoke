package com.xinze.haoke.module.send.fragment;

import android.support.v7.widget.LinearLayoutManager;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xinze.haoke.base.BaseFragment;
import com.xinze.haoke.module.ordinary.modle.Bill;
import com.xinze.haoke.module.select.car.view.SelectCarActivity;
import com.xinze.haoke.module.send.adapter.BillRecycleViewAdapter;
import com.xinze.haoke.module.send.presenter.BillPresenterImp;
import com.xinze.haoke.module.send.view.IBillView;
import com.xinze.haoke.module.transport.view.TransportDetailsActivity;

import java.util.HashMap;
import java.util.List;

/**
 * @author lxf
 * 抽象的父类
 */
public abstract class AbstractBillFragment extends BaseFragment implements IBillView {
    protected SmartRefreshLayout layout;
    protected int pageNo = 1;
    protected int pageSize = 10;
    protected List<Bill> data;
    protected BillRecycleViewAdapter billRecycleViewAdapter;
    protected BillPresenterImp bpi;
    protected LinearLayoutManager llm;
    protected int mPosition = 0;
    protected String remarks;

    @Override
    protected void initData() {
        super.initData();
        initData(getBillType());
    }

    /**
     * 获取订单类型
     * @return 返回订单类型
     */
    protected abstract int getBillType();


    protected void initData(int billType) {
        super.initData();
        bpi = new BillPresenterImp(this, mActivity);
        bpi.getBillList(billType, pageNo, pageSize,remarks);
    }
    public void setData(final List<Bill> data) {
        if (data != null && data.size() > 0) {
            if (pageNo == 1) {
                this.data = data;
            } else {
                this.data.addAll(data);
            }
            billRecycleViewAdapter.clearData();
            billRecycleViewAdapter.setData(this.data);
        }
    }

    @Override
    public void getBillsSuccess(String msg) {
        if (pageNo == 1) {
            layout.finishRefresh(2000);
        } else {
            layout.finishLoadMore(2000);
        }
    }

    @Override
    public void getBillsFailed(String msg) {
        layout.finishRefresh(false);
    }

    protected void jumpToOrderDetailActivity(int position,String from) {
        mPosition = position;
        Bill orderItem = data.get(position);
        String orderId = orderItem.getId();
        HashMap<String,String> map = new HashMap<>(2);
        map.put("orderId", orderId);
        map.put("from", from);
        openActivity(TransportDetailsActivity.class,map);

    }
    protected void jumpToSelectActivity(int position,String from) {
        Bill orderItem = data.get(position);
        String orderId = orderItem.getId();
        HashMap<String,String> map = new HashMap<>(2);
        map.put("orderId", orderId);
        map.put("from", from);
        openActivity(SelectCarActivity.class,map);

    }
    public void setBillData(List<Bill> data) {
        billRecycleViewAdapter.setData(data);
    }

    @Override
    public void searchRouteListSuccess(String msg) {

    }

    @Override
    public void searchRouteListFailed(String msg) {

    }
}
