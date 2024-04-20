package com.example.personal_finance_tracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.personal_finance_tracker.DB.AppDataBase;
import com.example.personal_finance_tracker.DB.FinanceTrackerDAO;
import com.example.personal_finance_tracker.databinding.ActivityLoginPageBinding;

import java.util.List;

public class LoginPageActivity extends AppCompatActivity {

    ActivityLoginPageBinding binding;
    private Button loginButton;
    private Button addUserButton;
    private EditText usernameField;
    private EditText passwordField;

    private String username;
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

    /**
     * This method is used to wire up the display
     */
    private void wireupDisplay(){
        loginButton = binding.LoginButton;
        addUserButton = binding.SignUpButton;
        usernameField = binding.editTextUsername;
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

        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateNewUserActivity.class);
                startActivity(intent);
            }
        });

    }

    /**
     * This method validates the entered password
     * @return boolean
     */
    private boolean validatePassword() {
        return user.getPassword().equals(userPassword);
    }

    /**
     * This method is used to get the values from the display
     */
    private void getValuesFromDisplay() {
        username = usernameField.getText().toString();
        userPassword = passwordField.getText().toString();
    }

    /**
     * This method is used to check if the user is in the database
     * @return boolean
     */
    private boolean checkForUserInDatabase() {
        user = financeTrackerDAO.getUserByUsername(username);
        if(user == null) {
            Toast.makeText(this, "No account associated with this username.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * This method is used to get the database
     */
    private void getDatabase() {
        financeTrackerDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .financeTrackerDAO();
    }


    /**
     * This method is used to create an intent to start the LoginPageActivity
     * @param context
     * @return Intent
     */
    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, LoginPageActivity.class);
        return intent;
    }

}