package com.example.debtmanager.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.debtmanager.R;
import com.example.debtmanager.model.DebtInfo;


import java.util.Arrays;
import java.util.List;

public class DebtListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String TAG = DebtListAdapter.class.getSimpleName();

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_ITEM = 1;

    private List<DebtInfo> debtInfoList;
    private OnClickListener onClickListener;

    public DebtListAdapter(List<DebtInfo> debtInfoList, OnClickListener onClickListener) {
        this.debtInfoList = debtInfoList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recyclerview_item, parent, false);
            return new DebtItemViewHolder(itemView, onClickListener);
        } else if (viewType == TYPE_HEADER) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.header_layout, parent, false);
            return new HeaderViewHolder(itemView);
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            headerViewHolder.total_amount_text.setText("TOTAL AMOUNT");
            headerViewHolder.amount_text.setText((Double.toString(calculateTotalDebtAmount(debtInfoList))));



        } else if (holder instanceof DebtItemViewHolder) {
            DebtItemViewHolder debtItemViewHolder = (DebtItemViewHolder) holder;
            DebtInfo currentDebtInfo = debtInfoList.get(position - 1);
            debtItemViewHolder.textViewName.setText(currentDebtInfo.getName());
            debtItemViewHolder.textViewAmount.setText(String.valueOf(currentDebtInfo.getDebtAmount()));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return debtInfoList.size()+1;
    }

    public DebtInfo getItemAtPosition(int position) {
        return debtInfoList.get(position -1);
    }

    public interface OnClickListener {
        void onDeleteButtonClicked(DebtInfo debtInfo);

        void onEditClicked(int position);
    }

    private class DebtItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textViewName;
        private TextView textViewAmount;
        private ImageView deleteImageView;
        private OnClickListener mOnClickListener;

        public DebtItemViewHolder(@NonNull View itemView, OnClickListener onClickListener) {
            super(itemView);

            mOnClickListener = onClickListener;

            textViewName = itemView.findViewById(R.id.textViewName);
            textViewAmount = itemView.findViewById(R.id.textViewAmount);
            deleteImageView = itemView.findViewById(R.id.delete_image_view);
            deleteImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            switch (v.getId()) {
                case R.id.delete_image_view:
                    if (mOnClickListener != null) {
                        mOnClickListener.onDeleteButtonClicked(getItemAtPosition(position));
                    }
                    break;
                default:
                    break;
            }

        }
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {

        TextView total_amount_text, amount_text;

        public HeaderViewHolder(View itemView) {
            super(itemView);

            total_amount_text = itemView.findViewById(R.id.total_amount_text);
            amount_text = itemView.findViewById(R.id.amount_text);

        }
    }

    public double calculateTotalDebtAmount(List<DebtInfo> debtInfoList) {

        double total = 0;

        for (DebtInfo debtInfo : debtInfoList) {
            total += debtInfo.getDebtAmount();
        }
        return total;
    }
}