package com.xinze.haoke.module.main.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.xinze.haoke.App;
import com.xinze.haoke.R;
import com.xinze.haoke.base.BaseFragment;
import com.xinze.haoke.module.regular.view.RegularRunActivity;
import com.xinze.haoke.module.send.adapter.SelectPageAdapter;
import com.xinze.haoke.module.send.fragment.DirectionalBillFragment;
import com.xinze.haoke.module.send.fragment.OrdinaryBillFragment;
import com.xinze.haoke.utils.DialogUtil;
import com.xinze.haoke.widget.SimpleToolbar;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author lxf
 * 普通货单和定向货单
 */
public class GoodsBillFragment extends BaseFragment {
    @BindView(R.id.send_goods_toolbar)
    SimpleToolbar sendGoodsToolbar;

    @BindView(R.id.send_goods_vp)
    ViewPager sendGoodsVp;

    private RadioButton mRbStart;
    private RadioButton mRbEnd;

    private static final int RADIO_START = 0;
    private static final int RADIO_END = 1;

    private int mCurrentRadio = 0;
    @Override
    protected int initLayout() {
        return R.layout.send_goods_activity;
    }

    @Override
    protected void initView() {
        initToolBar();
        initViewPager();
        changeToolbarChecked(mCurrentRadio);
    }

    private void initToolBar() {
        // 初始化标题栏上的RadioButton
        RadioGroup radioTitle = (RadioGroup) LayoutInflater.from(mActivity).inflate(
                R.layout.title_bar_two_radio, null);

        mRbStart = (RadioButton) radioTitle.findViewById(R.id.title_left);
        mRbEnd = (RadioButton) radioTitle.findViewById(R.id.title_right);
        mRbStart.setText(getString(R.string.bill_ordinary));
        mRbEnd.setText(getString(R.string.bill_directional));
        sendGoodsToolbar.setTitleView(radioTitle);
        sendGoodsToolbar.setTitleMarginTop();
        sendGoodsToolbar.setRightTitleText(getString(R.string.order_freight_goods));
        sendGoodsToolbar.setRightTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (App.mUser.isLogin()) {
                    openActivity(RegularRunActivity.class);
                } else {
                    DialogUtil.showUnloginDialog(mActivity);
                }

            }
        });
        radioTitle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.title_left:
                        changeToolbarChecked(RADIO_START);
                        break;
                    case R.id.title_right:
                        if (App.mUser.isLogin()) {
                            changeToolbarChecked(RADIO_END);
                        } else {
                            DialogUtil.showUnloginDialog(mActivity, new DialogUtil.DialogCallBack() {
                                @Override
                                public void cancle() {
                                    changeToolbarChecked(RADIO_START);
                                }
                            });
                        }

                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void changeToolbarChecked(int where) {
        sendGoodsVp.setCurrentItem(where);
        mCurrentRadio = where;
        switch (mCurrentRadio) {
            case RADIO_START:
                mRbStart.setChecked(true);
                mRbEnd.setChecked(false);
                break;
            case RADIO_END:
                mRbStart.setChecked(false);
                mRbEnd.setChecked(true);
                break;
            default:
                break;
        }
    }

    private void initViewPager() {
        ArrayList<Fragment> list = new ArrayList<>();
        list.add(new OrdinaryBillFragment());
        list.add(new DirectionalBillFragment());

        SelectPageAdapter adapter = new SelectPageAdapter(getFragmentManager(), list);
        sendGoodsVp.setAdapter(adapter);
        sendGoodsVp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                changeToolbarChecked(position);
            }
        });
    }

    public static GoodsBillFragment newInstance() {
        return new GoodsBillFragment();
    }

    public void refresh() {

    }

    public void clearData() {

    }
}
