package com.unitedcreation.latticeinnovations.ui;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.unitedcreation.latticeinnovations.R;

import static com.unitedcreation.latticeinnovations.utils.StringResourceCollection.ADDRESS_EXTRA;
import static com.unitedcreation.latticeinnovations.utils.StringResourceCollection.EMAIL_EXTRA;
import static com.unitedcreation.latticeinnovations.utils.StringResourceCollection.KEY_EXTRA;
import static com.unitedcreation.latticeinnovations.utils.StringResourceCollection.NAME_EXTRA;
import static com.unitedcreation.latticeinnovations.utils.StringResourceCollection.PHONE_EXTRA;

public class UserDetailActivity extends AppCompatActivity {

    private Integer key;
    private String nameString;
    private String addressString;
    private String emailString;
    private String phoneString;

    @BindView(R.id.avatar_iv)
    ImageView avatar;

    @BindView(R.id.name_tv)
    TextView name;

    @BindView(R.id.address_info_tv)
    TextView address;

    @BindView(R.id.email_info_tv)
    TextView email;

    @BindView(R.id.phone_info_tv)
    TextView phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        setTitle(getString(R.string.user_title));

        initExtras();

        /**
         * Populate data only if there is some key associated with User object, passed from previous activity, which validates the existence of data.
         */
        if (key != 0)
            populateUi();

    }

    /**
     * Initiating all the variables holding String data to be populated in UI.
     */
    private void initExtras() {

        key = getIntent().getIntExtra(KEY_EXTRA, 0);
        nameString = getIntent().getStringExtra(NAME_EXTRA);
        addressString = getIntent().getStringExtra(ADDRESS_EXTRA);
        emailString = getIntent().getStringExtra(EMAIL_EXTRA);
        phoneString = getIntent().getStringExtra(PHONE_EXTRA);


    }

    /**
     * Method to populate UI with desired data.
     */
    private void populateUi() {

        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(key);

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(nameString.substring(0, 1), color);

        avatar.setImageDrawable(drawable);
        name.setText(nameString);
        address.setText(addressString);
        email.setText(emailString);
        phone.setText(phoneString);
    }
}
