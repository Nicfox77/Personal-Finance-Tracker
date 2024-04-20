package com.example.personal_finance_tracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ExpenseViewHolder extends RecyclerView.ViewHolder {
    private final TextView expenseItemView;

    private ExpenseViewHolder(View itemView) {
        super(itemView);
        expenseItemView = itemView.findViewById(R.id.textView);
    }

    public void bind(String text) {
        expenseItemView.setText(text);
    }

    /**
     *  This method is used to create a new ExpenseViewHolder
     * @param parent
     * @return
     */
    static ExpenseViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new ExpenseViewHolder(view);
    }
}
