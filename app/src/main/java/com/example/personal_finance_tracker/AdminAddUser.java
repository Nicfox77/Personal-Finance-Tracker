package com.example.personal_finance_tracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import com.example.personal_finance_tracker.DB.AppDataBase;
import com.example.personal_finance_tracker.DB.FinanceTrackerDAO;
import com.example.personal_finance_tracker.databinding.ActivityAdminAddUserBinding;
import com.example.personal_finance_tracker.databinding.ActivityMainBinding;



public class AdminAddUser extends AppCompatActivity {
    private ActivityAdminAddUserBinding binding;
    private FinanceTrackerDAO financeTrackerDAO;


    private EditText emailField;
    private EditText passwordField;
    private EditText confirmPasswordField;
    private EditText usernameField;
    private Button submitButton;
    private Button returnToDashboardButton;

    private String userEmail;
    private String userPassword;
    private String username;
    private String confirmPassword;
    private User user;
    private int userID = -1;
    private int budget = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminAddUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Intent intent = getIntent();
        userID = intent.getIntExtra("ID", -2);
        wireupDisplay();
        getDatabase();


    }

    private void wireupDisplay(){
        returnToDashboardButton = binding.ReturnToDashboardButton;
        submitButton = binding.CreateUserButton;
        emailField = binding.NewEmailEditTextView;
        passwordField = binding.NewPasswordEditTextView;
        confirmPasswordField = binding.ConfirmPasswordEditTextView;
        usernameField = binding.UserNameEditTextView;

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminAddUser.this);
                getValuesFromDisplay();
                if(checkForUserInDatabase()){
                    if(checkUniqueUsername()) {
                        if (!validatePassword()) {
                            Toast.makeText(AdminAddUser.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = MainActivity.intentFactory(getApplicationContext(), userID);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(AdminAddUser.this, "Username already taken!", Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(AdminAddUser.this, "Email address already taken!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        returnToDashboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.intentFactory(getApplicationContext(), userID);
                startActivity(intent);
            }
        });

    }


    private void getDatabase() {
        financeTrackerDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .financeTrackerDAO();
    }

    private boolean checkUniqueUsername() {
        return financeTrackerDAO.getUserByUsername(username) == null;
    }

    private boolean validatePassword() {
        if (userPassword.equals(confirmPassword)) {
            user = new User(userEmail, username, userPassword, budget);
            financeTrackerDAO.insert(user);
            user = financeTrackerDAO.getUserByUsername(username);
            System.out.println("User ID when validating password: " + user.getUserID());
            return true;
        }
        return false;
    }

    private boolean checkForUserInDatabase() {
        user = financeTrackerDAO.getUserByEmail(userEmail);
        return user == null;
    }

    private void getValuesFromDisplay() {
        userEmail = emailField.getText().toString();
        userPassword = passwordField.getText().toString();
        username = usernameField.getText().toString();
        confirmPassword = confirmPasswordField.getText().toString();
    }

}