package com.example.personal_finance_tracker.DB;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.personal_finance_tracker.DB.entities.ExpenseLog;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ExpenseLogRepository {

    private FinanceTrackerDAO financeTrackerDAO;
    private LiveData<List<ExpenseLog>> allExpenses;

    public ExpenseLogRepository(Application application) {
        AppDataBase db = AppDataBase.getInstance(application);
        this.financeTrackerDAO = db.financeTrackerDAO();
        this.allExpenses = financeTrackerDAO.getAllRecords();
    }

    public List<ExpenseLog> getAllExpensesByUserId(int loggedInUserId) {
        Future<ArrayList<ExpenseLog>> future = AppDataBase.databaseWriteExecutor.submit(
                new Callable<ArrayList<ExpenseLog>>() {
                    @Override
                    public ArrayList<ExpenseLog> call() throws Exception {
                        return (ArrayList<ExpenseLog>) financeTrackerDAO.getRecordSetUserId(loggedInUserId);
                    }
                });
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error with future");
        }
        return null;
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
