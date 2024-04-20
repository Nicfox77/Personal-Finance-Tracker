package com.example.personal_finance_tracker.DB.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.personal_finance_tracker.DB.AppDataBase;


import java.util.Objects;

@Entity(tableName = AppDataBase.EXPENSE_LOG_TABLE)
public class ExpenseLog {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private double amount;

    private int userId;

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public ExpenseLog(String name, double amount, int userId) {
        this.name = name;
        this.amount = amount;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpenseLog that = (ExpenseLog) o;
        return id == that.id && amount == that.amount && userId == that.userId && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, amount, userId);
    }
}

