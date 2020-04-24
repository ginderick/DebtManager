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

import java.util.ArrayList;
import java.util.List;

public class DebtListAdapter extends RecyclerView.Adapter<DebtListAdapter.DebtHolder> {

    public static final String TAG = DebtListAdapter.class.getSimpleName();

    private List<DebtInfo> debts = new ArrayList<>();
    private OnClickListener onClickListener;

    public DebtListAdapter(List<DebtInfo> debts, OnClickListener onClickListener) {
        this.debts = debts;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public DebtHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new DebtHolder(itemView, onClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DebtListAdapter.DebtHolder holder, int position) {
        DebtInfo currentDebtInfo = debts.get(position);
        holder.textViewName.setText(currentDebtInfo.getName());
        holder.textViewAmount.setText(String.valueOf(currentDebtInfo.getDebtAmount()));
    }

    @Override
    public int getItemCount() {
        return debts.size();
    }

    public DebtInfo getItemAtPosition(int position) {
        return debts.get(position);
    }

    public interface OnClickListener {
        void onDeleteButtonClicked(DebtInfo debtInfo);

        void onEditClicked(int position);
    }


    class DebtHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textViewName;
        private TextView textViewAmount;
        private ImageView deleteImageView;
        private OnClickListener mOnClickListener;

        public DebtHolder(@NonNull View itemView, OnClickListener onClickListener) {
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
                        Log.d(TAG, "onClick: delete_image_view Clicked");
                    }
                    break;
                default:
                    Log.d(TAG, "onClick: default clicked");
                    break;
            }

        }
    }
}