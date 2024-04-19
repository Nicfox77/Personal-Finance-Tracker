package com.example.personal_finance_tracker.DB;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.personal_finance_tracker.DB.entities.ExpenseLog;

import java.util.ArrayList;
import java.util.List;

public class ExpenseLogRepository {

    private FinanceTrackerDAO financeTrackerDAO;
    private LiveData<List<ExpenseLog>> allExpenses;

    public ExpenseLogRepository(Application application) {
        AppDataBase db = AppDataBase.getInstance(application);
        this.financeTrackerDAO = db.financeTrackerDAO();
        this.allExpenses = financeTrackerDAO.getAllRecords();
    }

    public LiveData<List<ExpenseLog>> getAllExpensesByUserId(int loggedInUserId) {
        return financeTrackerDAO.getRecordSetUserId(loggedInUserId);
    }

    public void insertExpenseLog(ExpenseLog expense) {
        AppDataBase.databaseWriteExecutor.execute(() -> {
            financeTrackerDAO.insert(expense);
        });
        //TODO: Need to implement created in Recycler View Video
    }

    public static ExpenseLogRepository getRepository(Application application) {
        //TODO: Need to implement created in Recycler View Video
        return null;
    }

//    public LiveData<List<ExpenseLog>> getAllLogsByUserId(int userId) {
//        return expenseLogDAO.getRecordsByUserId(loggedInUserId);
//    }


}
