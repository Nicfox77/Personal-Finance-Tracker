package com.example.personal_finance_tracker.DB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.personal_finance_tracker.FinanceTrackerUser;
import com.example.personal_finance_tracker.User;

import java.util.List;

@Dao
public interface FinanceTrackerDAO {

    @Insert
    void insert(User data);

    @Insert
    void insert(User ... users);

    @Update
    void update(User ...user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM " + AppDataBase.USER_TABLE+ " ORDER BY email")
    List<User> getAllUsers();

    @Query("SELECT * FROM " + AppDataBase.USER_TABLE+ " WHERE email = :email")
    User getUser(String email);

    @Query("SELECT * FROM " + AppDataBase.USER_TABLE+ " WHERE userID = :userID")
    User getUser(int userID);
}
