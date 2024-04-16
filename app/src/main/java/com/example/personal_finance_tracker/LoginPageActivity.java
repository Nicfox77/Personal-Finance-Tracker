package com.example.personal_finance_tracker;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import com.example.personal_finance_tracker.DB.AppDataBase;
import com.example.personal_finance_tracker.DB.FinanceTrackerDAO;
import com.example.personal_finance_tracker.databinding.ActivityLoginPageBinding;
import com.example.personal_finance_tracker.databinding.ActivityMainBinding;

import java.util.List;

public class LoginPageActivity extends AppCompatActivity {
    ActivityLoginPageBinding binding;
    EditText email;
    EditText password;

    Button login;

    FinanceTrackerDAO financeTrackerDAO;

    List<FinanceTrackerUser> users;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        binding = ActivityLoginPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        email = binding.editTextEmailAddress;
        password = binding.editTextPassword;
        login = binding.LoginButton;

        financeTrackerDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .financeTrackerDAO();
    }

}