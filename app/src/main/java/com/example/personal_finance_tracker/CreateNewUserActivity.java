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
import com.example.personal_finance_tracker.databinding.ActivityCreateNewUserBinding;

public class CreateNewUserActivity extends AppCompatActivity {
    ActivityCreateNewUserBinding binding;

    private EditText emailField;
    private EditText passwordField;
    private EditText confirmPasswordField;
    private EditText usernameField;
    private Button submitButton;
    private Button returnToLoginButton;

    private String userEmail;
    private String userPassword;
    private String username;
    private String confirmPassword;
    private User user;
    private int userID = -1;
    private int budget = 0;
    private FinanceTrackerDAO financeTrackerDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateNewUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        wireupDisplay();
        getDatabase();



    }

    private void wireupDisplay(){
        returnToLoginButton = binding.ReturnToLoginButton;
        submitButton = binding.CreateUserButton;
        emailField = binding.NewEmailEditTextView;
        passwordField = binding.NewPasswordEditTextView;
        confirmPasswordField = binding.ConfirmPasswordEditTextView;
        usernameField = binding.UserNameEditTextView;

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CreateNewUserActivity.this);
                getValuesFromDisplay();
                if(checkForUserInDatabase()){
                    if(checkUniqueUsername()) {
                        if (!validatePassword()) {
                            Toast.makeText(CreateNewUserActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                        } else {
                            System.out.println("User ID when created user: " + user.getUserID());
                            Intent intent = MainActivity.intentFactory(getApplicationContext(), user.getUserID());
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(CreateNewUserActivity.this, "Username already taken!", Toast.LENGTH_SHORT).show();
                    }
                } else{
                    alertUser();
                }
            }
        });

        returnToLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = LoginPageActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

    }

    private void alertUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Return to Login screen?").setTitle("User already exists for this email address.");
        builder.setPositiveButton("OK", (dialog, id) -> {
            Intent intent = LoginPageActivity.intentFactory(getApplicationContext());
            startActivity(intent);
        });
        builder.setNegativeButton("Cancel", (dialog, id) -> {
            // User cancelled the dialog
            dialog.cancel();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
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

    private void getDatabase() {
        financeTrackerDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .financeTrackerDAO();
    }

    private boolean checkUniqueUsername() {
        return financeTrackerDAO.getUserByUsername(username) == null;
    }
}

