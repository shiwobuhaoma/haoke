package com.xinze.haoke.module.ordinary.view;

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
import com.xinze.haoke.config.ProtocolConfig;
import com.xinze.haoke.module.about.view.AboutUsActivity;
import com.xinze.haoke.module.goods.bean.Goods;
import com.xinze.haoke.module.ordinary.modle.Bill;
import com.xinze.haoke.module.ordinary.presenter.OrdinarySendGoodsPresenterImp;
import com.xinze.haoke.module.select.cartype.modle.CarType;
import com.xinze.haoke.module.select.cartype.view.SelectCarTypeActivity;
import com.xinze.haoke.module.select.driver.view.SelectDriverActivity;
import com.xinze.haoke.module.select.goodstype.view.SelectGoodsTypeActivity;
import com.xinze.haoke.widget.FromDetailsInfoView;
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
    @BindView(R.id.from)
    FromDetailsInfoView fromWhere;
    @BindView(R.id.to)
    FromDetailsInfoView toWhere;

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

    @BindView(R.id.ordinary_unload_pay)
    TextView ordinaryUnloadPay;
    @BindView(R.id.ordinary_goods)
    TextView ordinaryGoods;
    @BindView(R.id.ordinary_distance)
    TextView ordinaryDistance;
    @BindView(R.id.ordinary_freight)
    TextView ordinaryFreight;
    @BindView(R.id.ordinary_delivery_date)
    TextView ordinaryDeliveryDate;
    @BindView(R.id.ordinary_delivery_line)
    View ordinaryDeliveryLine;
    @BindView(R.id.ordinary_car_number)
    TextView ordinaryCarNumber;
    @BindView(R.id.ordinary_car_type)
    TextView ordinaryCarType;
    @BindView(R.id.ordinary_car_long)
    TextView ordinaryCarLong;
    @BindView(R.id.ordinary_road_loss)
    TextView ordinaryRoadLoss;
    @BindView(R.id.ordinary_remark)
    TextView ordinaryRemark;
    @BindView(R.id.ordinary_rl)
    RelativeLayout ordinaryRl;

    private ArrayList<Province> provinces;


    private boolean isSelectedProtocol;
    private boolean needMeConfirm;
    private static final String PROTOCOL = "我已仔细阅读并同意<font color=\"#52affc\">《委托运输服务合同》</font>";
    private static final String SEND = "托运人";
    private static final String RECEIVE = "接收人";
    private static final String DRIVER = "司机选择";
    private static final String RELEASE = "发布货单";
    private static final String DIRECTIONA = "定向";


    private String fromID;
    private String toID = "0";
    private int who;

    private OrdinarySendGoodsPresenterImp mPresenter;
    private String from;
    private CarType carType;
    private Goods goods;
    private String value = SEND;
    private Bill bill;

    @Override
    protected int initLayout() {
        return R.layout.ordinary_send_goods_activity;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        from = getIntent().getStringExtra("from");
        bill = (Bill) getIntent().getSerializableExtra("bill");
        if (bill !=  null){
            fromWhere.setFrom(bill.getFromAreaId());
            fromWhere.setDetailsAddress(bill.getFromDetailAdress());
            fromWhere.setContact(bill.getFromName());
            fromWhere.setPhone(bill.getFromPhone());

            toWhere.setFrom(bill.getToAreaId());
            toWhere.setDetailsAddress(bill.getToDetailAdress());
            toWhere.setContact(bill.getToName());
            toWhere.setPhone(bill.getToPhone());

            ordinaryInfoPayEt.setText(bill.getMsgPrice());
            ordinaryLoadPayEt.setText(bill.getLoadPrice());
            ordinaryUnloadPayEt.setText(bill.getUnloadPrice());
            ordinaryGoodsEt.setText(bill.getProductName());
            ordinaryDistanceEt.setText(bill.getDistance());
            ordinaryFreightEt.setText(bill.getPrice());
            ordinaryDeliveryFromDateEt.setText(bill.getDateFrom().substring(0,11));
            ordinaryDeliveryToDateEt.setText(bill.getDateTo().substring(0,11));
            ordinaryCarNumberEt.setText(bill.getTruckNumber());
            ordinaryCarTypeEt.setText(bill.getTruckName());
            ordinaryCarLongEt.setText(bill.getTruckLong());
            ordinaryRoadLossEt.setText(bill.getJourneyLoss());
            ordinaryRemarkEt.setText(bill.getRemarks());
            if ("0".equals(bill.getConfirmFlag())){
                ordinaryWaitConfirm.setVisibility(View.GONE);
            }else{
                selectNeedMeConfirm();
            }
            isSelectedProtocol = true;
            ordinaryProtocolIv.setBackgroundResource(R.mipmap.select_choicd);
        }
        initTitleBar();
        fromWhere.setTitle(SEND);
        fromWhere.setShowAddressDialog(new FromDetailsInfoView.ShowAddressDialog() {
            @Override
            public void showAddressDialog() {
                value = SEND;
                ordinarySelectAddress.setViewVisible();
                half.setVisibility(View.VISIBLE);
            }
        });
        toWhere.setTitle(RECEIVE);
        toWhere.setShowAddressDialog(new FromDetailsInfoView.ShowAddressDialog() {

            @Override
            public void showAddressDialog() {
                value = RECEIVE;
                ordinarySelectAddress.setViewVisible();
                half.setVisibility(View.VISIBLE);
            }
        });
        ordinarySelectAddress.setmOnSelectAddressListener(new SelectAddressView2.OnSelectAddressListener2() {
            @Override
            public void selectAddress(String name, String id) {
                half.setVisibility(View.GONE);
                fromID = id;
                if (value.equals(SEND)) {
                    fromWhere.setFrom(name);
                } else if (value.equals(RECEIVE)) {
                    toWhere.setFrom(name);
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
                    ordinaryDeliveryFromDateEt.setText(date.getYear() + ":" + date.getMonth() + ":" + date.getDay()+" 00:00:00");
                } else {
                    ordinaryDeliveryToDateEt.setText(date.getYear() + ":" + date.getMonth() + ":" + date.getDay()+" 00:00:00");
                }
            }
        });
    }

    private void initTitleBar() {
        ordinaryToolbar.setLeftTitleVisible();
        if (DIRECTIONA.equals(from)) {
            ordinaryToolbar.setMainTitle(R.string.home_directional_shipper);
            ordinaryProtocolRL.setVisibility(View.GONE);
            ordinaryWaitConfirm.setVisibility(View.GONE);
            ordinaryRelease.setText("下一步");
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

    @OnClick({R.id.ordinary_wait_confirm, R.id.ordinary_protocol, R.id.ordinary_goods_et,
            R.id.ordinary_release, R.id.ordinary_delivery_from_date_et, R.id.ordinary_delivery_to_date_et,
            R.id.ordinary_car_type_et, R.id.ordinary_protocol_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ordinary_wait_confirm:
                needMeConfirm = !needMeConfirm;

                if (needMeConfirm) {
                    selectNeedMeConfirm();
                } else {
                    unselectNeedMeConfirm();
                }
                break;
            case R.id.ordinary_release:
                if (RELEASE.equals(ordinaryRelease.getText().toString())) {
                    release();
                } else {
                    Bill bill = createBill();
                    if (bill != null) {
                        openActivity(SelectDriverActivity.class, "bill", bill);
                    }
                }
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
            case R.id.ordinary_goods_et:
                openActivity(SelectGoodsTypeActivity.class);
                break;

            case R.id.ordinary_protocol:
                openActivity(AboutUsActivity.class, "type", ProtocolConfig.TRANSPORT_SERVICE_PROTOCOL);
                break;
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

    private void unselectNeedMeConfirm() {
        Drawable drawable = getApplicationContext().getResources().getDrawable(R.mipmap.select_choice);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        ordinaryWaitConfirm.setCompoundDrawables(drawable, null, null, null);
    }

    private void selectNeedMeConfirm() {
        Drawable drawable = getApplicationContext().getResources().getDrawable(R.mipmap.select_choicd);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        ordinaryWaitConfirm.setCompoundDrawables(drawable, null, null, null);
    }

    private void release() {
        Bill bill = createBill();
        if (bill == null) {
            return;
        }
        mPresenter.releaseTheBillOfGoods(bill);
    }

    private Bill createBill() {
        if(bill == null){
            Bill bill = new Bill();
            bill.setUserId(App.mUser.getId());
            bill.setFromAreaId(fromID);
            if (SEND.equals(fromWhere.getFromText())) {
                bill.setFromDetailAdress(fromWhere.getFromText());
                bill.setFromName(fromWhere.getContact());
                bill.setFromPhone(fromWhere.getPhone());
            } else {
                bill.setFromDetailAdress(toWhere.getFromText());
                bill.setFromName(toWhere.getContact());
                bill.setFromPhone(toWhere.getPhone());
            }
            bill.setToAreaId(toID);
            bill.setPrice(ordinaryInfoPayEt.getText().toString());
            bill.setDistance(ordinaryDistanceEt.getText().toString());
            bill.setPrice(ordinaryFreightEt.getText().toString());
            bill.setDateFrom(ordinaryDeliveryFromDateEt.getText().toString());
            bill.setDateTo(ordinaryDeliveryToDateEt.getText().toString());
            bill.setTruckNumber(ordinaryCarNumberEt.getText().toString());
            if (carType == null) {
                return null;
            }
            bill.setTruckCode(carType.getTruckcode());
            bill.setTruckLong(ordinaryCarLongEt.getText().toString());
            bill.setJourneyLoss(ordinaryRoadLossEt.getText().toString());
            bill.setRemarks(ordinaryRemarkEt.getText().toString());
            bill.setMsgPrice(ordinaryInfoPayEt.getText().toString());
            bill.setLoadPrice(ordinaryLoadPayEt.getText().toString());
            bill.setUnloadPrice(ordinaryUnloadPayEt.getText().toString());
            if ("定向".equals(from)) {
                bill.setWlBilltype("1");
                bill.setConfirmFlag("0");
            } else {
                if (needMeConfirm) {
                    bill.setConfirmFlag("1");
                } else {
                    bill.setConfirmFlag("0");
                }
                bill.setWlBilltype("0");
                bill.setDriverId("");
            }
        }




        return bill;
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
        finish();
        shotToast(msg);
    }

    @Override
    public void releaseTheBillOfGoodsFailed(String msg) {
        shotToast(msg);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void receive(Object messageEvent) {
        if (messageEvent instanceof CarType) {
            carType = (CarType) messageEvent;
            ordinaryCarTypeEt.setText(carType.getTruckname());
        } else if (messageEvent instanceof Goods) {
            goods = (Goods) messageEvent;
            ordinaryGoodsEt.setText(goods.getCargoName());
            ordinaryGoodsEt.setSelection(goods.getCargoName().length());
        } else {
            ordinaryCarTypeEt.setText("");
        }
    }
}
