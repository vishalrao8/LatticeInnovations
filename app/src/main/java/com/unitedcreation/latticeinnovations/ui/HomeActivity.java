package com.unitedcreation.latticeinnovations.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.unitedcreation.latticeinnovations.R;
import com.unitedcreation.latticeinnovations.adapter.UserRecyclerViewAdapter;
import com.unitedcreation.latticeinnovations.model.User;
import com.unitedcreation.latticeinnovations.utils.AppSharedPreferences;
import com.unitedcreation.latticeinnovations.viewModel.UserListViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.unitedcreation.latticeinnovations.utils.StringResourceCollection.ADDRESS_EXTRA;
import static com.unitedcreation.latticeinnovations.utils.StringResourceCollection.EMAIL_EXTRA;
import static com.unitedcreation.latticeinnovations.utils.StringResourceCollection.KEY_EXTRA;
import static com.unitedcreation.latticeinnovations.utils.StringResourceCollection.NAME_EXTRA;
import static com.unitedcreation.latticeinnovations.utils.StringResourceCollection.PHONE_EXTRA;

public class HomeActivity extends AppCompatActivity implements UserRecyclerViewAdapter.myOnClickListener {

    @BindView(R.id.users_rv)
    RecyclerView userListRecyclerView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            /**
             * Updating current user's status as logged out.
             */
            case R.id.item_logout:
                AppSharedPreferences.updateUserStatus(getApplication(), false);
                moveToRegistrationActivity();

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setTitle(getString(R.string.home_title));

        ButterKnife.bind(this);

        final UserRecyclerViewAdapter adapter = new UserRecyclerViewAdapter(this, new ArrayList<>());
        userListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        userListRecyclerView.setAdapter(adapter);

        /**
         * ViewModel to access list of all users stored inside Database and attaching Observer to LiveData coming from Database for single event change.
         */
        final UserListViewModel userListViewModel = ViewModelProviders.of(this).get(UserListViewModel.class);
        userListViewModel.getUserList().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(final List<User> users) {

                userListViewModel.getUserList().removeObserver(this);
                adapter.updateList(users);

            }
        });

    }

    private void moveToRegistrationActivity() {

        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
        finish();

    }

    /**
     * Interface of UserRecyclerView to pass the data sets to current activity.
     * @param key Primary Key
     * @param name Name
     * @param address Address
     * @param email Email
     * @param phone Phone Number
     */
    @Override
    public void onClickListener(int key, String name, String address, String email, String phone) {

        Intent intent = new Intent(this, UserDetailActivity.class);

        intent.putExtra(KEY_EXTRA, key);
        intent.putExtra(NAME_EXTRA, name);
        intent.putExtra(ADDRESS_EXTRA, address);
        intent.putExtra(EMAIL_EXTRA, email);
        intent.putExtra(PHONE_EXTRA, phone);

        startActivity(intent);

    }
}
