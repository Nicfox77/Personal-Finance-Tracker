package com.example.personal_finance_tracker.DB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.personal_finance_tracker.DB.entities.ExpenseLog;

import java.util.List;

@Dao
public interface ExpenseLogDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ExpenseLog expenseLog);

    @Query("Select * from " + AppDataBase.EXPENSE_LOG_TABLE)
    List<ExpenseLog> getAllRecords();

    @Query("SELECT * FROM " + AppDataBase.EXPENSE_LOG_TABLE + " WHERE userId = :loggedInUserId")
    LiveData<List<ExpenseLog>> getRecordsByUserId(int loggedInUserId);
}
