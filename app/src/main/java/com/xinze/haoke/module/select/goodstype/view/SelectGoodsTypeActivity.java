package com.xinze.haoke.module.select.goodstype.view;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.xinze.haoke.R;
import com.xinze.haoke.base.BaseActivity;
import com.xinze.haoke.module.goods.bean.Goods;
import com.xinze.haoke.module.select.goodstype.adapter.SelectGoodsTypeAdapter;
import com.xinze.haoke.module.select.goodstype.presenter.SelectGoodsTypePresenterImp;
import com.xinze.haoke.widget.SimpleToolbar;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;

/**
 * 选择货品
 *
 * @author lxf
 */
public class SelectGoodsTypeActivity extends BaseActivity implements ISelectGoodsTypeView{
    @BindView(R.id.select_goods_type_toolbar)
    SimpleToolbar mToolbar;
    @BindView(R.id.goods_type_name)
    EditText goodsTypeName;
    @BindView(R.id.select_driver_rv)
    RecyclerView mRecyclerView;


    private GridLayoutManager glm;
    private SelectGoodsTypeAdapter mAdapter;
    private List<Goods> mData;

    @Override
    protected int initLayout() {
        return R.layout.select_goods_type_activity;
    }

    @Override
    protected void initView() {
        initTitleBar();
        // 初始化RecyclerView
        initRecyclerView();
    }

    @Override
    protected void initData() {
        SelectGoodsTypePresenterImp mPresenter = new SelectGoodsTypePresenterImp(this,this);
        mPresenter.getUsualGoodsType(10,1);
    }

    private void initTitleBar() {
        mToolbar.setLeftTitleVisible();
        mToolbar.setMainTitle(R.string.select_goods);
        mToolbar.setRightTitleText(R.string.confirm);
        mToolbar.setRightTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goods goods =new Goods();
                goods.setCargoName(goodsTypeName.getText().toString());
                EventBus.getDefault().post(goods);
                finish();
            }
        });
    }

    private void initRecyclerView() {
        glm = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(glm);
        mAdapter = new SelectGoodsTypeAdapter(this);
        mAdapter.setOnItemViewClick(new SelectGoodsTypeAdapter.OnItemViewClick() {
            @Override
            public void itemClick(int position) {
                mAdapter.selectOne(position);
                Goods goods = mData.get(position);
                EventBus.getDefault().post(goods);
                finish();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void getUsualGoodsTypeSuccess(String msg) {

    }

    @Override
    public void getUsualGoodsTypeFailed(String msg) {
        shotToast(msg);
    }

    public void setData(List<Goods> data) {
        if (data != null){
            mData = data;
            mAdapter.setData(data);
        }
    }
}
