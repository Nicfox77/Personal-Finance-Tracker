package com.example.personal_finance_tracker.viewHolders;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.personal_finance_tracker.DB.ExpenseLogRepository;
import com.example.personal_finance_tracker.DB.entities.ExpenseLog;

import java.util.List;

public class ExpenseLogViewModel extends AndroidViewModel {
    private ExpenseLogRepository repository;
    private final LiveData<List<ExpenseLog>> allLogsById;

    public ExpenseLogViewModel (Application application) {
        super(application);
    }
}
