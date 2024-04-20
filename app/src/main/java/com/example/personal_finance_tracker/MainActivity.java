package com.example.personal_finance_tracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.personal_finance_tracker.DB.AppDataBase;
import com.example.personal_finance_tracker.DB.FinanceTrackerDAO;
import com.example.personal_finance_tracker.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.personal_finance_tracker.USER_ID_KEY";
    private static final String PREFERENCES_KEY = "com.example.personal_finance_tracker.PREFERENCES_KEY";


    private ActivityMainBinding binding;
    private Button addBudgetButton;
    private Button viewExpensesButton;
    private Button addExpenseButton;
    private TextView totalExpenses;
    private TextView totalBudget;
    private TextView remainingBudget;
    private TextView dashboardTitle;
    private Button addUserButton;
    private Button deleteUserButton;
    private Button logoutButton;


    private FinanceTrackerDAO financeTrackerDAO;

    private List<FinanceTrackerUser> users;
    private SharedPreferences prefs = null;
    private int userID = -1;
    private User user;

    /**
     * This method is used to create the MainActivity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        getDatabase();
        createAdmin();
        checkForUser();
        addUserToPreferences(userID);
        loginUser(userID);
        System.out.println("User ID: " + userID);
        wireupDisplay();

        try {
            if(financeTrackerDAO.getUserLoginById(userID).isAdmin()){
                addUserButton.setVisibility(View.VISIBLE);
                deleteUserButton.setVisibility(View.VISIBLE);
            }
        }
        catch (Exception e){
            System.out.println("No User Logged in");
        }

        addExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddExpenseActivity.class);
                startActivity(intent);
            }
        });

        addBudgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddBudgetActivity.class);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

    }

    private void loginUser(int userID) {
        user = financeTrackerDAO.getUserLoginById(userID);
    }


    private void logoutUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(R.string.are_you_sure_you_want_to_logout);

        builder.setPositiveButton(getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clearUserFromIntent();
                        clearUserFromPreferences();
                        userID = -1;
                        checkForUser();
                    }
                });
        builder.setNegativeButton(getString(R.string.no),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do nothing
                    }
                });
        builder.create().show();
    }


    private void clearUserFromIntent() {
        getIntent().putExtra(USER_ID_KEY, -1);
    }

    private void clearUserFromPreferences() {
        addUserToPreferences(-1);
    }


    private void addUserToPreferences(int userID) {
        if(prefs == null){
            getPrefs();
        }
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(USER_ID_KEY, userID);
    }

    /**
     * This method is used to wire up the display
     * It sets the buttons and text views to their respective views
     */
    private void wireupDisplay(){
        addBudgetButton = binding.AddBudgetButton;
        addExpenseButton = binding.NewExpenseButton;
        viewExpensesButton = binding.ViewExpensesButton;
        totalExpenses = binding.ExpensesAmountTextView;
        totalBudget = binding.TotalBudgetAmountTextView;
        remainingBudget = binding.DiffAmountTextView;
        dashboardTitle = binding.DashboardTitleView;
        addUserButton = binding.AddUserAdminButton;
        deleteUserButton = binding.DeleteUserAdminButton;
        logoutButton = binding.logoutButton;
        try{
            dashboardTitle.setText(String.format("Welcome %s", financeTrackerDAO.getUserLoginById(userID).getUsername()));
        }catch (Exception e){
            System.out.println("No User Logged in");
        }
    }

    /**
     * This method is used to check if a user is logged in
     * If a user is not logged in, it will redirect to the login page
     * If a user is logged in, it will set the userID to the user's ID
     */
    private void checkForUser() {
        System.out.println("User ID: " + userID);
        userID = getIntent().getIntExtra(USER_ID_KEY, -1);

        if(userID != -1) {
            //we have a user
            return;
        }


        //do we have a user in the preferences?
        if(prefs == null){
            getPrefs();
        }
        System.out.println("User ID: " + userID);
        userID = prefs.getInt(USER_ID_KEY, -1);
        System.out.println("User ID: " + userID);

        if(userID != -1) {
            //we have a user
            return;
        }

        //no user, redirect to login page
        System.out.println("Should go to Login Page");
        Intent intent = LoginPageActivity.intentFactory(this);
        startActivity(intent);
    }

    private void getPrefs() {
        prefs = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
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
     * This method is used to create a default admin user, if one does not exist already
     *
     */
    private void createAdmin(){
        if(financeTrackerDAO.getUserByUsername("admin") == null){
            User admin = new User("admin", "admin", "password", true);
            financeTrackerDAO.insert(admin);
            System.out.println("Admin user created");
        }
        if(financeTrackerDAO.getUserByUsername("default") == null) {
            User defaultUser = new User("default", "default", "password");
            financeTrackerDAO.insert(defaultUser);
            System.out.println("default user created");
        }
    }


    /**
     * This method is used to create an intent to start the MainActivity
     * @param context
     * @param userID
     * @return
     */
    public static Intent intentFactory(Context context, int userID){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(USER_ID_KEY, userID);
        return intent;
    }

}