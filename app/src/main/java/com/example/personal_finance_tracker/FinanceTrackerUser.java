package com.example.personal_finance_tracker;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * This class represents a user of the finance tracker application.
 * The user can create a budget, add expenses and income, and view their financial information.
 */
@Entity
public class FinanceTrackerUser {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String username;
    private String password;
    private boolean isAdmin;
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
}
