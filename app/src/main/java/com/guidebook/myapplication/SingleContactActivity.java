package com.guidebook.myapplication;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class SingleContactActivity  extends Activity {

    // JSON node keys
    private static final String TAG_NAME = "name";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_PHONE_MOBILE = "mobile";
    private static final String TAG_CITY = "city";
    private static final String TAG_ICON = "icon";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_contact);

        // getting intent data
        Intent in = getIntent();

        // Get JSON values from previous intent
        String name = in.getStringExtra(TAG_NAME);
        String email = in.getStringExtra(TAG_EMAIL);
        String mobile = in.getStringExtra(TAG_PHONE_MOBILE);
        String City = in.getStringExtra(TAG_CITY);
        String Icon = in.getStringExtra(TAG_ICON);
        Uri myUri = Uri.parse(Icon);


        // Displaying all values on the screen
        TextView lblName = (TextView) findViewById(R.id.name_label);
        TextView lblEmail = (TextView) findViewById(R.id.email_label);
        TextView lblMobile = (TextView) findViewById(R.id.mobile_label);
        TextView lblCity = (TextView) findViewById(R.id.city_state_label);
        ImageView lblIcon = (ImageView) findViewById(R.id.icon);

        lblName.setText(name);
        lblEmail.setText(email);
        lblMobile.setText(mobile);
        lblCity.setText(City);
        Picasso.with(getApplicationContext()).load(myUri).into(lblIcon);

    }
}
