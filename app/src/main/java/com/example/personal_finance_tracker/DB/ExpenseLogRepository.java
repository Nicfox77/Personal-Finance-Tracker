package com.example.personal_finance_tracker.DB;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.personal_finance_tracker.DB.entities.ExpenseLog;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ExpenseLogRepository {

    private ExpenseLogDAO expenseLogDAO;
    private ArrayList<ExpenseLog> allExpenses;

    public ExpenseLogRepository(Application application) {
        AppDataBase db = AppDataBase.getDatabase(application);
        this.expenseLogDAO = db.expenseLogDAO();
        this.allExpenses = (ArrayList<ExpenseLog>) this.expenseLogDAO.getAllRecords();
    }

    public ArrayList<ExpenseLog> getAllExpenses() {
        return (ArrayList<ExpenseLog>) expenseLogDAO.getAllRecords();
    }

    public void insertExpenseLog(ExpenseLog expenseLog) {
        AppDataBase.databaseWriteExecutor.execute(() ->
        {
            expenseLogDAO.insert(expenseLog);
        });
    }

}
