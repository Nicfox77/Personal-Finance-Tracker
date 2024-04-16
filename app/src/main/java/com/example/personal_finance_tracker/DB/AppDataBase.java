package com.example.personal_finance_tracker.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.personal_finance_tracker.FinanceTrackerUser;

@Database(entities = {FinanceTrackerUser.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public static final String DB_NAME = "finance_tracker.db";
    public static final String TABLE_NAME = "user_login_table";

    private static volatile AppDataBase instance;
    private static final Object LOCK = new Object();
    public abstract FinanceTrackerDAO financeTrackerDAO();

    public static AppDataBase getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDataBase.class,
                            DB_NAME).build();
                }
            }
        }
        return instance;
    }
}
