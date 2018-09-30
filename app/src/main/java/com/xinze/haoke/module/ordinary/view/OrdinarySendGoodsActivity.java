package com.xinze.haoke.module.ordinary.view;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.xinze.haoke.App;
import com.xinze.haoke.R;
import com.xinze.haoke.base.BaseActivity;
import com.xinze.haoke.config.AppConfig;
import com.xinze.haoke.module.carType.modle.CarType;
import com.xinze.haoke.module.carType.view.SelectCarTypeActivity;
import com.xinze.haoke.module.ordinary.modle.Bill;
import com.xinze.haoke.module.ordinary.presenter.OrdinarySendGoodsPresenterImp;
import com.xinze.haoke.utils.MessageEvent;
import com.xinze.haoke.widget.SelectAddressView2;
import com.xinze.haoke.widget.SimpleToolbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
import cn.qqtheme.framework.picker.AddressPicker;

/**
 * 普通发货
 *
 * @author lxf
 */
public class OrdinarySendGoodsActivity extends BaseActivity implements IOrdinarySendGoodsView, AddressPicker.OnAddressPickListener {
    @BindView(R.id.ordinary_toolbar)
    SimpleToolbar ordinaryToolbar;
    @BindView(R.id.ordinary_from_et)
    EditText ordinaryFromEt;
    @BindView(R.id.ordinary_from_address_et)
    EditText ordinaryFromAddressEt;
    @BindView(R.id.ordinary_from_contact_et)
    EditText ordinaryFromContactEt;
    @BindView(R.id.ordinary_from_phone_et)
    EditText ordinaryFromPhoneEt;
    @BindView(R.id.ordinary_to_et)
    EditText ordinaryToEt;
    @BindView(R.id.ordinary_to_address_et)
    EditText ordinaryToAddressEt;
    @BindView(R.id.ordinary_to_contact_et)
    EditText ordinaryToContactEt;
    @BindView(R.id.ordinary_to_phone_et)
    EditText ordinaryToPhoneEt;
    @BindView(R.id.ordinary_info_pay_et)
    EditText ordinaryInfoPayEt;
    @BindView(R.id.ordinary_load_pay_et)
    EditText ordinaryLoadPayEt;
    @BindView(R.id.ordinary_unload_pay_et)
    EditText ordinaryUnloadPayEt;
    @BindView(R.id.ordinary_goods_et)
    EditText ordinaryGoodsEt;
    @BindView(R.id.ordinary_distance_et)
    EditText ordinaryDistanceEt;
    @BindView(R.id.ordinary_freight_et)
    EditText ordinaryFreightEt;
    @BindView(R.id.ordinary_delivery_from_date_et)
    EditText ordinaryDeliveryFromDateEt;
    @BindView(R.id.ordinary_delivery_to_date_et)
    EditText ordinaryDeliveryToDateEt;
    @BindView(R.id.ordinary_car_number_et)
    EditText ordinaryCarNumberEt;
    @BindView(R.id.ordinary_car_type_et)
    EditText ordinaryCarTypeEt;
    @BindView(R.id.ordinary_car_long_et)
    EditText ordinaryCarLongEt;
    @BindView(R.id.ordinary_road_loss_et)
    EditText ordinaryRoadLossEt;
    @BindView(R.id.ordinary_remark_et)
    EditText ordinaryRemarkEt;
    @BindView(R.id.ordinary_wait_confirm)
    TextView ordinaryWaitConfirm;
    @BindView(R.id.ordinary_protocol_iv)
    ImageView ordinaryProtocolIv;
    @BindView(R.id.ordinary_protocol)
    TextView ordinaryProtocol;
    @BindView(R.id.ordinaryProtocolLL)
    LinearLayout ordinaryProtocolLL;
    @BindView(R.id.ordinary_protocol_rl)
    RelativeLayout ordinaryProtocolRL;
    @BindView(R.id.ordinary_release)
    Button ordinaryRelease;
    @BindView(R.id.ordinary_half)
    View half;
    @BindView(R.id.imcv_tem_mater_calendar_week)
    MaterialCalendarView imcvTemMaterCalendarWeek;


    @BindView(R.id.ordinary_select_address)
    SelectAddressView2 ordinarySelectAddress;

    private ArrayList<Province> provinces;


    private boolean isSelectedProtocol;
    private boolean needMeComfirm;
    private static final String PROTOCOL ="我已仔细阅读并同意<font color=\"#52affc\">《委托运输服务合同》</font>";


    private int mCurrentView;
    private String fromID;
    private String toID = "0";
    private int who;

    private OrdinarySendGoodsPresenterImp mPresenter;
    private String from;
    private CarType carType;

    @Override
    protected int initLayout() {
        return R.layout.ordinary_send_goods_activity;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        from = getIntent().getStringExtra("from");
        initTitleBar();

        ordinarySelectAddress.setmOnSelectAddressListener(new SelectAddressView2.OnSelectAddressListener2() {
            @Override
            public void selectAddress(String name, String id) {
                half.setVisibility(View.GONE);
                switch (mCurrentView) {
                    case R.id.ordinary_from_et:

                        fromID = id;
                        ordinaryFromEt.setText(name);

                        break;
                    case R.id.ordinary_to_et:

                        toID = id;
                        ordinaryToEt.setText(name);

                        break;
                    default:
                        break;
                }
            }
        });
        //隐藏标题栏和两边的箭头
        imcvTemMaterCalendarWeek.setTopbarVisible(true);
        //编辑日历属性
        imcvTemMaterCalendarWeek.state().edit()
                //设置每周开始的第一天
                .setFirstDayOfWeek(Calendar.MONDAY)
                //设置可以显示的最早时间
                .setMinimumDate(CalendarDay.from(2010, 4, 3))
                //设置可以显示的最晚时间
                .setMaximumDate(CalendarDay.from(2200, 12, 31))
                //设置显示模式，可以显示月的模式，也可以显示周的模式
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                // 返回对象并保存
                .commit();

        Calendar calendar = Calendar.getInstance();
        //当日选中
        imcvTemMaterCalendarWeek.setSelectedDate(calendar.getTime());

        //设置选中日期颜色。
        imcvTemMaterCalendarWeek.setSelectionColor(getResources().getColor(R.color.themeOrange));

        //设置日期选中时的点击事件。
        imcvTemMaterCalendarWeek.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                //在这个方法中处理选中事件。
                imcvTemMaterCalendarWeek.setVisibility(View.GONE);
                if (who == R.id.ordinary_delivery_from_date_et) {
                    ordinaryDeliveryFromDateEt.setText(date.getYear() + "/" + date.getMonth() + "/" + date.getDay());
                } else {
                    ordinaryDeliveryToDateEt.setText(date.getYear() + "/" + date.getMonth() + "/" + date.getDay());
                }
            }
        });
    }

    private void initTitleBar() {
        ordinaryToolbar.setLeftTitleVisible();
        if ("定向".equals(from)) {
            ordinaryToolbar.setMainTitle(R.string.home_directional_shipper);
            ordinaryProtocolRL.setVisibility(View.GONE);
            ordinaryWaitConfirm.setVisibility(View.GONE);
        } else {
            ordinaryToolbar.setMainTitle(R.string.ordinary_send_goods);
            ordinaryProtocol.setText(Html.fromHtml(PROTOCOL));
        }
        ordinaryToolbar.setLeftTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    protected void initData() {
        super.initData();
        mPresenter = new OrdinarySendGoodsPresenterImp(this, this);
        mPresenter.getAreaList("0");
    }

    @OnClick({R.id.ordinary_from_et, R.id.ordinary_to_et, R.id.ordinary_wait_confirm, R.id.ordinary_protocol,
            R.id.ordinary_release, R.id.ordinary_delivery_from_date_et, R.id.ordinary_delivery_to_date_et,
            R.id.ordinary_car_type_et, R.id.ordinary_protocol_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ordinary_from_et:
                mCurrentView = R.id.ordinary_from_et;
                showAddressDialog();
                break;
            case R.id.ordinary_to_et:
                mCurrentView = R.id.ordinary_to_et;
                showAddressDialog();
                break;
            case R.id.ordinary_wait_confirm:
                needMeComfirm = !needMeComfirm;

                if (needMeComfirm) {
                    Drawable drawable = getApplicationContext().getResources().getDrawable(R.mipmap.select_choicd);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    ordinaryWaitConfirm.setCompoundDrawables(drawable, null, null, null);
                } else {
                    Drawable drawable = getApplicationContext().getResources().getDrawable(R.mipmap.select_choice);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    ordinaryWaitConfirm.setCompoundDrawables(drawable, null, null, null);
                }
                break;
            case R.id.ordinary_release:
                release();
                break;
            case R.id.ordinary_delivery_from_date_et:
                who = R.id.ordinary_delivery_from_date_et;
                imcvTemMaterCalendarWeek.setVisibility(View.VISIBLE);
                break;
            case R.id.ordinary_delivery_to_date_et:
                who = R.id.ordinary_delivery_to_date_et;
                imcvTemMaterCalendarWeek.setVisibility(View.VISIBLE);
                break;
            case R.id.ordinary_car_type_et:
                openActivity(SelectCarTypeActivity.class);
                break;

            case R.id.ordinary_protocol:
            case R.id.ordinary_protocol_iv:
                isSelectedProtocol = !isSelectedProtocol;
                if (isSelectedProtocol) {
                    ordinaryProtocolIv.setBackgroundResource(R.mipmap.select_choicd);
                } else {
                    ordinaryProtocolIv.setBackgroundResource(R.mipmap.select_choice);
                }

                break;

            default:
                break;
        }
    }

    private void release() {
        Bill bill = new Bill();
        bill.setUserId(App.mUser.getId());
        bill.setFromAreaId(fromID);
        bill.setFromDetailAdress(ordinaryFromAddressEt.getText().toString());
        bill.setFromName(ordinaryFromContactEt.getText().toString());
        bill.setFromPhone(ordinaryFromPhoneEt.getText().toString());
        bill.setToAreaId(toID);
        bill.setToDetailAdress(ordinaryToAddressEt.getText().toString());
        bill.setToName(ordinaryToContactEt.getText().toString());
        bill.setToPhone(ordinaryToPhoneEt.getText().toString());
        bill.setPrice(ordinaryInfoPayEt.getText().toString());
        bill.setDistance(ordinaryDistanceEt.getText().toString());
        bill.setPrice(ordinaryFreightEt.getText().toString());
        bill.setDateFrom(ordinaryDeliveryFromDateEt.getText().toString());
        bill.setDateTo(ordinaryDeliveryToDateEt.getText().toString());
        bill.setTruckNumber(ordinaryCarNumberEt.getText().toString());
        bill.setTruckCode(carType.getTruckcode());
        bill.setTruckLong(ordinaryCarLongEt.getText().toString());
        bill.setJourneyLoss(ordinaryRoadLossEt.getText().toString());
        bill.setRemarks(ordinaryRemarkEt.getText().toString());
        if ("定向".equals(from)) {
            if (isSelectedProtocol) {
                bill.setWlBilltype("1");
            }
        } else {
            if (needMeComfirm) {
                bill.setConfirmFlag("1");
            } else {
                bill.setConfirmFlag("0");
            }
            bill.setWlBilltype("0");
            bill.setDriverId("");
        }
        bill.setMsgPrice(ordinaryInfoPayEt.getText().toString());
        bill.setLoadPrice(ordinaryLoadPayEt.getText().toString());
        bill.setUnloadPrice(ordinaryUnloadPayEt.getText().toString());

        mPresenter.releaseTheBillOfGoods(bill);
    }

    private void showAddressDialog() {
        ordinarySelectAddress.setViewVisible();
        half.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAddressPicked(Province province, City city, County county) {

    }

    public void setAreaList(ArrayList<Province> result) {
        this.provinces = result;
    }

    @Override
    public void getAreaListSuccess(String msg) {

    }

    @Override
    public void getAreaListFailed(String msg) {

    }

    @Override
    public void releaseTheBillOfGoodsSuccess(String msg) {
        shotToast(msg);
    }

    @Override
    public void releaseTheBillOfGoodsFailed(String msg) {
        shotToast(msg);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void receive(Object messageEvent) {
        if (messageEvent instanceof CarType){
            carType = (CarType) messageEvent;
            ordinaryCarTypeEt.setText(carType.getTruckname());
        }else{
            ordinaryCarTypeEt.setText("");
        }
    }
}
