package com.xinze.haoke.module.select.carType.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xinze.haoke.R;
import com.xinze.haoke.base.BaseActivity;
import com.xinze.haoke.module.select.carType.adapter.CarTypeRecycleViewAdapter;
import com.xinze.haoke.module.select.carType.modle.CarType;
import com.xinze.haoke.module.select.carType.presenter.SelectCarTypePresenterImp;
import com.xinze.haoke.widget.SimpleToolbar;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 选择车型
 *
 * @author lxf
 */
public class SelectCarTypeActivity extends BaseActivity implements ISelectCarTypeView {


    @BindView(R.id.car_type_toolbar)
    SimpleToolbar carTypeToolbar;
    @BindView(R.id.car_type_search)
    TextView carTypeSearch;
    @BindView(R.id.car_type_search_bar)
    RelativeLayout carTypeSearchBar;
    @BindView(R.id.car_type_rv)
    RecyclerView carTypeRv;
    private List<CarType> data = new ArrayList<>();
    private SelectCarTypePresenterImp mPresenter;
    private LinearLayoutManager llm;
    private CarTypeRecycleViewAdapter mAdapter;
    private String truckCode;


    @Override
    protected int initLayout() {
        return R.layout.select_car_type_activity;
    }

    @Override
    protected void initView() {
        initTitleBar();

        llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        carTypeRv.setLayoutManager(llm);
        mAdapter = new CarTypeRecycleViewAdapter(this);
        mAdapter.setOnItemViewClick(new CarTypeRecycleViewAdapter.OnItemViewClick() {
            @Override
            public void itemClick(int position) {
                if (data != null && data.size() > 0){
                    mAdapter.selected(position);
                }
            }
        });
        carTypeRv.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        mPresenter = new SelectCarTypePresenterImp(this, this);
        mPresenter.getCarType();
    }

    private void initTitleBar() {
        carTypeToolbar.setMainTitle("选择车型");
        carTypeToolbar.setLeftTitleVisible();
        carTypeToolbar.setLeftClickListener(new SimpleToolbar.TitleOnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        carTypeToolbar.setRightTitleText("确定");
        carTypeToolbar.setRightTitleClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                for (CarType carType : data) {
                    if (carType.isSelected()) {
                        truckCode = carType.getTruckcode();
                        EventBus.getDefault().post(carType);
                    }
                }
                finish();
            }
        });
    }

    @OnClick(R.id.car_type_search_bar)
    public void onClick() {

    }

    @Override
    public void getCarTypeSuccess(String msg) {

    }

    @Override
    public void getCarTypeFailed(String msg) {
        shotToast(msg);
    }

    public void setData(List<CarType> data) {
        if (data != null) {
            if (data.size() > 0) {
                this.data = data;
                mAdapter.setData(data);
            }
        }
    }
}
