package com.xinze.haoke.module.select.carType.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.xinze.haoke.R;
import com.xinze.haoke.module.select.carType.modle.CarType;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author lxf
 * 车辆类型的适配器
 */
public class CarTypeRecycleViewAdapter extends RecyclerView.Adapter<CarTypeRecycleViewAdapter.ViewHolder> {

    private List<CarType> mBS;
    private Context mActivity;

    public CarTypeRecycleViewAdapter(Context activity) {
        this.mActivity = activity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(mActivity).inflate(R.layout.select_car_type_rv_item, parent, false);
        return new ViewHolder(mView);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.goodsName.setText(mBS.get(position).getTruckname());
        holder.goodsSelect.setChecked(mBS.get(position).isSelected());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mBS == null ? 0 : mBS.size();
    }

    public void setData(List<CarType> data) {
        this.mBS = data;
        notifyDataSetChanged();
    }


    public void selected(int mPosition) {
        for (int i = 0; i < mBS.size(); i++) {
            if (mPosition != i) {
                mBS.get(i).setSelected(false);
            } else {
                mBS.get(mPosition).setSelected(!mBS.get(mPosition).isSelected());
            }
        }
        notifyDataSetChanged();
    }


    public void deleteItem(int mPosition) {
        mBS.remove(mPosition);
        notifyItemChanged(mPosition);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.car_type_name)
        TextView goodsName;
        @BindView(R.id.car_type_select)
        CheckBox goodsSelect;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnItemViewClick.itemClick((int) itemView.getTag());
        }
    }

    public interface OnItemViewClick {
        void itemClick(int position);
    }

    public void setOnItemViewClick(OnItemViewClick onItemViewClick) {
        this.mOnItemViewClick = onItemViewClick;
    }

    private OnItemViewClick mOnItemViewClick;
}
