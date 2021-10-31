package com.foodmagpie;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.foodmagpie.api.ApiService;
import com.foodmagpie.api.RetroClient;
import com.foodmagpie.model.ResponseData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    ProgressDialog progress;
    EditText etemail, etpass;
    Button btnlogin;
    TextView tvforgotpass, tvsignup;
    Spinner sptype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etemail = findViewById(R.id.etemail);
        etpass = findViewById(R.id.etpass);
        btnlogin = findViewById(R.id.btnlogin);
        tvforgotpass = findViewById(R.id.tvforgotpass);
        sptype=(Spinner)findViewById(R.id.sptype);

        tvforgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent s=new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                startActivity(s);
            }
        });
        tvsignup = findViewById(R.id.tvsignup);
        tvsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent s=new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(s);
            }
        });


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etemail.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(etpass.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {

                    if(sptype.getSelectedItem().toString().equals("Hotel"))
                    {
                        //progreess bar loading
                        progress= new ProgressDialog(LoginActivity.this);
                        progress.setTitle("Please wait,Data is being submit...");
                        progress.show();
                        // api call code
                        ApiService apiService = RetroClient.getRetrofitInstance().create(ApiService.class);
                        Call<ResponseData> call = apiService.hotellogin(etemail.getText().toString(),etpass.getText().toString(),sptype.getSelectedItem().toString());
                        call.enqueue(new Callback<ResponseData>() {
                            @Override
                            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                                progress.dismiss();
                                if (response.body().status.equals("true")) {
                                    SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor et = sharedPreferences.edit();
                                    et.putString("user_name", etemail.getText().toString());
                                     et.putString("type", "Hotel");
                                    et.commit();
                                    startActivity(new Intent(LoginActivity.this, HotelHomeActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                                }
                            }
                            @Override
                            public void onFailure(Call<ResponseData> call, Throwable t) {
                                progress.dismiss();
                                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    else if (sptype.getSelectedItem().toString().equals("Charity"))
                    {
                        //progreess bar loading
                        progress= new ProgressDialog(LoginActivity.this);
                        progress.setTitle("Please wait,Data is being submit...");
                        progress.show();
                        // api call code
                        ApiService apiService = RetroClient.getRetrofitInstance().create(ApiService.class);
                        Call<ResponseData> call = apiService.charitylogin(etemail.getText().toString(),etpass.getText().toString(),sptype.getSelectedItem().toString());
                        call.enqueue(new Callback<ResponseData>() {
                            @Override
                            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                                progress.dismiss();
                                if (response.body().status.equals("true")) {
                                    SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor et = sharedPreferences.edit();
                                    et.putString("user_name", etemail.getText().toString());
                                     et.putString("type", "Charity");
                                    et.commit();
                                    startActivity(new Intent(LoginActivity.this, CharityhomeActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                                }
                            }
                            @Override
                            public void onFailure(Call<ResponseData> call, Throwable t) {
                                progress.dismiss();
                                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    else
                    {
                        //progreess bar loading
                        progress= new ProgressDialog(LoginActivity.this);
                        progress.setTitle("Please wait,Data is being submit...");
                        progress.show();
                        // api call code
                        ApiService apiService = RetroClient.getRetrofitInstance().create(ApiService.class);
                        Call<ResponseData> call = apiService.login(etemail.getText().toString(),etpass.getText().toString(),sptype.getSelectedItem().toString());
                        call.enqueue(new Callback<ResponseData>() {
                            @Override
                            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                                progress.dismiss();
                                if (response.body().status.equals("true")) {
                                    SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor et = sharedPreferences.edit();
                                    et.putString("user_name", etemail.getText().toString());
                                     et.putString("type", "User");
                                    et.commit();
                                    startActivity(new Intent(LoginActivity.this, UserHomeActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                                }
                            }
                            @Override
                            public void onFailure(Call<ResponseData> call, Throwable t) {
                                progress.dismiss();
                                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                }
            }
        });
    }

    private void restartActivity() {
        Intent intent = getIntent();
        startActivity(intent);
        finish();
    }
}