package com.example.personal_finance_tracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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


    FinanceTrackerDAO financeTrackerDAO;

    List<FinanceTrackerUser> users;

    private int userID = -1;

    /**
     * This method is used to create the MainActivity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        wireupDisplay();
        getDatabase();
        createAdmin();
        checkForUser();

        dashboardTitle.setText(String.format("Welcome %s", financeTrackerDAO.getUserLoginById(userID).getUsername()));
        if(financeTrackerDAO.getUserLoginById(userID).isAdmin()){
            addUserButton.setVisibility(View.VISIBLE);
            deleteUserButton.setVisibility(View.VISIBLE);
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
    }

    /**
     * This method is used to check if a user is logged in
     * If a user is not logged in, it will redirect to the login page
     * If a user is logged in, it will set the userID to the user's ID
     */
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
        List<User> users = financeTrackerDAO.getAllUsernames();
        if(users.size() <= 1) {
            User defaultUser = new User("default", "default", "password");
            financeTrackerDAO.insert(defaultUser);
        }

        Intent intent = LoginPageActivity.intentFactory(this);
        startActivity(intent);

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