package com.xinze.haoke.module.select.car.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xinze.haoke.R;
import com.xinze.haoke.module.invite.model.OwnerDriverVO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * @author lxf
 */
public class SelectDriverRecycleAdapter extends RecyclerView.Adapter<SelectDriverRecycleAdapter.ViewHolder> {

    private Context mContext;
    private List<OwnerDriverVO> mData;

    public SelectDriverRecycleAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.select_driver_rv_item, parent, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OwnerDriverVO ownerDriverVO = mData.get(position);
        String driverMobile = ownerDriverVO.getDriverMobile();
        String driverName = ownerDriverVO.getDriverName();
        String driverPhoto = ownerDriverVO.getDriverPhoto();

        holder.selectDriverNameTv.setText(driverName);
        holder.selectDriverPhoneTv.setText(driverMobile);
        Glide.with(mContext).load(driverPhoto).into(holder.selectDriverAvatarIv);
        if (ownerDriverVO.isChecked()) {
            holder.selectDriverCb.setChecked(true);
        } else {
            holder.selectDriverCb.setChecked(false);
        }
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setData(List<OwnerDriverVO> data) {
        this.mData = data;
        notifyDataSetChanged();
    }
    public void selectOne(int position) {
        for (int i = 0; i < mData.size() ; i++){
            if (i == position){
                if (mData.get(i).isChecked()){
                    mData.get(i).setChecked(false);
                }else{
                    mData.get(i).setChecked(true);
                }
            }else{
                mData.get(i).setChecked(false);
            }
        }
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.select_driver_cb)
        CheckBox selectDriverCb;
        @BindView(R.id.select_driver_avatar_iv)
        ImageView selectDriverAvatarIv;
        @BindView(R.id.select_driver_name_tv)
        TextView selectDriverNameTv;
        @BindView(R.id.select_driver_phone_tv)
        TextView selectDriverPhoneTv;
        @BindView(R.id.select_driver_call_iv)
        ImageView selectDriverCallIv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView.setOnClickListener(this);
            int position = (int) itemView.getTag();
            selectDriverCallIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String driverMobile = mData.get(position).getDriverMobile();
                    mContext.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + driverMobile)));
                }
            });
        }

        @Override
        public void onClick(View v) {
            mOnItemViewClick.itemClick((int) itemView.getTag());
        }
    }

    public interface OnItemViewClick {
        /**
         * 条目的点击事件
         * @param position 点击的哪个条目
         */
        void itemClick(int position);
    }

    public void setOnItemViewClick(OnItemViewClick onItemViewClick) {
        this.mOnItemViewClick = onItemViewClick;
    }

    private OnItemViewClick mOnItemViewClick;
}
