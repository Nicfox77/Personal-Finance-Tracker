package com.example.personal_finance_tracker;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.personal_finance_tracker.DB.AppDataBase;

/**
 * This class represents a user of the finance tracker application.
 * The user can create a budget, add expenses and income, and view their financial information.
 */
@Entity(tableName = AppDataBase.USER_TABLE)
public class FinanceTrackerUser {
    @PrimaryKey(autoGenerate = true)
    private int userID;



    public FinanceTrackerUser(int userID) {
        this.userID = userID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
