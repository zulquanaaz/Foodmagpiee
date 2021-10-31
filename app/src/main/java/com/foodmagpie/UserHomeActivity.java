package com.foodmagpie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;

public class UserHomeActivity extends AppCompatActivity {
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private DrawerLayout dl;
    Button btnHotels,btnCharity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        getSupportActionBar().setTitle("Home");
        navigationView();
        btnHotels=(Button)findViewById(R.id.btnHotels);
        btnHotels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home=new Intent(getApplicationContext(), HotelsListActivity.class);
                startActivity(home);
            }
        });

        btnCharity=(Button)findViewById(R.id.btnCharity);
        btnCharity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home=new Intent(getApplicationContext(), CharityListActivity.class);
                startActivity(home);
            }
        });
    }

    private void navigationView(){
        dl = (DrawerLayout)findViewById(R.id.drawe_layout);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);
        dl.addDrawerListener(t);
        t.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nv = (NavigationView)findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.home:
                        Intent home=new Intent(getApplicationContext(), UserHomeActivity.class);
                        startActivity(home);
                        break;
                    case R.id.profile:
                        Intent profile=new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(profile);
                        break;

                    case R.id.hotels:
                        Intent hotels=new Intent(getApplicationContext(), HotelsListActivity.class);
                        startActivity(hotels);
                        break;

                    case R.id.requests:
                        Intent requests=new Intent(getApplicationContext(), UserMyRequestsActivity.class);
                        startActivity(requests);
                        break;

                    case R.id.origanisations:
                        Intent origanisations=new Intent(getApplicationContext(), UserHomeActivity.class);
                        startActivity(origanisations);
                        break;

                    case R.id.logout:
                        Intent logout=new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(logout);
                        finish();
                        break;

                    default:
                        return true;
                }
                dl.closeDrawer(GravityCompat.START);
                return true;

            }
        });
    }
    @Override
    public void onBackPressed() {
        if (dl.isDrawerOpen(GravityCompat.START)) {
            dl.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (dl.isDrawerOpen(GravityCompat.START)) {
            dl.closeDrawer(GravityCompat.START);
        } else {
            dl.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
}