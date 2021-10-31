package com.foodmagpie;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.foodmagpie.api.ApiService;
import com.foodmagpie.api.RetroClient;
import com.foodmagpie.model.ResponseData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditCharityActivity extends AppCompatActivity {

    EditText etaddress,etname,etphone;
    private Uri uri;
    Button btnsubmit,btnimageupload;
    ProgressDialog progress;
    ImageView image;
    String url= "http://covidinformation.live/food/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_charity);
        etname=(EditText)findViewById(R.id.etname);
        etphone=(EditText)findViewById(R.id.etphone);
        etaddress=(EditText)findViewById(R.id.etaddress);
        image=(ImageView)findViewById(R.id.image);


        etname.setText(getIntent().getStringExtra("name"));
        etphone.setText(getIntent().getStringExtra("phone"));
        etaddress.setText(getIntent().getStringExtra("address"));

        image=(ImageView)findViewById(R.id.image);
        Glide.with(this).load(url+getIntent().getStringExtra("photo")).into(image);

        btnsubmit=(Button) findViewById(R.id.btnsubmit);
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id=getIntent().getStringExtra("hid");
                String name=etname.getText().toString();
                String phone=etphone.getText().toString();
                String address=etaddress.getText().toString();

                progress = new ProgressDialog(EditCharityActivity.this);
                progress.setMessage("Updating please wait");
                progress.show();
                ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
                Call<ResponseData> call = service.updateCharity(id,name,phone,address);
                call.enqueue(new Callback<ResponseData>() {
                    @Override
                    public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                        progress.dismiss();
                        if (response.body().status.equals("true")) {
                            Toast.makeText(EditCharityActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(EditCharityActivity.this, CharityhomeActivity.class);
                            startActivity(intent);
                            //  finish();

                        } else {
                            Toast.makeText(EditCharityActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseData> call, Throwable t) {
                        progress.dismiss();
                        Toast.makeText(EditCharityActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}