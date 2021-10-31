package com.foodmagpie;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.foodmagpie.adapters.HotelMyRequestsAdapter;
import com.foodmagpie.adapters.UserMyRequestsAdapter;
import com.foodmagpie.api.ApiService;
import com.foodmagpie.api.RetroClient;
import com.foodmagpie.model.RequestsPojo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotelMyRequestsActivity extends AppCompatActivity {

    List<RequestsPojo> requestsPojos;
    ListView hotelmyrequests_list;
    ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_my_requests);
        getSupportActionBar().setTitle("My Requests");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        hotelmyrequests_list = (ListView)findViewById(R.id.hotelmyrequests_list);
        requestsPojos = new ArrayList<>();
        getMyRequests();
    }

    public void getMyRequests() {
        loading = new ProgressDialog(HotelMyRequestsActivity.this);
        loading.setMessage("Loading");
        loading.show();
        SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("user_name", "def-val");
        ApiService apiService = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<RequestsPojo>> call= apiService.getmyuserrequests(email);
        call.enqueue(new Callback<List<RequestsPojo>>() {
            @Override
            public void onResponse(Call<List<RequestsPojo>> call, Response<List<RequestsPojo>> response) {
                loading.dismiss();
                // Toast.makeText(AdminHome.this, response.toString(), Toast.LENGTH_SHORT).show();
                if (response.body() == null) {
                    Toast.makeText(HotelMyRequestsActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                } else {
                    requestsPojos = response.body();
                    hotelmyrequests_list.setAdapter(new HotelMyRequestsAdapter(requestsPojos, HotelMyRequestsActivity.this));
                }
            }
            @Override
            public void onFailure(Call<List<RequestsPojo>> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(HotelMyRequestsActivity.this, "Something went wrong...Please contact admin !", Toast.LENGTH_SHORT).show();
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