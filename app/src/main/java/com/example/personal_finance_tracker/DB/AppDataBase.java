package com.example.personal_finance_tracker.DB;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.personal_finance_tracker.DB.entities.ExpenseLog;
import com.example.personal_finance_tracker.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, ExpenseLog.class}, version = 2)
public abstract class AppDataBase extends RoomDatabase {
    public static final String DB_NAME = "finance_tracker.db";
    public static final String USER_LOGIN_TABLE = "user_login_table";
    public static final String USER_TABLE = "user_table";
    public static final String EXPENSE_LOG_TABLE = "expenseLogTable";

    private static volatile AppDataBase instance;
    public static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static final Object LOCK = new Object();
    public abstract FinanceTrackerDAO financeTrackerDAO();

    public static AppDataBase getInstance(Context context) {
        if(instance == null) {
            synchronized (LOCK) {
                if(instance == null) {
                    instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDataBase.class,
                                    DB_NAME
                            )
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return instance;
    }

    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            //TODO: add databaseWriteExecutor.execute
        }
    };

    public abstract ExpenseLogDAO expenseLogDAO();

//    public static AppDataBase getInstance(Context context) {
//        if (instance == null) {
//            synchronized (LOCK) {
//                if (instance == null) {
//                    instance = Room.databaseBuilder(context.getApplicationContext(),
//                            AppDataBase.class,
//                            DB_NAME).build();
//                }
//            }
//        }
//        return instance;
//    }

}
