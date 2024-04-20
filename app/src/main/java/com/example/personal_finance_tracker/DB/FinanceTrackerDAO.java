package com.example.personal_finance_tracker.DB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.personal_finance_tracker.DB.entities.ExpenseLog;
import com.example.personal_finance_tracker.FinanceTrackerUser;
import com.example.personal_finance_tracker.User;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface FinanceTrackerDAO {

    @Query("SELECT * FROM " + AppDataBase.EXPENSE_LOG_TABLE + " WHERE userId = :loggedInUserId")
    List<ExpenseLog> getRecordSetUserId(int loggedInUserId);

    @Insert
    void insert(User data);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ExpenseLog expenseLog);

    @Query("Select * from " + AppDataBase.EXPENSE_LOG_TABLE)
    LiveData<List<ExpenseLog>> getAllRecords();

    @Query("DELETE FROM " + AppDataBase.EXPENSE_LOG_TABLE)
    void deleteAll();

//    @Query("SELECT * FROM " + AppDataBase.EXPENSE_LOG_TABLE + " WHERE userId = :loggedInUserId")
//    LiveData<List<ExpenseLog>> getRecordsByUserId(int loggedInUserId);

    @Query("SELECT * FROM " + AppDataBase.USER_LOGIN_TABLE+ " ORDER BY username")
    List<User> getAllUsernames();

    @Query("SELECT * FROM " + AppDataBase.USER_LOGIN_TABLE + " WHERE userID = :userID")
    User getUserLoginById(int userID);

    @Query("SELECT * FROM " + AppDataBase.USER_LOGIN_TABLE + " WHERE email = :email")
    User getUserByEmail(String email);

    @Query("SELECT * FROM " + AppDataBase.USER_TABLE)
    List<FinanceTrackerUser> getAllUsers();

    @Query("SELECT * FROM " + AppDataBase.USER_TABLE + " WHERE userID = :userID")
    List<FinanceTrackerUser> getUserByID(int userID);

    @Insert
    void insert(User ...users);

    @Update
    void update(User ...users);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM " + AppDataBase.USER_LOGIN_TABLE + " WHERE username = :username")
    User getUserByUsername(String username);
}
