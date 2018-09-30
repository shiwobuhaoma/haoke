package com.xinze.haoke.module.invite.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xinze.haoke.R;
import com.xinze.haoke.base.BaseActivity;
import com.xinze.haoke.bean.SpaceItemDecoration;
import com.xinze.haoke.config.AppConfig;
import com.xinze.haoke.module.invite.adapter.MyInviteRecycleViewAdapter;
import com.xinze.haoke.module.invite.model.OwnerDriverVO;
import com.xinze.haoke.module.invite.presenter.MyInvitePresenterImp;
import com.xinze.haoke.widget.SimpleToolbar;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的邀请
 * @author lxf
 */
public class MyInviteActivity extends BaseActivity implements IMyInviteView{
    @BindView(R.id.my_invite_tool_bar)
    SimpleToolbar mToolbar;
    @BindView(R.id.my_invite_driver)
    Button myInviteDriver;
    @BindView(R.id.my_invite_srl_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.my_invite_srl)
    SmartRefreshLayout mSmartRefreshLayout;

    protected SmartRefreshLayout layout;
    protected LinearLayoutManager llm;

    private int pageNo = AppConfig.PAGE_NO;
    private int pageSize = AppConfig.PAGE_SIZE;
    private MyInviteRecycleViewAdapter mAdapter;
    private MyInvitePresenterImp mPresenter;

    @Override
    protected int initLayout() {
        return R.layout.my_invite_activity;
    }

    @Override
    protected void initView() {
        // 初始化标题栏
        initToolbar();
        // 初始化RecyclerView
        initRecyclerView();
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter = new MyInvitePresenterImp(this, this);
        mPresenter.myCooperatedDrivers(pageNo,pageSize,"");
    }

    private void initRecyclerView() {
        llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(llm);
        mAdapter = new MyInviteRecycleViewAdapter(this);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(30));
        mRecyclerView.setAdapter(mAdapter);
        // 绑定下拉刷新事件
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
//                mPresenter.getMyUsualCargo(pageNo, pageSize);
            }
        });
        // 绑定上拉刷新加载更多事件
        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (!pageEndFlag) {
                    pageNo++;
//                    mPresenter.getMyUsualCargo(pageNo, pageSize);
                } else {
                    layout.finishLoadMore(500);
                    MyInviteActivity.this.shotToast(AppConfig.LOAD_INFO_FINISH);
                }
            }
        });
        layout = mSmartRefreshLayout.getLayout();
    }

    private void initToolbar() {
        mToolbar.setTitleMarginTop();
        mToolbar.setMainTitle(getString(R.string.my_invite));
        mToolbar.setLeftTitleVisible();
        mToolbar.setLeftClickListener(new SimpleToolbar.TitleOnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick(R.id.my_invite_driver)
    public void onClick() {
        openActivity(InviteDriverActivity.class);
    }


    @Override
    public void myCooperatedDriversSuccess(String msg) {

    }

    @Override
    public void myCooperatedDriversFailed(String msg) {

    }

    public void setData(List<OwnerDriverVO> data) {

    }
}
