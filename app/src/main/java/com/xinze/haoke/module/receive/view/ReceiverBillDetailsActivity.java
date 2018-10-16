package com.xinze.haoke.module.receive.view;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.xinze.haoke.R;
import com.xinze.haoke.base.BaseActivity;
import com.xinze.haoke.config.AppConfig;
import com.xinze.haoke.module.receive.ReceiverBill;
import com.xinze.haoke.module.receive.presenter.ReceiverBillDetailsPresenterImp;

import java.util.List;

import butterknife.BindView;

/**
 * @author lxf
 * 接单详情
 */
public class ReceiverBillDetailsActivity extends BaseActivity implements IReceiverBillDetailsView{
    @BindView(R.id.send_number)
    TextView sendNumber;
    @BindView(R.id.driver_receive_bill_list)
    TextView driverReceiveBillList;
    @BindView(R.id.receive_bill_list)
    RecyclerView receiveBillList;



    private int pageNo = AppConfig.PAGE_NO;
    private int pageSize = AppConfig.PAGE_SIZE;
    private String wayBillId;
    private ReceiverBillDetailsPresenterImp mPresenter;

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

    }

    @Override
    protected void initData() {
        mPresenter = new ReceiverBillDetailsPresenterImp(this,this);
        mPresenter.getBillOrderListForOwner(pageNo,pageSize,wayBillId);
    }

    @Override
    public void getBillOrderListForOwnerSuccess(String msg) {

    }

    @Override
    public void getBillOrderListForOwnerFailed(String msg) {

    }

    public void setData(List<ReceiverBill> data) {
        if (data != null){

        }
    }
}
