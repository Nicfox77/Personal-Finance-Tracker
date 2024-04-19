package com.example.personal_finance_tracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.personal_finance_tracker.DB.ExpenseLogRepository;
import com.example.personal_finance_tracker.DB.entities.ExpenseLog;
import com.example.personal_finance_tracker.databinding.ActivityAddExpenseBinding;

public class AddExpenseActivity extends AppCompatActivity {

    private ActivityAddExpenseBinding binding;
    private ExpenseLogRepository repository;

    private FinanceTrackerUser user;

    String description = "";
    int amount = 0;

    int loggedInUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddExpenseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = new ExpenseLogRepository(getApplication());

        Intent intent = getIntent();
        loggedInUserId = intent.getIntExtra("ID", -2);

        binding.AddExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getExpenseInfoFromDisplay();
                insertExpenseInfoRecord();
                finish();
            }
        });
    }

    private void insertExpenseInfoRecord() {
        ExpenseLog expense = new ExpenseLog(description, amount, loggedInUserId);
        repository.insertExpenseLog(expense);
    }

    private void getExpenseInfoFromDisplay(){
        description = binding.TransactionNameEditTextView.getText().toString();

        try{
            amount = Integer.parseInt(binding.TransactionAmountEditTextView.getText().toString());
        } catch (NumberFormatException e){
            Log.d("DAC EXPENSE", "Error reading expense amount from Amount text view.");
        }
    }

}