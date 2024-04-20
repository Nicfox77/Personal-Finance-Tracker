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
import com.example.personal_finance_tracker.databinding.ActivityAdminDeleteUserBinding;

public class AdminDeleteUser extends AppCompatActivity {
    private ActivityAdminDeleteUserBinding binding;
    private FinanceTrackerDAO financeTrackerDAO;


    private EditText emailField;
    private EditText usernameField;

    private Button deleteButton;
    private Button returnToDashboardButton;

    private String userEmail;
    private String username;
    private User user;
    private int userID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.example.personal_finance_tracker.databinding.ActivityAdminDeleteUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Intent intent = getIntent();
        userID = intent.getIntExtra("ID", -2);
        wireupDisplay();
        getDatabase();

    }

    private void wireupDisplay(){
        returnToDashboardButton = binding.ReturnToDashboardButton;
        deleteButton = binding.DeleteUserButton;
        emailField = binding.NewEmailEditTextView;
        usernameField = binding.UserNameEditTextView;

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminDeleteUser.this);
                getValuesFromDisplay();
                if(checkForUserEmailInDatabase()){
                    builder.setMessage("Confirm Delete?").setTitle("User found.");
                    builder.setPositiveButton("OK", (dialog, id) -> {
                        financeTrackerDAO.delete(user);
                        Toast.makeText(AdminDeleteUser.this, "User deleted!", Toast.LENGTH_SHORT).show();
                    });
                    builder.setNegativeButton("Cancel", (dialog, id) -> {
                        // User cancelled the dialog
                        dialog.cancel();
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else{
                    if(checkForUsernameInDatabase()){
                        builder.setMessage("Confirm Delete?").setTitle("User found.");
                        builder.setPositiveButton("OK", (dialog, id) -> {
                            financeTrackerDAO.delete(user);
                            Toast.makeText(AdminDeleteUser.this, "User deleted!", Toast.LENGTH_SHORT).show();
                        });
                        builder.setNegativeButton("Cancel", (dialog, id) -> {
                            // User cancelled the dialog
                            dialog.cancel();
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else{
                        Toast.makeText(AdminDeleteUser.this, "No User Found", Toast.LENGTH_SHORT).show();
                    }
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

    private void getValuesFromDisplay() {
        userEmail = emailField.getText().toString();
        username = usernameField.getText().toString();
    }

    private boolean checkForUserEmailInDatabase() {
        user = financeTrackerDAO.getUserByEmail(userEmail);
        return user != null;
    }

    private boolean checkForUsernameInDatabase() {
        user = financeTrackerDAO.getUserByUsername(username);
        return user != null;
    }
}