package com.example.personal_finance_tracker.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.example.personal_finance_tracker.FinanceTrackerUser;

@Dao
public interface FinanceTrackerDAO {

    @Insert
    void insert(FinanceTrackerUser... users);

    @Update
    void update(FinanceTrackerUser... users);

    @Delete
    void delete(FinanceTrackerUser users);

}
