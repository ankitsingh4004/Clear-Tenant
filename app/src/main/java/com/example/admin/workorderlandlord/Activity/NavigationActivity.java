package com.example.admin.workorderlandlord.Activity;

import android.app.Dialog;
import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.admin.workorderlandlord.Fragment.HomeFragment;
import com.example.admin.workorderlandlord.R;

public class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(NavigationActivity.this);

        HomeFragment fragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container, fragment).commit();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();

        if (id == R.id.nav_home) {

            //isDestroyed();
        }


        if (id == R.id.nav_logout) {
            showDialog();
            //isDestroyed();
        }
        if (id == R.id.nav_profile) {

            Intent intent = new Intent(NavigationActivity.this, ProfileActivity.class);
            startActivity(intent);


        } if (id == R.id.nav_changePass) {

            Intent intent = new Intent(NavigationActivity.this, ChangePassword.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

            return false;



    }


    public void showDialog() {
            final Dialog dialog = new Dialog(NavigationActivity.this);
            dialog.setContentView(R.layout.inflate_logout_dialogue);
            TextView tv_no = dialog.findViewById(R.id.tv_dialog_check_no);
            TextView tv_yes = dialog.findViewById(R.id.tv_dialog_check_yes);

            dialog.setTitle("Alert");
            dialog.setCancelable(false);
            dialog.show();


            tv_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();


                }
            });

            tv_yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    startActivity(new Intent(NavigationActivity.this, LoginActivity.class).
                            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finish();

                }
            });


        }

}