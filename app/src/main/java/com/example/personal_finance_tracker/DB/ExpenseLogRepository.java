package com.example.personal_finance_tracker.DB;

import android.app.Application;

import com.example.personal_finance_tracker.DB.entities.ExpenseLog;

import java.util.ArrayList;

public class ExpenseLogRepository {

    private ExpenseLogDAO expenseLogDAO;
    private ArrayList<ExpenseLog> allExpenses;

    public ExpenseLogRepository(Application application) {
        AppDataBase db = AppDataBase.getInstance(application);
        this.expenseLogDAO = db.expenseLogDAO();
        this.allExpenses = (ArrayList<ExpenseLog>) this.expenseLogDAO.getAllRecords();
    }

//    public ArrayList<ExpenseLog> getAllExpenses() {
//        Future
//    }


}
