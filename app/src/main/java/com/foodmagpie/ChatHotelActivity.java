package com.foodmagpie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.foodmagpie.adapters.ChatAdapter;
import com.foodmagpie.api.ApiService;
import com.foodmagpie.api.RetroClient;
import com.foodmagpie.model.Chat;
import com.foodmagpie.model.ResponseData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatHotelActivity extends AppCompatActivity {

    String frm;
    String eto;
    List<Chat> msg = new ArrayList<Chat>();
    ApiService apiService;
    EditText msgtext;
    ProgressDialog pd;
    ChatAdapter messagesadapter;
    Button send;
    Runnable r;
    RecyclerView recyclerView;
    Handler h = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_hotel);

        getSupportActionBar().setTitle(getIntent().getStringExtra("hid") + "Hotel");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        msgtext = (EditText) findViewById(R.id.msgtext);
        send = (Button) findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (msgtext.getText().toString().isEmpty()) {
                    Toast.makeText(ChatHotelActivity.this, "Please enter message", Toast.LENGTH_SHORT).show();
                }
                sendMessage(getIntent().getStringExtra("from"), getIntent().getStringExtra("hid"));
            }
        });

        frm = getIntent().getStringExtra("from");
        eto = getIntent().getStringExtra("to");
        // Log.d("tomessage", frm + "" + eto);
        recyclerView = findViewById(R.id.messages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        messagesadapter = new ChatAdapter(msg, frm, ChatHotelActivity.this);
        recyclerView.setAdapter(messagesadapter);

        r = new Runnable() {
            @Override
            public void run() {
                h.postDelayed(r, 10000);
                getChat();
            }
        };

        h.post(r);


    }

    public void getChat() {
        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<Chat>> call = service.getchat(frm, eto, getIntent().getStringExtra("hid"));
        call.enqueue(new Callback<List<Chat>>() {
            @Override
            public void onResponse(Call<List<Chat>> call, Response<List<Chat>> response) {
                if (response.body() == null) {
                    Toast.makeText(ChatHotelActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                } else {
                    // pd.dismiss();
                    Log.d("test", response.toString());
                    if (response.body().size() > 0) {
                        msg.clear();
                        msg.addAll(response.body());
                        Log.d("testing", msg.toString());
                        messagesadapter.notifyDataSetChanged();
                        recyclerView.smoothScrollToPosition(msg.size() - 1);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Chat>> call, Throwable t) {

            }
        });
    }

    public void sendMessage(final String frm, final String hid) {

        ApiService apiService = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseData> call = apiService.msglist(frm,eto, hid, msgtext.getText().toString());
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                getChat();
                msgtext.setText("");

                if (response.body().message.equals("true")) {
                    // pd.dismiss();
                    Toast.makeText(ChatHotelActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                    // Log.i("msg", "" + response.body().message);
                    msgtext.setText("");
                    // finish();
                } else {
                    Toast.makeText(ChatHotelActivity.this, response.body().message, Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Log.d("TAG", "Response = " + t.toString());
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