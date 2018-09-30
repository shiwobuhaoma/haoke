package com.xinze.haoke.module.goods.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xinze.haoke.R;
import com.xinze.haoke.module.drivers.view.MyDriverActivity;
import com.xinze.haoke.module.goods.bean.Goods;
import com.xinze.haoke.module.goods.view.FrequentlyTransportedGoodsActivity;
import com.xinze.haoke.module.invite.model.TruckownerDriverVO;
import com.xinze.haoke.utils.DialogUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author feibai
 * @date 2018/5/14
 * desc:GoodsRecycleViewAdapter
 */
public class GoodsRecycleViewAdapter extends RecyclerView.Adapter<GoodsRecycleViewAdapter.ViewHolder> {

    private List<Goods> mBS;
    private Context mActivity;

    public GoodsRecycleViewAdapter(Context activity) {
        this.mActivity = activity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(mActivity).inflate(R.layout.goods_rv_item, parent, false);
        return new ViewHolder(mView);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.goodsName.setText(mBS.get(position).getName());
        if (mBS.get(position).isTag()) {
            holder.goodsSelect.setChecked(true);
        } else {
            holder.goodsSelect.setChecked(false);
        }
        if (mBS.get(position).isVisiable()){
            holder.goodsSelect.setVisibility(View.VISIBLE);
        }else {
            holder.goodsSelect.setVisibility(View.GONE);
        }
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mBS == null ? 0 : mBS.size();
    }

    public void setData(List<Goods> data) {
        this.mBS = data;
        notifyDataSetChanged();
    }
    public void visiableAll() {
        for (Goods good :mBS) {
            good.setVisiable(true);
        }
        notifyDataSetChanged();
    }

    public void unvisiableAll() {
        for (Goods good :mBS) {
            good.setVisiable(false);
        }
        notifyDataSetChanged();
    }

    public void selectAll() {
        for (Goods good :mBS) {
            good.setTag(true);
        }
        notifyDataSetChanged();
    }

    public void unSelectAll() {
        for (Goods good :mBS) {
            good.setTag(false);
        }
        notifyDataSetChanged();
    }

    public void deleteItem(int mPosition) {
        mBS.remove(mPosition);
        notifyItemChanged(mPosition);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.goods_name)
        TextView goodsName;
        @BindView(R.id.goods_select)
        CheckBox goodsSelect;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView.setOnClickListener(this);

        }

         @Override
         public void onClick(View v) {
             mOnItemViewClick.itemClick((int)itemView.getTag());
         }
     }

  public   interface OnItemViewClick{
        void itemClick(int position);
    }

    public void setOnItemViewClick(OnItemViewClick onItemViewClick){
        this.mOnItemViewClick = onItemViewClick;
    }
    private OnItemViewClick mOnItemViewClick;
}
