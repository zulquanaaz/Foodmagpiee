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

import com.foodmagpie.adapters.MyDonationAdapter;
import com.foodmagpie.adapters.MyHotelsAdapter;
import com.foodmagpie.api.ApiService;
import com.foodmagpie.api.RetroClient;
import com.foodmagpie.model.DonationPojo;
import com.foodmagpie.model.HotelPojo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyDonationsActivity extends AppCompatActivity {
    List<DonationPojo> donationPojos;
    ListView donations_list;
    ProgressDialog loading;
    Button btnaddDonation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_donations);
        getSupportActionBar().setTitle("Donations List");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnaddDonation=(Button)findViewById(R.id.btnaddDonation);

        btnaddDonation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MyDonationsActivity.this,AddDonationActivity.class);
                startActivity(i);
            }
        });

        donations_list = (ListView)findViewById(R.id.donations_list);
        donationPojos = new ArrayList<>();
        getDonations();
    }

    public void getDonations() {
        loading = new ProgressDialog(MyDonationsActivity.this);
        loading.setMessage("Loading");
        loading.show();
        SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("user_name", "def-val");
        ApiService apiService = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<DonationPojo>> call= apiService.getmydonations(email);
        call.enqueue(new Callback<List<DonationPojo>>() {
            @Override
            public void onResponse(Call<List<DonationPojo>> call, Response<List<DonationPojo>> response) {
                loading.dismiss();
                // Toast.makeText(AdminHome.this, response.toString(), Toast.LENGTH_SHORT).show();
                if (response.body() == null) {
                    Toast.makeText(MyDonationsActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                } else {
                    donationPojos = response.body();
                    donations_list.setAdapter(new MyDonationAdapter(donationPojos, MyDonationsActivity.this));
                }
            }
            @Override
            public void onFailure(Call<List<DonationPojo>> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(MyDonationsActivity.this, "Something went wrong...Please contact admin !", Toast.LENGTH_SHORT).show();
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