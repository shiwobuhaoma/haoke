package com.xinze.haoke.module.receive.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinze.haoke.R;
import com.xinze.haoke.module.receive.ReceiverBill;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReceiverBillAdapter extends RecyclerView.Adapter<ReceiverBillAdapter.ViewHolder> {
    private Context mContext;
    private List<ReceiverBill> mData;

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
        ReceiverBill receiverBill = mData.get(position);

    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setData(List<ReceiverBill> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
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
        }
    }
}
