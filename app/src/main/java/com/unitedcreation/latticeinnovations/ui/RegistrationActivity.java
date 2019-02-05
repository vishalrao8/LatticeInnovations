package com.unitedcreation.latticeinnovations.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.unitedcreation.latticeinnovations.R;
import com.unitedcreation.latticeinnovations.database.UserDatabase;
import com.unitedcreation.latticeinnovations.model.User;
import com.unitedcreation.latticeinnovations.utils.AppExecutor;
import com.unitedcreation.latticeinnovations.utils.AppSharedPreferences;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.unitedcreation.latticeinnovations.utils.StringResourceCollection.ADDRESS_EXTRA;
import static com.unitedcreation.latticeinnovations.utils.StringResourceCollection.EMAIL_EXTRA;
import static com.unitedcreation.latticeinnovations.utils.StringResourceCollection.NAME_EXTRA;
import static com.unitedcreation.latticeinnovations.utils.StringResourceCollection.PASSWORD_EXTRA;
import static com.unitedcreation.latticeinnovations.utils.StringResourceCollection.PHONE_EXTRA;

public class RegistrationActivity extends AppCompatActivity {

    private UserDatabase mDb;

    @BindView(R.id.name_edittext)
    TextInputEditText name;

    @BindView(R.id.address_edittext)
    TextInputEditText address;

    @BindView(R.id.email_edittext)
    TextInputEditText email;

    @BindView(R.id.phone_edittext)
    TextInputEditText phone;

    @BindView(R.id.password_edittext)
    TextInputEditText password;

    @BindView(R.id.signup_btn)
    Button sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        checkUserStatus();
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        ButterKnife.bind(this);

        setFocusChangeListener();

        /**
         * Restoring previous input field data into corresponding EditTexts.
         */
        if (savedInstanceState != null) {

            name.setText(savedInstanceState.getString(NAME_EXTRA));
            address.setText(savedInstanceState.getString(ADDRESS_EXTRA));
            email.setText(savedInstanceState.getString(EMAIL_EXTRA));
            phone.setText(savedInstanceState.getString(PHONE_EXTRA));
            password.setText(savedInstanceState.getString(PASSWORD_EXTRA));

        }

        mDb = UserDatabase.getInstance(getApplicationContext());

        observeUi();

        /**
         * Adding data to database as soon as user clicks Sign up.
        **/
        sign_up.setOnClickListener(v -> {

            AppExecutor.getInstance().getDiskIO().execute(() ->

                    mDb.userDao().addUser(new User(getKey(),
                    Objects.requireNonNull(name.getText()).toString(),
                    Objects.requireNonNull(address.getText()).toString(),
                    Objects.requireNonNull(email.getText()).toString(),
                    Objects.requireNonNull(phone.getText()).toString(),
                    Objects.requireNonNull(password.getText()).toString())));

            /**
             * Updating SharedPreferences with user status as logged in.
             */
            AppSharedPreferences.updateUserStatus(getApplication(), true);
            moveToHomeActivity();

        });
    }

    /**
     * Method to set focus change listener on all input fields to hide on screen keyboard when focus goes away.
     */
    private void setFocusChangeListener() {

        name.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });

        address.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });

        email.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });

        phone.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });

        password.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        });

    }

    private void checkUserStatus() {

        /**
         * Navigating user to HomeActivity based upon current user's logged in status saved in SharedPreferences.
         */
        if (AppSharedPreferences.isUserLoggedIn(getApplication()))
            moveToHomeActivity();

    }

    /**
     * Method to initiate all the Observables, validate the emissions(input data) and updating button via Observer.
     */
    private void observeUi() {

        Observable<String> nameObservable = RxTextView.textChanges(name).skipInitialValue().map(CharSequence::toString);

        Observable<String> addressObservable = RxTextView.textChanges(address).skipInitialValue().map(CharSequence::toString);

        Observable<String> emailObservable = RxTextView.textChanges(email).skipInitialValue().map(CharSequence::toString);

        Observable<String> phoneObservable = RxTextView.textChanges(phone).skipInitialValue().map(CharSequence::toString);

        Observable<String> passwordObservable = RxTextView.textChanges(password).skipInitialValue().map(CharSequence::toString);

        /**
         * Combining all Observables into a single Observable.
         */
        Observable<Boolean> observable = Observable.combineLatest(nameObservable,
                addressObservable,
                emailObservable,
                phoneObservable,
                passwordObservable, this::isValidForm);

        /**
         * Subscribing Observable to a disposable Observer.
         */
        observable.subscribe(new DisposableObserver<Boolean>() {
            @Override
            public void onNext(Boolean aBoolean) {
                updateButton(aBoolean);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }

    /**
     * Method used to update Sign Up button.
     * @param valid A boolean based upon the result coming from validation method.
     */
    private void updateButton(Boolean valid) {

        if (valid)
            sign_up.setEnabled(true);

    }

    /**
     * Validating input data from all EditTexts.
     * @param s Name
     * @param s2 Address
     * @param s3 Email
     * @param s4 Phone Number
     * @param s5 Password
     * @return A boolean based upon the result of validation function.
     */
    private Boolean isValidForm(String s, String s2, String s3, String s4, String s5) {

        boolean validName = !s.isEmpty() && s.length() >= 4;
        if (!validName)
            name.setError(getString(R.string.reg_error_name));

        boolean validAddress = !s2.isEmpty() && s2.length() >= 10;
        if (!validAddress)
            address.setError(getString(R.string.reg_error_address));

        boolean validEmail = !s3.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(s3).matches();
        if (!validEmail)
            address.setError(getString(R.string.reg_error_email));

        boolean validPhone = !s4.isEmpty() && Patterns.PHONE.matcher(s4).matches() && s4.length() == 13;
        if (!validPhone)
            phone.setError(getString(R.string.reg_error_phone));

        boolean validPassword = !s5.isEmpty() && isValidPassword(s5);
        if (!validPassword)
            password.setError(getString(R.string.reg_error_password));

        return validName && validAddress && validEmail && validPhone && validPassword;

    }

    /**
     * Specific method to validate Password input which must contain 1 upper, 1 lower and 1 digit.
     * @param password Password input by user via corresponding EditText.
     * @return A boolean representing valid or invalid password.
     */
    private boolean isValidPassword(String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,15}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    /**
     * Using last four digits of user phone number as Primary Key for Database.
     * @return last four digits of given phone number.
     */
    private int getKey () {
        return Integer.valueOf(Objects.requireNonNull(phone.getText()).toString()
                .substring(phone.length()-4, phone.length()));
    }

    private void moveToHomeActivity() {

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();

    }

    public void hideKeyboard(View view) {

        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(inputMethodManager).hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

    /**
     * Saving current EditText input data to be restored after configuration changes (screen rotation).
     * @param outState Bundle of data provided by user.
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(NAME_EXTRA, Objects.requireNonNull(name.getText()).toString());
        outState.putString(ADDRESS_EXTRA, Objects.requireNonNull(address.getText()).toString());
        outState.putString(EMAIL_EXTRA, Objects.requireNonNull(email.getText()).toString());
        outState.putString(PHONE_EXTRA, Objects.requireNonNull(phone.getText()).toString());
        outState.putString(PASSWORD_EXTRA, Objects.requireNonNull(password.getText()).toString());
    }
}
