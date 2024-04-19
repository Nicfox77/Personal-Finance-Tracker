package com.example.personal_finance_tracker.DB;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.personal_finance_tracker.DB.entities.ExpenseLog;

import java.util.ArrayList;
import java.util.List;

public class ExpenseLogRepository {

    private ExpenseLogDAO expenseLogDAO;
    private ArrayList<ExpenseLog> allExpenses;

    public ExpenseLogRepository(Application application) {
        AppDataBase db = AppDataBase.getInstance(application);
        this.expenseLogDAO = db.expenseLogDAO();
        this.allExpenses = (ArrayList<ExpenseLog>) this.expenseLogDAO.getAllRecords();
    }

    public static ExpenseLogRepository getRepository(Application application) {
        //TODO: Need to implement created in Recycler View Video
        return null;
    }

    public LiveData<List<ExpenseLog>> getAllLogsByUserId(int userId) {
        return expenseLogDAO.getRecordsByUserId(loggedInUserId);
    }

    public void insertExpenseLog(ExpenseLog expense) {
        //TODO: Need to implement created in Recycler View Video
    }
}
