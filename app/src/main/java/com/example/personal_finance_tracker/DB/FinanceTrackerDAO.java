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

    @Query("SELECT * FROM " + AppDataBase.USER_LOGIN_TABLE+ " ORDER BY email")
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

}
