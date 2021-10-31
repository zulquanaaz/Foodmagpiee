package com.foodmagpie;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.foodmagpie.adapters.UserMyCharityRequestsAdapter;
import com.foodmagpie.adapters.UserMyRequestsAdapter;
import com.foodmagpie.api.ApiService;
import com.foodmagpie.api.RetroClient;
import com.foodmagpie.model.FoodRequest;
import com.foodmagpie.model.RequestsPojo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserMyCharityRequestsActivity extends AppCompatActivity {

    List<FoodRequest> requestsPojos;
    ListView usermyrequests_list;
    ProgressDialog loading;
    TextView btnHotelsrequests,btncharityrequests;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_my_charity_requests);
        getSupportActionBar().setTitle("My Requests");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnHotelsrequests=(TextView)findViewById(R.id.btnHotelsrequests);
        btnHotelsrequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent s=new Intent(UserMyCharityRequestsActivity.this,UserMyRequestsActivity.class);
                startActivity(s);
            }
        });

        btncharityrequests=(TextView)findViewById(R.id.btncharityrequests);
        btncharityrequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent s=new Intent(UserMyCharityRequestsActivity.this,UserMyCharityRequestsActivity.class);
                startActivity(s);
            }
        });


        usermyrequests_list = (ListView)findViewById(R.id.usermyrequests_list);
        requestsPojos = new ArrayList<>();
        getmyrequests();
    }
    public void getmyrequests() {
        loading = new ProgressDialog(UserMyCharityRequestsActivity.this);
        loading.setMessage("Loading");
        loading.show();
        SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("user_name", "def-val");
        ApiService apiService = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<FoodRequest>> call= apiService.getmycharityrequests(email);
        call.enqueue(new Callback<List<FoodRequest>>() {
            @Override
            public void onResponse(Call<List<FoodRequest>> call, Response<List<FoodRequest>> response) {
                loading.dismiss();
                // Toast.makeText(AdminHome.this, response.toString(), Toast.LENGTH_SHORT).show();
                if (response.body() == null) {
                    Toast.makeText(UserMyCharityRequestsActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                } else {
                    requestsPojos = response.body();
                    usermyrequests_list.setAdapter(new UserMyCharityRequestsAdapter(requestsPojos, UserMyCharityRequestsActivity.this));
                }
            }
            @Override
            public void onFailure(Call<List<FoodRequest>> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(UserMyCharityRequestsActivity.this, "Something went wrong...Please contact admin !", Toast.LENGTH_SHORT).show();
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