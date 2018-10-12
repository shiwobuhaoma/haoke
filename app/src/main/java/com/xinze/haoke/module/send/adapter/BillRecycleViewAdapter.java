package com.xinze.haoke.module.send.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xinze.haoke.R;
import com.xinze.haoke.module.main.modle.OrderItem;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author lxf
 * 订单界面适配器
 */
public class BillRecycleViewAdapter extends RecyclerView.Adapter<BillRecycleViewAdapter.ViewHolder> implements View.OnClickListener {

    private List<OrderItem> mBS;
    private Context mContext;
    private View view;
    private String from;



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
        String id = orderRecycleViewItem.getId();
        String startText = orderRecycleViewItem.getFromDetailAdress();
        String endText = orderRecycleViewItem.getToDetailAdress();
        int carCount = orderRecycleViewItem.getTruckNumber();
        String productName = orderRecycleViewItem.getProductName();
        BigDecimal distance = orderRecycleViewItem.getDistance();
        BigDecimal freight = orderRecycleViewItem.getPrice();
        int leftNumber = orderRecycleViewItem.getLeft_number();

        String billNumber = mContext.getResources().getString(R.string.bill_number);
        String count = mContext.getResources().getString(R.string.bill_car_count);
        String money = mContext.getResources().getString(R.string.bill_car_freight);
        String product = mContext.getResources().getString(R.string.order_product_name);
        String distances = mContext.getResources().getString(R.string.order_distance);
        String remainCars = mContext.getResources().getString(R.string.remain_cars);


        billNumber = getString(id, billNumber);
        remainCars = getString(String.valueOf(leftNumber), remainCars);
        count = getString(String.valueOf(carCount), count);
        money = getString(String.valueOf(freight), money);
        product = getString(productName, product);
        distances = getString(String.valueOf(distance), distances);


        viewHolder.billNumber.setText(billNumber);
        if (!TextUtils.isEmpty(startText)) {
            viewHolder.directionalTvRightStart.setText(startText);
        }
        if (!TextUtils.isEmpty(endText)) {
            viewHolder.directionalTvRightEnd.setText(endText);
        }
        viewHolder.directionalIvState.setText(remainCars);
        viewHolder.directionalTvCarCount.setText(count);
        viewHolder.directionalTvFreight.setText(money);
        viewHolder.directionalTvProductName.setText(product);
        viewHolder.directionalDistance.setText(distances);

        viewHolder.receiveDetails.setOnClickListener(this);
        viewHolder.relay.setOnClickListener(this);

        viewHolder.directionalIvState.setOnClickListener(this);
        viewHolder.directionalIvState.setTag(position);


        if (position == mBS.size() - 1) {
            viewHolder.directionalItemSpace.setVisibility(View.GONE);
        } else {
            viewHolder.directionalItemSpace.setVisibility(View.VISIBLE);
        }

        //将position保存在itemView的Tag中，以便点击时进行获取
        viewHolder.itemView.setTag(position);
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


    /**
     *点击事件回调
     */
    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            switch (v.getId()) {
                case R.id.directional_iv_state:
                    mOnItemClickListener.jumpSelectCar((int) v.getTag());
                    break;
                case R.id.receive_details:
                    mOnItemClickListener.jumpReceiveBillDetails((int) v.getTag());

                    break;
                case R.id.relay:
                    mOnItemClickListener.jumpRelay((int) v.getTag());
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
        /**
         * 跳转到订单详情界面
         *
         * @param position 点击的位置
         */
        void jumpDetails(int position);

        /**
         * 跳转到选择车辆界面
         *
         * @param position 点击的位置
         */
        void jumpSelectCar(int position);

        /**
         * 跳转到接单详情界面
         *
         * @param position 点击的位置
         */
        void jumpReceiveBillDetails(int position);

        /**
         * 跳转到一键转发界面
         *
         * @param position 点击的位置
         */
        void jumpRelay(int position);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.bill_number)
        TextView billNumber;
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
        @BindView(R.id.directional_tv_productName)
        TextView directionalTvProductName;
        @BindView(R.id.directional_distance)
        TextView directionalDistance;
        @BindView(R.id.receive_details)
        TextView receiveDetails;
        @BindView(R.id.relay)
        TextView relay;
        @BindView(R.id.directional_item_space)
        View directionalItemSpace;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
