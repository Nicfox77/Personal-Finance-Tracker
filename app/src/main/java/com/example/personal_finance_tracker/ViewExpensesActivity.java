package com.example.personal_finance_tracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personal_finance_tracker.DB.entities.ExpenseLog;
import com.example.personal_finance_tracker.viewHolders.ExpenseLogViewModel;

public class ViewExpensesActivity extends AppCompatActivity {

    private ExpenseLogViewModel expenseViewModel;


    public static final int NEW_EXPENSE_ACTIVITY_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_expenses);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final ExpenseListAdapter adapter = new ExpenseListAdapter(new ExpenseListAdapter.ExpenseDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        expenseViewModel = new ViewModelProvider(this).get(ExpenseLogViewModel.class);

        expenseViewModel.getAllExpenses().observe(this, expenseLogs -> {
            adapter.submitList(expenseLogs);
        });

    }

}