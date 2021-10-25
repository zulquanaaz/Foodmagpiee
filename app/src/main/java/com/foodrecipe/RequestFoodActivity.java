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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.foodmagpie.api.ApiService;
import com.foodmagpie.api.RetroClient;
import com.foodmagpie.model.ResponseData;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//Request Food Activity Class extend from AppCompatActivity
public class RequestFoodActivity extends AppCompatActivity {

    EditText etname, etemail, etphone, etmessage;
    Button btnask;

    Spinner sptype;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_food);
        getSupportActionBar().setTitle("Request Food");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("user_name", "def-val");
        etname =(EditText)findViewById(R.id.etname);
        etemail = (EditText)findViewById(R.id.etemail);
        etemail.setText(email);
        etphone = (EditText)findViewById(R.id.etphone);
        etmessage = (EditText)findViewById(R.id.etmessage);

        btnask = (Button)findViewById(R.id.btnask);
        btnask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ((etmessage.getText().toString().isEmpty())) {
                    Toast.makeText(getApplicationContext(), "Message not empty", Toast.LENGTH_SHORT).show();
                    return;
                } else if (etname.getText().toString().isEmpty()) {
                    Toast.makeText(RequestFoodActivity.this, "Enter Name", Toast.LENGTH_SHORT).show();
                    return;
                } else if (etemail.getText().toString().isEmpty()) {
                    Toast.makeText(RequestFoodActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                } else if (etphone.getText().toString().isEmpty() || etphone.length() < 10) {
                    Toast.makeText(RequestFoodActivity.this, "Enter Phone or Invalid Phone format", Toast.LENGTH_SHORT).show();
                    return;
                } else {

                    progress = new ProgressDialog(RequestFoodActivity.this);
                    progress.setMessage("Adding please wait");
                    progress.show();


                    ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
                    Call<ResponseData> call = service.requestfood(etname.getText().toString(), etemail.getText().toString(), etmessage.getText().toString(), etphone.getText().toString(), getIntent().getStringExtra("cid"));

                    call.enqueue(new Callback<ResponseData>() {
                        @Override
                        public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                            progress.dismiss();
                            if (response.body().status.equals("true")) {
                                Toast.makeText(RequestFoodActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(RequestFoodActivity.this, UserHomeActivity.class);
                                startActivity(intent);

                            } else {
                                Toast.makeText(RequestFoodActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                            }
                        }

                        //Method Called on Failure
                        @Override
                        public void onFailure(Call<ResponseData> call, Throwable t) {
                            progress.dismiss();
                            Toast.makeText(RequestFoodActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

//Displays items selected
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
