package com.unitedcreation.latticeinnovations.viewModel;

import android.app.Application;

import com.unitedcreation.latticeinnovations.database.UserDatabase;
import com.unitedcreation.latticeinnovations.model.User;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class UserListViewModel extends AndroidViewModel {

    private final LiveData<List<User>> userList;

    public UserListViewModel (@NonNull Application application) {
        super(application);
        userList = UserDatabase.getInstance(application).userDao().loadAllUsers();
    }

    public LiveData<List<User>> getUserList() {
        return userList;
    }
}
