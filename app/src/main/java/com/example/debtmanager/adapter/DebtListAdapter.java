package com.example.debtmanager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.debtmanager.R;
import com.example.debtmanager.model.DebtInfo;

import java.util.ArrayList;
import java.util.List;

public class DebtListAdapter extends RecyclerView.Adapter<DebtListAdapter.DebtHolder> {

    private List<DebtInfo> debts = new ArrayList<>();
    private OnDeleteButtonClickListener onDeleteButtonClickListener;

    public DebtListAdapter(List<DebtInfo> debts, OnDeleteButtonClickListener onDeleteButtonClickListener) {
        this.debts = debts;
        this.onDeleteButtonClickListener = onDeleteButtonClickListener;
    }

    @NonNull
    @Override
    public DebtHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new DebtHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DebtListAdapter.DebtHolder holder, int position) {
        DebtInfo currentDebtInfo = debts.get(position);
        holder.textViewName.setText(currentDebtInfo.getName());
        holder.textViewAmount.setText(String.valueOf(currentDebtInfo.getDebtAmount()));
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDeleteButtonClickListener != null ) {
                        onDeleteButtonClickListener.onDeleteButtonClicked(currentDebtInfo);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return debts.size();
    }

    public DebtInfo getItemAtPosition(int position) {
        return debts.get(position);
    }

    public interface OnDeleteButtonClickListener {
        void onDeleteButtonClicked(DebtInfo debtInfo);
    }


    class DebtHolder extends RecyclerView.ViewHolder {

        private TextView textViewName;
        private TextView textViewAmount;
        private Button deleteButton;

        public DebtHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewAmount = itemView.findViewById(R.id.textViewAmount);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }


    }
}