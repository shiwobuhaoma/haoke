package com.xinze.haoke.module.select.driver.view;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.vondear.rxtools.view.dialog.RxDialogSureCancel;
import com.xinze.haoke.R;
import com.xinze.haoke.base.BaseActivity;
import com.xinze.haoke.bean.SpaceItemDecoration;
import com.xinze.haoke.config.ProtocolConfig;
import com.xinze.haoke.module.about.view.AboutUsActivity;
import com.xinze.haoke.module.select.car.adapter.SelectDriverRecycleAdapter;
import com.xinze.haoke.module.select.driver.presenter.SelectDriverPresenterImp;
import com.xinze.haoke.module.invite.model.OwnerDriverVO;
import com.xinze.haoke.module.ordinary.modle.Bill;
import com.xinze.haoke.widget.SimpleToolbar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 选择司机
 *
 * @author lxf
 */
public class SelectDriverActivity extends BaseActivity implements ISelectDriverView {
    @BindView(R.id.select_driver_toolbar)
    SimpleToolbar mToolbar;
    @BindView(R.id.select_driver_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.ordinary_protocol_iv)
    ImageView ordinaryProtocolIv;
    @BindView(R.id.ordinary_protocol)
    TextView ordinaryProtocol;
    @BindView(R.id.ordinary_release)
    Button ordinaryRelease;

    private SelectDriverPresenterImp mPresenter;
    protected LinearLayoutManager llm;
    private SelectDriverRecycleAdapter mAdapter;
    private List<OwnerDriverVO> mData;
    private boolean isSelectedProtocol;
    private String mId;
    private Bill bill;
    private String ownerTruckCount;

    @Override
    protected int initLayout() {
        return R.layout.select_driver_activity;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        bill = (Bill) intent.getSerializableExtra("bill");
        initTitleBar();
        // 初始化RecyclerView
        initRecyclerView();
    }

    @Override
    protected void initData() {
        mPresenter = new SelectDriverPresenterImp(this, this);
        mPresenter.getAlwaysDriver();
    }

    private void initTitleBar() {
        mToolbar.setLeftTitleVisible();
        mToolbar.setMainTitle(R.string.select_always_driver);
    }

    private void initRecyclerView() {
        llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(llm);
        mAdapter = new SelectDriverRecycleAdapter(this);
        mAdapter.setOnItemViewClick(new SelectDriverRecycleAdapter.OnItemViewClick() {
            @Override
            public void itemClick(int position) {
                OwnerDriverVO ownerDriverVO = mData.get(position);
                ownerTruckCount = ownerDriverVO.getOwnerTruckCount();
                mId = ownerDriverVO.getId();
                bill.setDriverId(mId);
                mAdapter.selectOne(position);
            }
        });
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(30));
        mRecyclerView.setAdapter(mAdapter);
    }

    @OnClick({R.id.ordinary_protocol_iv, R.id.ordinary_protocol, R.id.ordinary_release})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ordinary_protocol_iv:
                isSelectedProtocol = !isSelectedProtocol;
                if (isSelectedProtocol) {
                    ordinaryProtocolIv.setBackgroundResource(R.mipmap.select_choicd);
                } else {
                    ordinaryProtocolIv.setBackgroundResource(R.mipmap.select_choice);
                }
                break;
            case R.id.ordinary_protocol:
                openActivity(AboutUsActivity.class,"type", ProtocolConfig.TRANSPORT_SERVICE_PROTOCOL);
                break;
            case R.id.ordinary_release:
                if ("0".equals(ownerTruckCount)){
                    RxDialogSureCancel rxDialogSureCancel = new RxDialogSureCancel(SelectDriverActivity.this);
                    rxDialogSureCancel.getTitleView().setText("该司机没有车辆，是否派单？");
                    rxDialogSureCancel.setCancelListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            rxDialogSureCancel.cancel();
                        }
                    });
                    rxDialogSureCancel.setSureListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            rxDialogSureCancel.cancel();
                            release();
                        }
                    });
                    rxDialogSureCancel.show();
                }else{
                    release();
                }

                break;
            default:
                break;
        }
    }

    private void release() {
        mPresenter.release(bill);
    }

    @Override
    public void releaseTheBillOfGoodsSuccess(String msg) {
        shotToast(msg);
    }

    @Override
    public void releaseTheBillOfGoodsFailed(String msg) {
        shotToast(msg);
    }

    @Override
    public void getAlwaysDriverSuccess(String msg) {

    }

    @Override
    public void getAlwaysDriverFailed(String msg) {
        shotToast(msg);
    }

    public void setData(List<OwnerDriverVO> data) {
        if (data != null) {
            this.mData = data;
            mAdapter.setData(data);
        }
    }
}
