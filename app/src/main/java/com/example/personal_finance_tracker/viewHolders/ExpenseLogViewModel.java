package com.example.personal_finance_tracker.viewHolders;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.personal_finance_tracker.DB.ExpenseLogRepository;
import com.example.personal_finance_tracker.DB.entities.ExpenseLog;

import java.util.List;

public class ExpenseLogViewModel extends AndroidViewModel {
    private final ExpenseLogRepository repository;
    private final LiveData<List<ExpenseLog>> allExpensesByUserId;

    public ExpenseLogViewModel (Application application, int userId) {

        super(application);
        repository = ExpenseLogRepository.getRepository(application);
        assert repository != null;
        allExpensesByUserId = repository.getAllExpensesByUserId(userId);
    }

    public LiveData<List<ExpenseLog>> getAllExpensesById() {
        return allExpensesByUserId;
    }

    public void insert(ExpenseLog expense) {
        repository.insertExpenseLog(expense);
    }
}
