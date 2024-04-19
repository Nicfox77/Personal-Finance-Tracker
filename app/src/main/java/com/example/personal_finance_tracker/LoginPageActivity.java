package com.example.personal_finance_tracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    private Button loginButton;
    private EditText emailField;
    private EditText passwordField;

    private String userEmail;
    private String userPassword;
    private User user;
    private int userID = -1;

    private FinanceTrackerDAO financeTrackerDAO;

    List<FinanceTrackerUser> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        wireupDisplay();
        getDatabase();

    }

    private void wireupDisplay(){
        loginButton = binding.LoginButton;

        emailField = binding.editTextEmailAddress;
        passwordField = binding.editTextPassword;

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValuesFromDisplay();
                if(checkForUserInDatabase()){
                    if(!validatePassword()){
                        Toast.makeText(LoginPageActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                    }else{
                        Intent intent = MainActivity.intentFactory(getApplicationContext(), user.getUserID());
                        startActivity(intent);
                    }
                }
            }
        });

    }

    private boolean validatePassword() {
        return user.getPassword().equals(userPassword);
    }

    private void getValuesFromDisplay() {
        userEmail = emailField.getText().toString();
        userPassword = passwordField.getText().toString();
    }

    private boolean checkForUserInDatabase() {
        user = financeTrackerDAO.getUserByEmail(userEmail);
        if(user == null) {
            Toast.makeText(this, "No account associated with this email address.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void getDatabase() {
        financeTrackerDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .financeTrackerDAO();
    }


    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, LoginPageActivity.class);
        return intent;
    }

}