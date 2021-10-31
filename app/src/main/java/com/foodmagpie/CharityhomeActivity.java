package com.foodmagpie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.foodmagpie.adapters.CharityMyRequestsAdaper;
import com.foodmagpie.adapters.HotelMyRequestsAdapter;
import com.foodmagpie.api.ApiService;
import com.foodmagpie.api.RetroClient;
import com.foodmagpie.model.FoodRequest;
import com.foodmagpie.model.RequestsPojo;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharityhomeActivity extends AppCompatActivity {
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private DrawerLayout dl;
    Button btnHotels,btnCharity;
    List<FoodRequest> requestsPojos;
    ListView charitymyrequests_list;
    ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charityhome);
        getSupportActionBar().setTitle("Charity Home");
        navigationView();


        charitymyrequests_list = (ListView)findViewById(R.id.charitymyrequests_list);
        requestsPojos = new ArrayList<>();
        getMyRequests();
    }



    public void getMyRequests() {
        loading = new ProgressDialog(CharityhomeActivity.this);
        loading.setMessage("Loading");
        loading.show();
        SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("user_name", "def-val");
        ApiService apiService = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<FoodRequest>> call= apiService.getcharityrequests(email);
        call.enqueue(new Callback<List<FoodRequest>>() {
            @Override
            public void onResponse(Call<List<FoodRequest>> call, Response<List<FoodRequest>> response) {
                loading.dismiss();
                // Toast.makeText(AdminHome.this, response.toString(), Toast.LENGTH_SHORT).show();
                if (response.body() == null) {
                    Toast.makeText(CharityhomeActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                } else {
                    requestsPojos = response.body();
                    charitymyrequests_list.setAdapter(new CharityMyRequestsAdaper(requestsPojos, CharityhomeActivity.this));
                }
            }
            @Override
            public void onFailure(Call<List<FoodRequest>> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(CharityhomeActivity.this, "Something went wrong...Please contact admin !", Toast.LENGTH_SHORT).show();
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
                        Intent home=new Intent(getApplicationContext(), CharityhomeActivity.class);
                        startActivity(home);
                        break;
                    case R.id.profile:
                        Intent profile=new Intent(getApplicationContext(), CharityProfileActivity.class);
                        startActivity(profile);
                        break;

                    case R.id.hotels:
                        Intent hotels=new Intent(getApplicationContext(), CharityHotelsListActivity.class);
                        startActivity(hotels);
                        break;

                    case R.id.requests:
                        Intent requests=new Intent(getApplicationContext(), CharityHotelReplyActivity.class);
                        startActivity(requests);
                        break;

                    case R.id.origanisations:
                        Intent origanisations=new Intent(getApplicationContext(), MyCharityActivity.class);
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