package com.example.personal_finance_tracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.personal_finance_tracker.DB.AppDataBase;
import com.example.personal_finance_tracker.DB.FinanceTrackerDAO;
import com.example.personal_finance_tracker.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String PREFERENCES_KEY = "com.example.personal_finance_tracker.PREFERENCES_KEY";
    private static final String USER_EMAIL_KEY = "com.example.personal_finance_tracker.USER_EMAIL_KEY";
    private static final String USER_ID_KEY = "com.example.personal_finance_tracker.USER_ID_KEY";
    ActivityMainBinding binding;
    private Button loginButton;

    private EditText emailField;
    private EditText passwordField;
    private String userEmail;
    private String userPassword;
    private User user;
    private int userID = -1;

    FinanceTrackerDAO financeTrackerDAO;

    List<FinanceTrackerUser> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
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
                        Toast.makeText(MainActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                    }else{
                        Intent intent = DashboardActivity.intentFactory(getApplicationContext(), user.getUserID());
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
        user = financeTrackerDAO.getUser(userEmail);
        if(user == null) {
            Toast.makeText(this, "No account associated with this email address.", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private void getDatabase() {
        financeTrackerDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .financeTrackerDAO();
    }

    private void checkForUser() {
        userID = getIntent().getIntExtra(USER_ID_KEY, -1);

        if(userID != -1) {
            //we have a user
            return;
        }

        //do we have a user in the preferences?
        SharedPreferences preferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);

        userID = preferences.getInt(USER_ID_KEY, -1);

        if(userID != -1) {
            //we have a user
            return;
        }


        //do we have any users at all?
        List<User> users = financeTrackerDAO.getAllUsers();
        if(users.size() <= 0) {
            User defaultUser = new User("default", "password");
            financeTrackerDAO.insert(defaultUser);
        }

    }

    public static Intent intentFactory(Context context, int userID){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(USER_ID_KEY, userID);
        return intent;
    }

}