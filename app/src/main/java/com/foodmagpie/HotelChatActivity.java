package com.foodmagpie;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.foodmagpie.adapters.HotelChatAdapter;
import com.foodmagpie.api.ApiService;
import com.foodmagpie.api.RetroClient;
import com.foodmagpie.model.Chat;
import com.foodmagpie.model.ResponseData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotelChatActivity extends AppCompatActivity {

    ListView list_view;
    ProgressDialog progressDialog;
    List<Chat> message;
    SharedPreferences sharedPreferences;
    HotelChatAdapter hotelChatAdapter;
    String session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_chat);
        getSupportActionBar().setTitle("Chat");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("user_name", "def-val");

        list_view=(ListView)findViewById(R.id.list_view);
        message= new ArrayList<>();
        getMessages();
    }
    public void getMessages(){
        progressDialog = new ProgressDialog(HotelChatActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();


        ApiService apiService = RetroClient.getRetrofitInstance().create(ApiService.class);
          Call<List<Chat>> call= apiService.hotelchat(session);
        call.enqueue(new Callback<List<Chat>>() {
            @Override
            public void onResponse(Call<List<Chat>> call, Response<List<Chat>> response) {
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(HotelChatActivity.this,"No data found", Toast.LENGTH_SHORT).show();
                }else {
                    message = response.body();
                    hotelChatAdapter=new HotelChatAdapter(message,HotelChatActivity.this);  //attach adapter class with therecyclerview
                    list_view.setAdapter(hotelChatAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Chat>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(HotelChatActivity.this, "Please try later!", Toast.LENGTH_SHORT).show();
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