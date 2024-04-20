package com.example.personal_finance_tracker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personal_finance_tracker.DB.ExpenseLogRepository;
import com.example.personal_finance_tracker.DB.entities.ExpenseLog;
import com.example.personal_finance_tracker.databinding.ActivityViewExpensesBinding;
import com.example.personal_finance_tracker.viewHolders.ExpenseLogViewModel;

import java.util.ArrayList;
import java.util.List;

public class ViewExpensesActivity extends AppCompatActivity {

    ActivityViewExpensesBinding binding;
    private int loggedInUserId;

    private ExpenseLogRepository repository;

    public static final int NEW_EXPENSE_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewExpensesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        loggedInUserId = intent.getIntExtra("ID", -1);

        repository = new ExpenseLogRepository(getApplication());

        List<ExpenseLog> allUserExpenses = repository.getAllExpensesByUserId(loggedInUserId);
        if (allUserExpenses.isEmpty()) {
            binding.ExpenseListTextView.setText("Nothing to show, please enter expenses.");
        }
        StringBuilder sb = new StringBuilder();
        for(ExpenseLog log: allUserExpenses) {
            String formattedExpense = "$" + log.getAmount() + " -  " + log.getName() + "\n";
            sb.append(formattedExpense);
        }

        binding.ExpenseListTextView.setText(sb.toString());


        binding.DoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}