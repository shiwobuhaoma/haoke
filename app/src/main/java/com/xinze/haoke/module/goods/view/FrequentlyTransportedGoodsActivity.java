package com.xinze.haoke.module.goods.view;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vondear.rxtools.view.dialog.RxDialogEditSureCancel;
import com.xinze.haoke.R;
import com.xinze.haoke.base.BaseActivity;
import com.xinze.haoke.bean.SpaceItemDecoration;
import com.xinze.haoke.config.AppConfig;
import com.xinze.haoke.module.goods.adapter.GoodsRecycleViewAdapter;
import com.xinze.haoke.module.goods.bean.Goods;
import com.xinze.haoke.module.goods.prsenter.FrequentlyTransportedGoodsPresenterImp;
import com.xinze.haoke.widget.SimpleToolbar;

import java.util.List;

import butterknife.BindView;

/**
 * @author 李晓飞
 * @date 2018/9/12
 * desc:长拉货品列表界面
 */
public class FrequentlyTransportedGoodsActivity extends BaseActivity implements IFrequentlyTransportedGoodsView, GoodsRecycleViewAdapter.OnItemViewClick {


    @BindView(R.id.tra_goods_toolbar)
    SimpleToolbar mToolbar;
    @BindView(R.id.add_goods)
    Button addGoods;
    @BindView(R.id.tra_goods_srl_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.tra_goods_srl)
    SmartRefreshLayout mSmartRefreshLayout;

    protected SmartRefreshLayout layout;

    private int pageNo = AppConfig.PAGE_NO;
    private int pageSize = AppConfig.PAGE_SIZE;
    private boolean pageEndFlag = false;

    protected List<Goods> data;
    protected GoodsRecycleViewAdapter mAdapter;
    protected FrequentlyTransportedGoodsPresenterImp mPresenter;
    protected LinearLayoutManager llm;
    protected int mPosition = 0;
    protected String inviteFlag = null;
    /**
     * onResume是否刷新标志
     */
    public static Boolean isRefresh = false;


    @Override
    protected int initLayout() {
        return R.layout.grequently_transported_goods_activity;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isRefresh) {
            mPresenter.getMyUsualCargo(pageSize, pageNo);
        }
        isRefresh = false;
    }

    @Override
    protected void initView() {
        // 初始化标题栏
        initToolbar();
        // 初始化RecyclerView
        initRecyclerView();
        // 添加货品btn点击事件
        addGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("删除".equals(addGoods.getText().toString())) {
                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < data.size(); i++) {
                        Goods good = data.get(i);
                        if (good.isTag()) {
                            String id = good.getId() + ",";
                            sb.append(id);
                            mAdapter.deleteItem(i);
                        }
                    }
                    String substring = sb.substring(0, sb.length());
                    mPresenter.getMyUsualCargoDel(substring);
                } else {
                    //弹出对话框
                    RxDialogEditSureCancel rxDialogEditSureCancel = new RxDialogEditSureCancel(FrequentlyTransportedGoodsActivity.this);
                    rxDialogEditSureCancel.getTitleView().setVisibility(View.GONE);
                    rxDialogEditSureCancel.getSureView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String str = rxDialogEditSureCancel.getEditText().getText().toString();
                            mPresenter.getMyUsualCargoAdd(str);
                            rxDialogEditSureCancel.cancel();
                        }
                    });
                    rxDialogEditSureCancel.getCancelView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            rxDialogEditSureCancel.cancel();
                        }
                    });
                    rxDialogEditSureCancel.show();
                }

            }
        });
    }


    private void initRecyclerView() {
        llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(llm);
        mAdapter = new GoodsRecycleViewAdapter(this);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(30));
        mRecyclerView.setAdapter(mAdapter);
        // 绑定下拉刷新事件
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                mPresenter.getMyUsualCargo(pageNo, pageSize);
            }
        });
        // 绑定上拉刷新加载更多事件
        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (!pageEndFlag) {
                    pageNo++;
                    mPresenter.getMyUsualCargo(pageNo, pageSize);
                } else {
                    layout.finishLoadMore(500);
                    FrequentlyTransportedGoodsActivity.this.shotToast(AppConfig.LOAD_INFO_FINISH);
                }
            }
        });
        layout = mSmartRefreshLayout.getLayout();
    }

    /**
     * 初始化标题栏
     */
    private void initToolbar() {
        mToolbar.setTitleMarginTop();
        mToolbar.setMainTitle(getString(R.string.frequently_transported_goods));
        mToolbar.setLeftTitleVisible();
        mToolbar.setRightTitleText("编辑");
        TextView leftTitleView = mToolbar.getLeftTitleView();
        mToolbar.setLeftTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("".equals(leftTitleView.getText()) || leftTitleView.getText() == null) {
                    finish();
                } else {
                    leftTitleView.setText("");
                    mToolbar.getRightTitleView().setText("编辑");
                    Drawable drawable = FrequentlyTransportedGoodsActivity.this.getResources().getDrawable(R.mipmap.ic_back);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    leftTitleView.setCompoundDrawables(drawable, null, null, null);
                    mAdapter.unvisiableAll();
                    addGoods.setText("添加货品");
                }

            }
        });
        mToolbar.setRightTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.setOnItemViewClick(FrequentlyTransportedGoodsActivity.this);
                if ("编辑".equals(mToolbar.getRightTitleView().getText())) {
                    mToolbar.getRightTitleView().setText("全选");
                    leftTitleView.setText("取消");
                    leftTitleView.setCompoundDrawables(null, null, null, null);
                    mAdapter.unSelectAll();
                    mAdapter.visiableAll();
                    addGoods.setText("删除");
                } else {
                    mAdapter.selectAll();
                }
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter = new FrequentlyTransportedGoodsPresenterImp(this, this);
        mPresenter.getMyUsualCargo(pageSize, pageNo);
        isRefresh = false;
    }


    public void setData(final List<Goods> data) {
        if (data != null && data.size() > 0) {
            mSmartRefreshLayout.setVisibility(View.VISIBLE);
            if (pageNo == 1) {
                this.data = data;
            } else {
                this.data.addAll(data);
            }
            mAdapter.setData(this.data);
        } else {
            mSmartRefreshLayout.setVisibility(View.GONE);
        }
    }

    public void getInitDataSuccess() {
        if (pageNo == 1) {
            layout.finishRefresh(500);
        } else {
            layout.finishLoadMore(500);
        }
    }

    /**
     * 当数据为空显示的说明页面
     */
    public void showEmptyPage() {
        mSmartRefreshLayout.setVisibility(View.GONE);
    }

    /**
     * 重新刷新数据
     */
    public void setRefreshData() {
        mPresenter.getMyUsualCargo(pageNo, pageSize);
    }


    @Override
    public void myUsualCargoSuccess(String msg) {

    }

    @Override
    public void myUsualCargoFailed(String msg) {

    }

    @Override
    public void myUsualCargoAddSuccess(String msg) {
        mPresenter.getMyUsualCargo(pageNo, pageSize);
        shotToast("增加成功");
    }

    @Override
    public void myUsualCargoAddFailed(String msg) {
        shotToast("增加失败");
    }

    @Override
    public void myUsualCargoDelSuccess(String msg) {
        shotToast("删除成功");
    }

    @Override
    public void myUsualCargoDelFailed(String msg) {
        shotToast("删除失败");
    }

    @Override
    public void itemClick(int position) {
        this.mPosition = position;
        if (data.get(position).isTag()) {
            data.get(position).setTag(false);
        } else {
            data.get(position).setTag(true);
        }
        mAdapter.notifyItemChanged(position);
    }
}
