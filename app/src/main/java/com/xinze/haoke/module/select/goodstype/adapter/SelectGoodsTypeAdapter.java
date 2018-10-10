package com.xinze.haoke.module.select.goodstype.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xinze.haoke.R;
import com.xinze.haoke.module.goods.bean.Goods;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 选择货品适配器
 * @author lxf
 */
public class SelectGoodsTypeAdapter extends RecyclerView.Adapter<SelectGoodsTypeAdapter.ViewHolder> {
    
    private Context mActivity;
    private List<Goods> mData;

    public SelectGoodsTypeAdapter(Context activity) {
        this.mActivity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(mActivity).inflate(R.layout.select_goods_type_rv_item, parent, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Goods goods = mData.get(position);
        String cargoName = goods.getCargoName();
        holder.selectGoodsTypeName.setText(cargoName);
        holder.itemView.setTag(position);
        if (mData.get(position).isVisiable()){
            holder.selectGoodsTypeName.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_orange_button));
        }else{
            holder.selectGoodsTypeName.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_gray_button));
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void selectOne(int mPosition) {
        for (int i = 0; i < mData.size(); i++) {
            if (mPosition != i) {
                mData.get(i).setVisiable(false);
            } else {
                mData.get(i).setVisiable(true);
            }
        }
        notifyDataSetChanged();
    }

    public void setData(List<Goods> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.select_goods_type_name)
        TextView selectGoodsTypeName;

        ViewHolder(View view) {
            super(view);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onClick(View v) {
            mOnItemViewClick.itemClick((Integer) v.getTag());
        }
    }

    public interface OnItemViewClick {
        /**
         * 条目点击事件
         * @param position 点击的位置
         */
        void itemClick(int position);
    }

    public void setOnItemViewClick(OnItemViewClick onItemViewClick) {
        this.mOnItemViewClick = onItemViewClick;
    }

    private OnItemViewClick mOnItemViewClick;
}
