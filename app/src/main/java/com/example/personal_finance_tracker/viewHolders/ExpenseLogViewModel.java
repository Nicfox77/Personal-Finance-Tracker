package com.example.personal_finance_tracker.viewHolders;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.personal_finance_tracker.DB.ExpenseLogRepository;
import com.example.personal_finance_tracker.DB.entities.ExpenseLog;

import java.util.List;

public class ExpenseLogViewModel extends AndroidViewModel {
    private final ExpenseLogRepository repository;
    private final LiveData<List<ExpenseLog>> allLogsById;

    public ExpenseLogViewModel (Application application, int userId) {

        super(application);
        repository = ExpenseLogRepository.getRepository(application);
        allLogsById = repository.getAllLogsByUserId(userId);
    }

    public LiveData<List<ExpenseLog>> getAllLogsById() {
        return allLogsById;
    }

    public void insert(ExpenseLog expense) {
        repository.insertExpenseLog(expense);
    }
}
