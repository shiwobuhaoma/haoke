package com.xinze.haoke.module.select.goodsType.view;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.xinze.haoke.R;
import com.xinze.haoke.base.BaseActivity;
import com.xinze.haoke.bean.SpaceItemDecoration;
import com.xinze.haoke.widget.SimpleToolbar;

import butterknife.BindView;

/**
 * 选择货品
 *
 * @author lxf
 */
public class SelectGoodsTypeActivity extends BaseActivity {
    @BindView(R.id.select_goods_type_toolbar)
    SimpleToolbar mToolbar;
    @BindView(R.id.goods_type_name)
    EditText goodsTypeName;
    @BindView(R.id.select_driver_rv)
    RecyclerView mRecyclerView;


    private GridLayoutManager glm;

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


    private void initTitleBar() {
        mToolbar.setLeftTitleVisible();
        mToolbar.setMainTitle(R.string.select_goods);
        mToolbar.setRightTitleText(R.string.confirm);
        mToolbar.setRightTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initRecyclerView() {
        glm = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(glm);
//        mAdapter = new SelectDriverRecycleAdapter(this);
//        mAdapter.setOnItemViewClick(new SelectDriverRecycleAdapter.OnItemViewClick() {
//            @Override
//            public void itemClick(int position) {
//                mAdapter.selectOne(position);
//            }
//        });
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(30));
//        mRecyclerView.setAdapter(mAdapter);
    }
}
