package com.xinze.haoke.module.relay.view;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xinze.haoke.R;
import com.xinze.haoke.base.BaseActivity;
import com.xinze.haoke.module.ordinary.modle.Bill;
import com.xinze.haoke.module.ordinary.view.OrdinarySendGoodsActivity;
import com.xinze.haoke.module.relay.presenter.RelayPresenterImp;
import com.xinze.haoke.widget.SimpleToolbar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author lxf
 * 一键转发
 */
public class RelayActivity extends BaseActivity implements IRelayView {
    @BindView(R.id.relay_tool_bar)
    SimpleToolbar relayToolBar;
    @BindView(R.id.relay_des)
    TextView relayDes;
    @BindView(R.id.relay_ordinary)
    Button relayOrdinary;
    @BindView(R.id.relay_dx)
    Button relayDx;
    private Bill bill;

    @Override
    protected int initLayout() {
        return R.layout.relay_activity;
    }

    @Override
    protected void initView() {
        initToolBar();
    }

    private void initToolBar() {
        relayToolBar.setLeftTitleVisible();
        relayToolBar.setLeftTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        relayToolBar.setMainTitle("一键转发");
    }

    @Override
    protected void initData() {
        bill = (Bill) getIntent().getSerializableExtra("bill");
        RelayPresenterImp  mPresenter = new RelayPresenterImp(this, this);
        if (bill != null) {
            mPresenter.getBillInfoForTrans(bill.getId());
        }
        mPresenter.getDriverCounts();
    }

    @OnClick({R.id.relay_ordinary, R.id.relay_dx})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.relay_ordinary:
                openActivity(OrdinarySendGoodsActivity.class, "bill", bill);
                break;
            case R.id.relay_dx:
                Intent intent = new Intent(RelayActivity.this, OrdinarySendGoodsActivity.class);
                intent.putExtra("bill", bill);
                intent.putExtra("from", "定向");
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void getBillInfoForTransSuccess(String msg) {

    }

    @Override
    public void getBillInfoForTransFailed(String msg) {
        shotToast(msg);
    }

    public void setData(Bill data) {
        this.bill = data;
    }

    public void setNumData(Object data) {
        String relayDes = getString(R.string.relay_des);
        String des = String.format(relayDes, (int) data);
        this.relayDes.setText(des);
    }
}
