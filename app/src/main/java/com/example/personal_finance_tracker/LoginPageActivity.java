package com.example.personal_finance_tracker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.CountedCompleter;



public class LoginPageActivity extends AppCompatActivity {
    ActivityLoginPageactivity binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setContentView(R.layout.activity_main);

    }


}
