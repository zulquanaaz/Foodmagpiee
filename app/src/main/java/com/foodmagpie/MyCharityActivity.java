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

import com.foodmagpie.adapters.MyCharityAdapter;
import com.foodmagpie.adapters.MyHotelsAdapter;
import com.foodmagpie.api.ApiService;
import com.foodmagpie.api.RetroClient;
import com.foodmagpie.model.HotelPojo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCharityActivity extends AppCompatActivity {

    List<HotelPojo> hotelsPojos;
    ListView charity_list;
    ProgressDialog loading;
    Button btnaddcharity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_charity);
        getSupportActionBar().setTitle("Organisations List");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnaddcharity=(Button)findViewById(R.id.btnaddcharity);

        btnaddcharity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MyCharityActivity.this,AddCharityActivity.class);
                startActivity(i);
            }
        });

        charity_list = (ListView) findViewById(R.id.charity_list);
        hotelsPojos = new ArrayList<>();
        getOrganisations();
    }

    public void getOrganisations() {
        loading = new ProgressDialog(MyCharityActivity.this);
        loading.setMessage("Loading");
        loading.show();
        SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("user_name", "def-val");
        ApiService apiService = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<HotelPojo>> call= apiService.myorganisations(email);
        call.enqueue(new Callback<List<HotelPojo>>() {
            @Override
            public void onResponse(Call<List<HotelPojo>> call, Response<List<HotelPojo>> response) {
                loading.dismiss();
                // Toast.makeText(AdminHome.this, response.toString(), Toast.LENGTH_SHORT).show();
                if (response.body() == null) {
                    Toast.makeText(MyCharityActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                } else {
                    hotelsPojos = response.body();
                    charity_list.setAdapter(new MyCharityAdapter(hotelsPojos, MyCharityActivity.this));
                }
            }
            @Override
            public void onFailure(Call<List<HotelPojo>> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(MyCharityActivity.this, "Something went wrong...Please contact admin !", Toast.LENGTH_SHORT).show();
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