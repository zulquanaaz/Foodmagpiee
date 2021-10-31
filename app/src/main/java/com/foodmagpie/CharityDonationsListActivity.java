package com.foodmagpie;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.foodmagpie.adapters.CharityDonationsListAdapter;
import com.foodmagpie.adapters.DonationsListAdapter;
import com.foodmagpie.api.ApiService;
import com.foodmagpie.api.RetroClient;
import com.foodmagpie.model.DonationPojo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharityDonationsListActivity extends AppCompatActivity {
    List<DonationPojo> donationPojos;
    ListView donations_list;
    ProgressDialog loading;
    Button btnaddDonation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charity_donations_list);
        getSupportActionBar().setTitle("Donations List");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        donations_list = (ListView) findViewById(R.id.donations_list);
        donationPojos = new ArrayList<>();
        getDonations();
    }

    public void getDonations() {
        loading = new ProgressDialog(CharityDonationsListActivity.this);
        loading.setMessage("Loading");
        loading.show();
        String hname=getIntent().getStringExtra("name");
        SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("user_name", "def-val");
        ApiService apiService = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<DonationPojo>> call = apiService.getDonations(hname);
        call.enqueue(new Callback<List<DonationPojo>>() {
            @Override
            public void onResponse(Call<List<DonationPojo>> call, Response<List<DonationPojo>> response) {
                loading.dismiss();
                // Toast.makeText(AdminHome.this, response.toString(), Toast.LENGTH_SHORT).show();
                if (response.body() == null) {
                    Toast.makeText(CharityDonationsListActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                } else {
                    donationPojos = response.body();
                    donations_list.setAdapter(new CharityDonationsListAdapter(donationPojos, CharityDonationsListActivity.this));
                }
            }

            @Override
            public void onFailure(Call<List<DonationPojo>> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(CharityDonationsListActivity.this, "Something went wrong...Please contact admin !", Toast.LENGTH_SHORT).show();
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