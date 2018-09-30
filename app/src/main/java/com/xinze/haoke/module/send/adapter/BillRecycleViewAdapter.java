package com.xinze.haoke.module.send.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xinze.haoke.R;
import com.xinze.haoke.module.main.modle.OrderItem;
import com.xinze.haoke.utils.DateUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author lxf
 *         订单界面适配器
 */
public class BillRecycleViewAdapter extends RecyclerView.Adapter<BillRecycleViewAdapter.ViewHolder> implements View.OnClickListener {

    private List<OrderItem> mBS;
    private Context mContext;
    private View view;
    private String from;

    public BillRecycleViewAdapter(Context context, List<OrderItem> mbs) {
        this.mContext = context;
        this.mBS = mbs;
    }

    public BillRecycleViewAdapter(Context context, String from) {
        this.mContext = context;
        this.from = from;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mContext).inflate(R.layout.bill_directional_rv_item, parent, false);
        ViewHolder holder = new ViewHolder(view);

        //给布局设置点击和长点击监听
        view.setOnClickListener(this);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        OrderItem orderRecycleViewItem = mBS.get(position);

        String startText = orderRecycleViewItem.getFromDetailAdress();
        String endText = orderRecycleViewItem.getToDetailAdress();
        int carCount = orderRecycleViewItem.getTruckNumber();
        String productName = orderRecycleViewItem.getProductName();
        String truckCode = orderRecycleViewItem.getTruckCode();
        BigDecimal distance = orderRecycleViewItem.getDistance();
        BigDecimal freight = orderRecycleViewItem.getPrice();
        String dateFrom = orderRecycleViewItem.getDateFrom();
        dateFrom = DateUtils.getDate(dateFrom);
        String dateTo = orderRecycleViewItem.getDateTo();
        dateTo = DateUtils.getDate(dateTo);
        int leftNumber = orderRecycleViewItem.getLeft_number();

        String count = mContext.getResources().getString(R.string.bill_car_count);
        String money = mContext.getResources().getString(R.string.bill_car_freight);
        String type = mContext.getResources().getString(R.string.order_truck_type);
        String product = mContext.getResources().getString(R.string.order_product_name);
        String time = mContext.getResources().getString(R.string.send_goods_date);
        String distances = mContext.getResources().getString(R.string.order_distance);
        String robbingBillNumber = mContext.getResources().getString(R.string.directional_robbing_bill);
        String receiptBillNumber = mContext.getResources().getString(R.string.directional_receipt_bill);


        count = getString(String.valueOf(carCount), count);
        money = getString(String.valueOf(freight), money);
        type = getString(truckCode, type);
        product = getString(productName, product);
        distances = getString(String.valueOf(distance), distances);
        time = getString(dateFrom, dateTo, time);


        viewHolder.directionalTvCarCount.setText(count);
        viewHolder.directionalTvFreight.setText(money);
        viewHolder.directionalTvCarType.setText(type);
        viewHolder.directionalTvProductName.setText(product);
        viewHolder.directionalDistance.setText(distances);
        viewHolder.directionalTime.setText(time);


        if ("0".equals(robbingBillNumber)) {
            if ("DirectionalBillFragment".equals(from)) {
                viewHolder.directionalIvState.setText(mContext.getResources().getString(R.string.receipt_bill));
            } else {
                viewHolder.directionalIvState.setText(mContext.getResources().getString(R.string.bill_robbing));
            }

            viewHolder.directionalIvState.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.circle_gray_button));
        } else {
            if ("DirectionalBillFragment".equals(from)) {
                viewHolder.directionalIvState.setText(String.format(receiptBillNumber, leftNumber));
            } else {
                viewHolder.directionalIvState.setText(String.format(robbingBillNumber, leftNumber));

            }

            viewHolder.directionalIvState.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.circle_orange_button));
        }
        viewHolder.directionalIvState.setOnClickListener(this);
        viewHolder.directionalIvState.setTag(position);
        if (!TextUtils.isEmpty(startText)) {
            viewHolder.directionalTvRightStart.setText(startText);
        }
        if (!TextUtils.isEmpty(endText)) {
            viewHolder.directionalTvRightEnd.setText(endText);
        }


        if (position == mBS.size() - 1) {
            viewHolder.directionalItemSpace.setVisibility(View.GONE);
        } else {
            viewHolder.directionalItemSpace.setVisibility(View.VISIBLE);
        }

        //将position保存在itemView的Tag中，以便点击时进行获取
        viewHolder.itemView.setTag(position);
    }

    private String getString(String dateFrom, String dateTo, String time) {
        time = String.format(time, dateFrom, dateTo);
        return time;
    }

    private String getString(String id, String orderId) {
        id = String.format(orderId, id);
        return id;
    }

    public void clearData() {
        if (mBS != null) {
            mBS.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mBS == null ? 0 : mBS.size();
    }


    //点击事件回调
    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            switch (v.getId()) {
                case R.id.directional_iv_state:
                    mOnItemClickListener.jumpSelectCar((int) v.getTag());
                    break;

                default:
                    mOnItemClickListener.jumpDetails((int) v.getTag());
                    break;
            }

        }
    }

    public void setData(List<OrderItem> data) {
        this.mBS = data;
        notifyDataSetChanged();
    }

    //自定义监听事件
    public interface OnRecyclerViewItemClickListener {
        void jumpDetails(int position);

        void jumpSelectCar(int position);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.directional_tv_right_start)
        TextView directionalTvRightStart;
        @BindView(R.id.directional_tv_right_end)
        TextView directionalTvRightEnd;
        @BindView(R.id.directional_iv_state)
        TextView directionalIvState;
        @BindView(R.id.directional_tv_car_count)
        TextView directionalTvCarCount;
        @BindView(R.id.directional_tv_freight)
        TextView directionalTvFreight;
        @BindView(R.id.directional_tv_car_type)
        TextView directionalTvCarType;
        @BindView(R.id.directional_tv_productName)
        TextView directionalTvProductName;
        @BindView(R.id.directional_time)
        TextView directionalTime;
        @BindView(R.id.directional_distance)
        TextView directionalDistance;
        @BindView(R.id.directional_item_space)
        View directionalItemSpace;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
