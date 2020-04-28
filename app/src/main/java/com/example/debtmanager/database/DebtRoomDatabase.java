package com.example.debtmanager.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.debtmanager.dao.DebtDao;
import com.example.debtmanager.model.DebtInfo;



@Database(entities = {DebtInfo.class}, version = 1, exportSchema = false)
public abstract class DebtRoomDatabase extends RoomDatabase {
    
    public static final String TAG = DebtRoomDatabase.class.getSimpleName();

    public abstract DebtDao debtDao();

    private static DebtRoomDatabase INSTANCE;

    public static synchronized DebtRoomDatabase getDatabase(Context context) {



        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    DebtRoomDatabase.class, "info_table")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
