package com.foodmagpie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.foodmagpie.adapters.HotelMessageAdapter;
import com.foodmagpie.api.ApiService;
import com.foodmagpie.api.RetroClient;
import com.foodmagpie.model.Chat;
import com.foodmagpie.model.ResponseData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotelMessageActivity extends AppCompatActivity {

    String frm;
    String eto,hid;
    String pid = "3";
    List<Chat> msg = new ArrayList<Chat>();

    EditText et_message;
    ProgressDialog pd;
    Button btnsend;
    HotelMessageAdapter hotelMessageAdapter;
    Runnable r;
    RecyclerView recyclerView;
    Handler h = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_message);
        et_message = (EditText) findViewById(R.id.msgtext);
        btnsend = (Button) findViewById(R.id.btnsend);
        frm = getIntent().getStringExtra("from");
        eto = getIntent().getStringExtra("to");
        hid = getIntent().getStringExtra("hid");
        //   Toast.makeText(this, frm, Toast.LENGTH_LONG).show();
        String email = frm;
        String[] parts = email.split("@");

        // now parts[0] contains "example"
        // and parts[1] contains "domain.com"

        setTitle(parts[0]);
        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_message.getText().toString().isEmpty()) {
                    Toast.makeText(HotelMessageActivity.this, "Enter message", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(HotelMessageActivity.this, frm + eto, Toast.LENGTH_SHORT).show();
                sendMessage(getIntent().getStringExtra("to"), getIntent().getStringExtra("from"),getIntent().getStringExtra("hid"));
            }
        });

        final RecyclerView recyclerView = findViewById(R.id.messages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        hotelMessageAdapter = new HotelMessageAdapter(msg, frm, HotelMessageActivity.this);
        recyclerView.setAdapter(hotelMessageAdapter);

        r = new Runnable() {
            @Override
            public void run() {
                h.postDelayed(r, 9000);
                getmessages();
            }
        };

        h.post(r);


    }

    public void getmessages() {
        ApiService apiService = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<Chat>> call = apiService.getUserChat(frm, eto);
        call.enqueue(new Callback<List<Chat>>() {
            @Override
            public void onResponse(Call<List<Chat>> call, Response<List<Chat>> response) {
                if (response.body() == null) {
                    Toast.makeText(HotelMessageActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                } else {
                    msg = response.body();
                    hotelMessageAdapter.data(msg);
                }
            }

            @Override
            public void onFailure(Call<List<Chat>> call, Throwable t) {

            }
        });
    }

    public void sendMessage(final String frm, final String eto, final String hid) {

        ApiService apiService = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseData> call = apiService.msglist(frm, eto,hid, et_message.getText().toString());

        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                getmessages();
                et_message.setText("");
                if (response.body().message.toString().equals("true")) {
                    Toast.makeText(HotelMessageActivity.this, response.body().message.toString(), Toast.LENGTH_LONG).show();
                    et_message.setText("");
                } else {
                    Toast.makeText(HotelMessageActivity.this, response.body().message.toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
            }
        });
    }
}