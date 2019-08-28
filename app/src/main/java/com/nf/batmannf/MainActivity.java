package com.nf.batmannf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Navid Farhadi
 * Android developer
 * MVP pattern
 * <p>
 * Retrofit RXAndroid RXJava Glide MVP ButterKnife
 * singleton activity pattern   useCase
 * <p>
 * keep data in AppDataManager and settingManager and show data in offline mode
 * i can use Builder , singleton , chain , ... designPattern if i need it
 *
 * Extra :we have search for find item in this version
 */
public class MainActivity extends AppCompatActivity implements MainDrawerInterAction {

    DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.main_drawer);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void toggleMainDrawer() {
        if (!drawer.isDrawerOpen(GravityCompat.START)) drawer.openDrawer(Gravity.LEFT);
        else drawer.closeDrawer(Gravity.RIGHT);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            moveTaskToBack(true);
        }
    }
}
