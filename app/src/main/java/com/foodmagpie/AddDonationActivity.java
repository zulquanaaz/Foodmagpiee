package com.foodmagpie;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.foodmagpie.api.ApiService;
import com.foodmagpie.api.RetroClient;
import com.foodmagpie.model.HotelPojo;
import com.foodmagpie.model.ResponseData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDonationActivity extends AppCompatActivity {

    EditText etfoodname,etquantity;
    TextView tv_date;
    Button btndonate;
    int mYear,mMonth,mDay;
    String DAY,MONTH,YEAR;
    SharedPreferences sharedPreferences;
    String session;
    Spinner sphotel,sptype;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_donation);
        getSupportActionBar().setTitle("Donate food");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("user_name", "def-val");

        etfoodname=(EditText)findViewById(R.id.etfoodname);
        etquantity=(EditText)findViewById(R.id.etquantity);
        sphotel=(Spinner) findViewById(R.id.sphotel);
        sptype=(Spinner) findViewById(R.id.sptype);
        btndonate=(Button) findViewById(R.id.btndonate);
        btndonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((etfoodname.getText().toString().isEmpty())){
                    Toast.makeText(getApplicationContext(), "Enter Foodname", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(etquantity.getText().toString().isEmpty()){
                    Toast.makeText(AddDonationActivity.this, "Enter Quantity", Toast.LENGTH_SHORT).show();
                    return;
                }

                else {

                        progress = new ProgressDialog(AddDonationActivity.this);
                        progress.setMessage("Adding please wait");
                        progress.show();


                        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
                        Call<ResponseData> call = service.donatenow(etfoodname.getText().toString(), etquantity.getText().toString(), sptype.getSelectedItem().toString(), sphotel.getSelectedItem().toString(), session);

                        call.enqueue(new Callback<ResponseData>() {
                            @Override
                            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                                progress.dismiss();
                                if (response.body().status.equals("true")) {
                                    Toast.makeText(AddDonationActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(AddDonationActivity.this, HotelHomeActivity.class);
                                    startActivity(intent);

                                } else {
                                    Toast.makeText(AddDonationActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseData> call, Throwable t) {
                                progress.dismiss();
                                Toast.makeText(AddDonationActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });


                }

            }
        });

        getHotelNames();
    }




    ArrayList<String> hid;
    private void getHotelNames() {
        ApiService apiService = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<List<HotelPojo>> call = apiService.myhotels(session);
        call.enqueue(new Callback<List<HotelPojo>>() {
            @Override
            public void onResponse(Call<List<HotelPojo>> call, Response<List<HotelPojo>> response) {
             //   Toast.makeText(AddDonationActivity.this, response.toString(), Toast.LENGTH_SHORT).show();

                if (response.body()!=null) {
                    final List<HotelPojo> name=response.body();
                    if(name!=null) {
                        if(name.size()>0) {
                            ArrayList<String> cname = new ArrayList<String>();
                            hid = new ArrayList<String>();

                            for (int i = 0; i < name.size(); i++) {
                                cname.add(name.get(i).getName());
                                hid.add(name.get(i).getName());
                            }
                            ArrayAdapter<String> adp = new ArrayAdapter<String>(AddDonationActivity.this, android.R.layout.simple_spinner_dropdown_item, cname);
                            sphotel.setAdapter(adp);
                            sphotel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                 //   Toast.makeText(AddDonationActivity.this, ""+hid, Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                    //serverData();

                                }
                            });


                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<HotelPojo>> call, Throwable t) {
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