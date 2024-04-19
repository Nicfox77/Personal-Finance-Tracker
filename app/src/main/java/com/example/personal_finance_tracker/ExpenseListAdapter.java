package com.example.personal_finance_tracker;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.personal_finance_tracker.DB.entities.ExpenseLog;

public class ExpenseListAdapter extends ListAdapter<ExpenseLog, ExpenseViewHolder> {

    public ExpenseListAdapter(@NonNull DiffUtil.ItemCallback<ExpenseLog> diffCallback) {
        super(diffCallback);
    }

    @Override
    public ExpenseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ExpenseViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(ExpenseViewHolder holder, int position) {
        ExpenseLog current = getItem(position);
        holder.bind(current.getName());
    }

    static class ExpenseDiff extends DiffUtil.ItemCallback<ExpenseLog> {
        @Override
        public boolean areItemsTheSame(@NonNull ExpenseLog oldItem, @NonNull ExpenseLog newItem) {
            return oldItem.getName().equals(newItem.getName());
        }

        @Override
        public boolean areContentsTheSame(@NonNull ExpenseLog oldItem, @NonNull ExpenseLog newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    }
}
