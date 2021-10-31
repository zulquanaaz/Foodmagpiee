package com.foodmagpie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class HotelHomeActivity extends AppCompatActivity {

    CardView cdhotels,cddonations,cdchat,cdrequests;
    Button btnlogout;
    ImageView imgprofile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_home);
        getSupportActionBar().setTitle("Hotel Dashboard");

        imgprofile=(ImageView)findViewById(R.id.imgprofile);
        imgprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(HotelHomeActivity.this,HotelProfileActivity.class);
                startActivity(i);
            }
        });

        btnlogout=(Button)findViewById(R.id.btnlogout);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(HotelHomeActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });

        cdhotels=(CardView)findViewById(R.id.cdhotels);
        cdhotels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(HotelHomeActivity.this,MyHotelsActivity.class);
                startActivity(i);
            }
        });

        cddonations=(CardView)findViewById(R.id.cddonations);
        cddonations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(HotelHomeActivity.this,MyDonationsActivity.class);
                startActivity(i);
            }
        });

        cdchat=(CardView)findViewById(R.id.cdchat);
        cdchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(HotelHomeActivity.this,HotelChatActivity.class);
                startActivity(i);
            }
        });
        cdrequests=(CardView)findViewById(R.id.cdrequests);
        cdrequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(HotelHomeActivity.this,HotelMyRequestsActivity.class);
                startActivity(i);
            }
        });
    }
}