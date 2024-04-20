package com.example.personal_finance_tracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.personal_finance_tracker.DB.ExpenseLogRepository;
import com.example.personal_finance_tracker.DB.FinanceTrackerDAO;
import com.example.personal_finance_tracker.databinding.ActivityAddBudgetBinding;

public class AddBudgetActivity extends AppCompatActivity {

    ActivityAddBudgetBinding binding;

    private ExpenseLogRepository repository;
    int loggedInUserId;
    int newBudget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBudgetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = new ExpenseLogRepository(getApplication());

        Intent intent = getIntent();
        loggedInUserId = intent.getIntExtra("ID", -2);


        binding.UpdateBudgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUpdatedBudgetFromDisplay();
                updateUserBudget();
                finish();
            }
        });

        binding.ReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repository.updateUserBudget(loggedInUserId, newBudget);
            }
        });
    }

    private void getUpdatedBudgetFromDisplay() {
        try {
            newBudget = Integer.parseInt(binding.BudgetAmountEditTextView.getText().toString());
        } catch (NumberFormatException e){
            Log.d("DAC EXPENSE", "Error reading new budget amount from budget text view.");
        }
    }

    private void updateUserBudget() {

    }
}