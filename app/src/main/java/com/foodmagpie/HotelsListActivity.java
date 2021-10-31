package com.foodmagpie;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.foodmagpie.adapters.HotelsListAdapter;
import com.foodmagpie.adapters.MyHotelsAdapter;
import com.foodmagpie.api.ApiService;
import com.foodmagpie.api.RetroClient;
import com.foodmagpie.model.HotelPojo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotelsListActivity extends AppCompatActivity {

    List<HotelPojo> hotelsPojos;
    ListView hotel_list;
    ProgressDialog loading;
    Button btnaddhotel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotels_list);
        getSupportActionBar().setTitle("Hotels List");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        hotel_list = (ListView) findViewById(R.id.hotel_list);
        hotelsPojos = new ArrayList<>();
        getHotels();
    }

    public void getHotels() {
        loading = new ProgressDialog(HotelsListActivity.this);
        loading.setMessage("Loading");
        loading.show();
        SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("user_name", "def-val");
        ApiService apiService = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<HotelPojo>> call= apiService.getHotels();
        call.enqueue(new Callback<List<HotelPojo>>() {
            @Override
            public void onResponse(Call<List<HotelPojo>> call, Response<List<HotelPojo>> response) {
                loading.dismiss();
                // Toast.makeText(AdminHome.this, response.toString(), Toast.LENGTH_SHORT).show();
                if (response.body() == null) {
                    Toast.makeText(HotelsListActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                } else {
                    hotelsPojos = response.body();
                    hotel_list.setAdapter(new HotelsListAdapter(hotelsPojos, HotelsListActivity.this));
                }
            }
            @Override
            public void onFailure(Call<List<HotelPojo>> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(HotelsListActivity.this, "Something went wrong...Please contact admin !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}