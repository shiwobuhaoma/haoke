package com.xinze.haoke.module.send.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xinze.haoke.App;
import com.xinze.haoke.R;
import com.xinze.haoke.config.OrderConfig;
import com.xinze.haoke.module.ordinary.modle.Bill;
import com.xinze.haoke.module.send.adapter.BillRecycleViewAdapter;
import com.xinze.haoke.module.send.view.IBillView;
import com.xinze.haoke.utils.DialogUtil;
import com.xinze.haoke.widget.SelectAddressView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 普通货单
 *
 * @author lxf
 */
public class OrdinaryBillFragment extends AbstractBillFragment implements IBillView {
    @BindView(R.id.ordinary_bill_rv)
    RecyclerView ordinaryBillRv;
    @BindView(R.id.ordinary_bill_srl)
    SmartRefreshLayout ordinaryBillSrl;
    @BindView(R.id.send_goods_from)
    TextView sendGoodsFrom;
    @BindView(R.id.send_goods_to)
    TextView sendGoodsTo;
    @BindView(R.id.send_goods_select_from)
    SelectAddressView sendGoodsSelectFrom;

    private int mCurrentView;
    private String fromID;
    private String toID = "0";


    @Override
    protected int initLayout() {
        return R.layout.bill_ordinary_fragment;
    }


    @Override
    protected void initView() {
        llm = new LinearLayoutManager(mActivity);
        ordinaryBillRv.setLayoutManager(llm);
        billRecycleViewAdapter = new BillRecycleViewAdapter(mActivity, "OrdinaryBillFragment");
        ordinaryBillRv.setAdapter(billRecycleViewAdapter);
        layout = ordinaryBillSrl;
        ordinaryBillSrl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                bpi.getBillList(getBillType(), pageNo, pageSize, remarks);
            }
        });
        ordinaryBillSrl.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageNo++;
                bpi.getBillList(getBillType(), pageNo, pageSize, remarks);
            }
        });

        billRecycleViewAdapter.setOnItemClickListener(new BillRecycleViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void jumpSelectCar(int position) {
                if (App.mUser.isLogin()) {
                    if ("1".equals(App.mUser.getVertifyFlag())) {
                        jumpToSelectActivity(position, "OrdinaryBillFragment");
                    } else {
                        DialogUtil.showUnIdentificationDialog(mActivity);
                    }

                } else {
                    DialogUtil.showUnloginDialog(mActivity);
                }

            }

            @Override
            public void jumpDetails(int position) {
                jumpToOrderDetailActivity(position, "OrdinaryBillFragment");
            }
        });
        sendGoodsSelectFrom.setmOnSelectAddressListener(new SelectAddressView.OnSelectAddressListener() {
            @Override
            public void selectAddress(String name, String id) {

                switch (mCurrentView) {
                    case R.id.send_goods_from:

                        fromID = id;
                        sendGoodsFrom.setText(name);
                        bpi.searchRouteList(fromID, toID, pageNo, pageSize);
                        break;
                    case R.id.send_goods_to:

                        toID = id;
                        sendGoodsTo.setText(name);
                        bpi.searchRouteList(fromID, toID, pageNo, pageSize);
                        break;
                    default:
                        break;
                }
            }
        });
    }


    @OnClick({R.id.send_goods_from, R.id.send_goods_to})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_goods_from:
                mCurrentView = R.id.send_goods_from;
                sendGoodsSelectFrom.clearState();

                if (sendGoodsSelectFrom.getVisibility() == View.GONE) {
                    sendGoodsSelectFrom.setViewVisible();
                } else {
                    billRecycleViewAdapter.setData(data);
                    sendGoodsSelectFrom.setViewGone();
                }

                break;
            case R.id.send_goods_to:

                mCurrentView = R.id.send_goods_to;
                sendGoodsSelectFrom.clearState();
                if (sendGoodsSelectFrom.getVisibility() == View.GONE) {
                    sendGoodsSelectFrom.setViewVisible();
                } else {
                    sendGoodsSelectFrom.setViewGone();
                }

                break;
            default:
                break;
        }
    }

    @Override
    protected int getBillType() {
        //普通货单
        return OrderConfig.ORDINARYBILL;
    }

    @Override
    public void getBillsSuccess(String msg) {
        super.getBillsSuccess(msg);
        moveToPosition(llm, ordinaryBillRv, mPosition);
    }

    @Override
    public void searchRouteListSuccess(String msg) {
        sendGoodsSelectFrom.setViewGone();
        ordinaryBillSrl.setVisibility(View.VISIBLE);
    }

    @Override
    public void searchRouteListFailed(String msg) {
        sendGoodsSelectFrom.setViewGone();
        ordinaryBillSrl.setVisibility(View.VISIBLE);
    }

    @Override
    public void setBillData(List<Bill> data) {
        super.setBillData(data);
        billRecycleViewAdapter.setData(data);
    }
}
