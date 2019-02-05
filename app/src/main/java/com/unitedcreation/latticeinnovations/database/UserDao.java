package com.unitedcreation.latticeinnovations.database;

import com.unitedcreation.latticeinnovations.model.User;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface UserDao {

    @Query("SELECT * FROM users")
    LiveData<List<User>> loadAllUsers();

    @Query("SELECT * FROM users WHERE name=:name")
    LiveData<User> loadUserByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addUser(User user);
}
