package com.unitedcreation.latticeinnovations.database;

import android.content.Context;

import com.unitedcreation.latticeinnovations.model.User;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    private static UserDatabase sInstance;
    private static final Object LOCK = new Object();
    private static final String databaseName = "registered_users";

    public static UserDatabase getInstance (Context context) {

        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        UserDatabase.class, databaseName)
                        .build();
            }
        }

        return sInstance;
    }

    public abstract UserDao userDao();
}
