package com.example.personal_finance_tracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
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

    private EditText email;
    private EditText password;

    private int userID = -1;

    FinanceTrackerDAO financeTrackerDAO;

    List<FinanceTrackerUser> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getDatabase();

        checkForUser();

        loginButton = binding.LoginButton;

        email = binding.editTextEmailAddress;
        password = binding.editTextPassword;


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitLogin();
            }
        });
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

    private void submitLogin() {
        String email = this.email.getText().toString();
        String password = this.password.getText().toString();


    }

    public static Intent intentFactory(Context context, int userID){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(USER_ID_KEY, userID);
        return intent;
    }

}