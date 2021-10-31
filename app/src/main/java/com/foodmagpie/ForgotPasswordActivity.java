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
import android.widget.Toast;

import com.foodmagpie.api.ApiService;
import com.foodmagpie.api.RetroClient;
import com.foodmagpie.model.ResponseData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {
    ProgressDialog progress;
    EditText etemail, etpass,etcpass;
    Button btnreset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        etemail = findViewById(R.id.etemail);
        etpass = findViewById(R.id.etpass);
        etcpass = findViewById(R.id.etcpass);
        btnreset = findViewById(R.id.btnreset);


        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etemail.getText().toString().isEmpty()){
                    Toast.makeText(ForgotPasswordActivity.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(etpass.getText().toString().isEmpty()){
                    Toast.makeText(ForgotPasswordActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(etcpass.getText().toString().isEmpty()){
                    Toast.makeText(ForgotPasswordActivity.this, "Please Enter Confirm Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(etpass.getText().toString().equals(etcpass.getText().toString())){

                        //progreess bar loading
                        progress= new ProgressDialog(ForgotPasswordActivity.this);
                        progress.setTitle("Please wait, Data is being submit...");
                        progress.show();
                        // api call code
                        ApiService apiService = RetroClient.getRetrofitInstance().create(ApiService.class);
                        Call<ResponseData> call = apiService.reset(etemail.getText().toString(),etpass.getText().toString());
                        call.enqueue(new Callback<ResponseData>() {
                            @Override
                            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                                progress.dismiss();
                                if (response.body().status.equals("true")) {

                                    startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(ForgotPasswordActivity.this, "Failed", Toast.LENGTH_LONG).show();
                                }
                            }
                            @Override
                            public void onFailure(Call<ResponseData> call, Throwable t) {
                                progress.dismiss();
                                Toast.makeText(ForgotPasswordActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });

                }
                else
                {
                    Toast.makeText(ForgotPasswordActivity.this, "Password must match", Toast.LENGTH_LONG).show();

                }
            }
        });
    }

}