package com.example.personal_finance_tracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.personal_finance_tracker.databinding.ActivityAddExpenseBinding;

public class AddExpenseActivity extends AppCompatActivity {

    ActivityAddExpenseBinding binding;

    String description = "";
    double amount = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddExpenseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.AddExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);

                startActivity(intent);
            }
        });
    }

    private void getExpenseInfoFromDisplay(){
        description = binding.TransactionNameTitleTextView.getText().toString();

        try{
            amount = Double.parseDouble(binding.TransactionAmountEditTextView.getText().toString());
        } catch (NumberFormatException e){
            Log.d("DAC EXPENSE", "Error reading expense amount from Amount text view.");
        }
    }
}