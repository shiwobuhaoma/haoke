package com.xinze.haoke.widget.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xinze.haoke.R;
import com.xinze.haoke.module.allot.view.AllotDriverActivity;
import com.xinze.haoke.widget.bean.Address;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 区域适配器
 *
 * @author lxf
 */
public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    private Context mActivity;
    private List<Address> mData;
    private String mArea;
    private View mView;
    private int black = Color.parseColor("#333333");
    private int gray = Color.parseColor("#f5f5f5");

    public AddressAdapter(Context activity) {
        this.mActivity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        mView = LayoutInflater.from(mActivity).inflate(R.layout.select_address_item, parent, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.itemView.setBackgroundColor(mData.get(position).getBackgroundColor());
        holder.addressItem.setText(mData.get(position).getName());
        holder.addressItem.setTextColor(mData.get(position).getTextColor());
        holder.addressItem.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setData(List<Address> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public void clearData() {
        if (mData != null) {
            mData.clear();
            notifyDataSetChanged();
        }
    }

    public void reset() {
        for (Address address : mData) {
            address.setBackgroundColor(gray);
            address.setTextColor(black);
        }
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.address_item)
        TextView addressItem;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            addressItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            mOnItemClickListener.click(v, (Integer) v.getTag(), mArea);
        }
    }

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener, String address) {
        this.mOnItemClickListener = mOnItemClickListener;
        this.mArea = address;
    }

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void click(View view, int position, String address);
    }
}
