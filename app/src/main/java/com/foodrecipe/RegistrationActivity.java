package com.foodmagpie;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class RegistrationActivity extends AppCompatActivity {

    EditText etname, etemail, etphone, etpass;
    Button btnregister;

    Spinner sptype;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().setTitle("Account Registration");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        etname =(EditText)findViewById(R.id.etname);
        etemail = (EditText)findViewById(R.id.etemail);
        etphone = (EditText)findViewById(R.id.etphone);
        etpass = (EditText)findViewById(R.id.etpass);
        sptype=(Spinner)findViewById(R.id.sptype);
        btnregister = (Button)findViewById(R.id.btnregister);
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if((etpass.getText().toString().isEmpty())){
                    Toast.makeText(getApplicationContext(), "Password not empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(etname.getText().toString().isEmpty()){
                    Toast.makeText(RegistrationActivity.this, "Enter Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(etemail.getText().toString().isEmpty()){
                    Toast.makeText(RegistrationActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(etphone.getText().toString().isEmpty() || etphone.length()<10){
                    Toast.makeText(RegistrationActivity.this, "Enter Phone or Invalid Phone format", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    if (isValidPassword(etpass.getText().toString())) {
                        progress = new ProgressDialog(RegistrationActivity.this);
                        progress.setMessage("Adding please wait");
                        progress.show();


                        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
                        Call<ResponseData> call = service.registration(etname.getText().toString(), etemail.getText().toString(), etpass.getText().toString(), etphone.getText().toString(), sptype.getSelectedItem().toString());

                        call.enqueue(new Callback<ResponseData>() {
                            @Override
                            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                                progress.dismiss();
                                if (response.body().status.equals("true")) {
                                    Toast.makeText(RegistrationActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                                    startActivity(intent);

                                } else {
                                    Toast.makeText(RegistrationActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseData> call, Throwable t) {
                                progress.dismiss();
                                Toast.makeText(RegistrationActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }else {

                        Toast.makeText(getApplicationContext(), "Password must contain 8 letters and special character", Toast.LENGTH_SHORT).show();

                    }

                }
            }
        });

    }

    public boolean isValidPassword(final String password){
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
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