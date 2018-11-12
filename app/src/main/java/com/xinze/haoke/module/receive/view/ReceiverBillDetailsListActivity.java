package com.xinze.haoke.module.receive.view;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.xinze.haoke.R;
import com.xinze.haoke.base.BaseActivity;
import com.xinze.haoke.config.AppConfig;
import com.xinze.haoke.module.order.view.OrderDetailActivity;
import com.xinze.haoke.module.receive.modle.ReceiverBill;
import com.xinze.haoke.module.receive.adapter.ReceiverBillAdapter;
import com.xinze.haoke.module.receive.presenter.ReceiverBillDetailsListListPresenterImp;
import com.xinze.haoke.utils.MessageEvent;
import com.xinze.haoke.widget.SimpleToolbar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;

/**
 * @author lxf
 * 接单详情
 */
public class ReceiverBillDetailsListActivity extends BaseActivity implements IReceiverBillDetailsListView {
    @BindView(R.id.receive_bill_details_tool_bar)
    SimpleToolbar mToolbar;
    @BindView(R.id.send_number)
    TextView sendNumber;
    @BindView(R.id.driver_receive_bill_list)
    TextView driverReceiveBillList;
    @BindView(R.id.receive_bill_list)
    RecyclerView mRecyclerView;


    private int pageNo = AppConfig.PAGE_NO;
    private int pageSize = AppConfig.PAGE_SIZE;
    private String wayBillId;
    private ReceiverBillAdapter adapter;
    private List<ReceiverBill.WaybillOrderEntitiesBean> data;
    private ReceiverBillDetailsListListPresenterImp mPresenter;


    @Override
    protected int initLayout() {
        return R.layout.receiver_bill_details_activity;
    }

    @Override
    protected void initView() {
        wayBillId = getIntent().getStringExtra("wayBillId");
        String billNumber = getResources().getString(R.string.bill_number);
        String billId = String.format(billNumber, wayBillId);
        sendNumber.setText(billId);
        initToolBar();
        initRecycleView();

    }

    private void initToolBar() {
        mToolbar.setLeftTitleVisible();
        mToolbar.setLeftTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mToolbar.setMainTitle("接单详情");
    }

    private void initRecycleView() {
        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(llm);
        adapter = new ReceiverBillAdapter(this);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new ReceiverBillAdapter.ItemOnClickListener() {
            @Override
            public void itemOnClick(int position) {
                if (data != null){
                    ReceiverBill.WaybillOrderEntitiesBean waybillOrderEntitiesBean = data.get(position);
                    Intent intent = new Intent(ReceiverBillDetailsListActivity.this, OrderDetailActivity.class);
                    intent.putExtra("remarks",waybillOrderEntitiesBean.getRemarks());
                    intent.putExtra("orderId",waybillOrderEntitiesBean.getIdX());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void initData() {
        if (mPresenter == null){
            mPresenter = new ReceiverBillDetailsListListPresenterImp(this, this);
        }
        mPresenter.getBillOrderListForOwner(pageNo, pageSize, wayBillId);
    }

    @Override
    public void getBillOrderListForOwnerSuccess(String msg) {

    }

    @Override
    public void getBillOrderListForOwnerFailed(String msg) {
        shotToast(msg);
    }

    public void setData(List<ReceiverBill.WaybillOrderEntitiesBean> data) {
        if (data != null) {
            this.data = data;
            adapter.setData(data);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void clear(MessageEvent messageEvent) {
        if (AppConfig.UPDATE_ORDER.equals(messageEvent.getMessage())){
            initData();
        }

    }
}
