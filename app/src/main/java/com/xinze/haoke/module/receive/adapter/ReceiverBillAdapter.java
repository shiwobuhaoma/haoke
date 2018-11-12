package com.xinze.haoke.module.receive.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinze.haoke.R;
import com.xinze.haoke.module.receive.modle.ReceiverBill;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReceiverBillAdapter extends RecyclerView.Adapter<ReceiverBillAdapter.ViewHolder> {
    private Context mContext;
    private List<ReceiverBill.WaybillOrderEntitiesBean> mData;
    /**
     * 已接单
     */
    public final String TAKE_ORDER = "0";
    /**
     * 取货中，送货中
     */
    public final String PICK_UP = "1";
    /**
     * 发货中
     */
    public final String DELIVER_GOODS = "2";
    /**
     * 已到货
     */
    public final String GOODS_ARRIVE = "3";
    /**
     * 已签收
     */
    public final String GOODS_SIGNED_IN = "4";
    /**
     * 已拒绝
     */
    public final String GOODS_REFUSE = "R";
    /**
     * 已撤单
     */
    public final String GOODS_REVOKE = "B";
    /**
     * 已过期
     */
    private final String GOODS_OVERDUE = "X";
    /**
     * 已确定
     */
    public final String GOODS_CONFIRM = "C";
    public ReceiverBillAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.receiver_bill_rv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReceiverBill.WaybillOrderEntitiesBean waybillOrderEntitiesBean = mData.get(position);
        String id =waybillOrderEntitiesBean.getIdX();
        String bn = mContext.getResources().getString(R.string.bill_number);
        id = getString(bn, id);
        holder.receiverBillNumber.setText(id);

        String truckOwnerName = waybillOrderEntitiesBean.getTruckownernameX();
        String rp = mContext.getResources().getString(R.string.receiver_people);
        truckOwnerName = getString(rp, truckOwnerName);
        holder.receiverPeople.setText(truckOwnerName);

        String truckNumber = waybillOrderEntitiesBean.getTrucknumberX();
        String cn = mContext.getResources().getString(R.string.carNumber);
        truckNumber = getString(cn, truckNumber);
        holder.carNumber.setText(truckNumber);

        if (TAKE_ORDER.equals(waybillOrderEntitiesBean.getOrderStatusX())){
            Drawable drawable = mContext.getResources().getDrawable(R.mipmap.goods_robbing_order);
            holder.receiveBillState.setBackground(drawable);
        }else if(DELIVER_GOODS.equals(waybillOrderEntitiesBean.getOrderStatusX())){
            Drawable drawable = mContext.getResources().getDrawable(R.mipmap.goods_deliver);
            holder.receiveBillState.setBackground(drawable);
        }else if(PICK_UP.equals(waybillOrderEntitiesBean.getOrderStatusX())){
            Drawable drawable = mContext.getResources().getDrawable(R.mipmap.goods_pick_up);
            holder.receiveBillState.setBackground(drawable);
        }else if(GOODS_ARRIVE.equals(waybillOrderEntitiesBean.getOrderStatusX())){
            Drawable drawable = mContext.getResources().getDrawable(R.mipmap.goods_arrive);
            holder.receiveBillState.setBackground(drawable);
        }else if(GOODS_SIGNED_IN.equals(waybillOrderEntitiesBean.getOrderStatusX())){
            Drawable drawable = mContext.getResources().getDrawable(R.mipmap.goods_signed_in);
            holder.receiveBillState.setBackground(drawable);
        }else if(GOODS_REVOKE.equals(waybillOrderEntitiesBean.getOrderStatusX())){
            Drawable drawable = mContext.getResources().getDrawable(R.mipmap.goods_revoke);
            holder.receiveBillState.setBackground(drawable);
        }else if(GOODS_REFUSE.equals(waybillOrderEntitiesBean.getOrderStatusX())){
            Drawable drawable = mContext.getResources().getDrawable(R.mipmap.goods_refuse);
            holder.receiveBillState.setBackground(drawable);
        }else if(GOODS_OVERDUE.equals(waybillOrderEntitiesBean.getOrderStatusX())){
            Drawable drawable = mContext.getResources().getDrawable(R.mipmap.goods_overdue);
            holder.receiveBillState.setBackground(drawable);
        }

        holder.itemView.setTag(position);

    }

    private String getString(String s1,String s2){
        return String.format(s1,s2);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setData(List<ReceiverBill.WaybillOrderEntitiesBean> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.receiver_bill_number)
        TextView receiverBillNumber;
        @BindView(R.id.receiver_people)
        TextView receiverPeople;
        @BindView(R.id.car_number)
        TextView carNumber;
        @BindView(R.id.receive_bill_state2)
        ImageView receiveBillState;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (itemOnClickListener != null){
                int position = (int) v.getTag();
                itemOnClickListener.itemOnClick(position);
            }
        }
    }
    private ItemOnClickListener itemOnClickListener;

   public interface ItemOnClickListener{
        /**
         * 条目事件点击
         * @param position 点击的哪个条目
         */
        void itemOnClick(int position);
    }

    public void setOnItemClickListener(ItemOnClickListener mItemOnClickListener){
        itemOnClickListener = mItemOnClickListener;
    }
}
