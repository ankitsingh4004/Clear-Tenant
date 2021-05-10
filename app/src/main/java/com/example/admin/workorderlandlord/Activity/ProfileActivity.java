package com.example.admin.workorderlandlord.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.example.admin.workorderlandlord.R;
import com.example.admin.workorderlandlord.remote.PreferenceManager;

public class ProfileActivity extends AppCompatActivity {

    EditText name;
    EditText email;
    EditText phone;
    EditText company;
    EditText city;
    EditText country;

    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);




        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone);
        company=findViewById(R.id.company);
        city=findViewById(R.id.city);
        country=findViewById(R.id.country);




        preferenceManager= PreferenceManager.getInstance(getApplicationContext());

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        name.setText(preferenceManager.getKey_User_Name());
        email.setText(preferenceManager.getKey_User_Name());


    }
}