package com.foodmagpie.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.foodmagpie.CharityDonationsListActivity;
import com.foodmagpie.DonationsListActivity;
import com.foodmagpie.R;
import com.foodmagpie.model.HotelPojo;

import java.util.List;

public class CharityHotelsListAdapter extends BaseAdapter {
    List<HotelPojo> hotel;
    Context con;
    String url= "http://covidinformation.live/food/";
    ProgressDialog progressDialog;

    public CharityHotelsListAdapter(List<HotelPojo> hotel, Context con) {
        this.hotel = hotel;
        this.con = con;
    }

    @Override
    public int getCount() {
        return hotel.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int pos, View view, ViewGroup viewGroup) {
        LayoutInflater obj = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View hotels = obj.inflate(R.layout.child_charityhotelslist, null);

        ImageView image=(ImageView)hotels.findViewById(R.id.image);
        Glide.with(con).load(url+hotel.get(pos).getPhoto()).into(image);


        TextView tveventname = (TextView) hotels.findViewById(R.id.tvhotelname);
        tveventname.setText(hotel.get(pos).getName());

        TextView tvphone = (TextView) hotels.findViewById(R.id.tvphone);
        //tvphone.setText(hotel.get(pos).getPhone());
        tvphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phone = hotel.get(pos).getPhone();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                con.startActivity(intent);
            }
        });

        TextView tvemail = (TextView) hotels.findViewById(R.id.tvemail);
        tvemail.setText(hotel.get(pos).getEmail());

        TextView tvaddress = (TextView) hotels.findViewById(R.id.tvaddress);
        tvaddress.setText(hotel.get(pos).getAddress());


        CardView carddonationDetails=(CardView)hotels.findViewById(R.id.carddonationDetails);
        carddonationDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(con, CharityDonationsListActivity.class);
                intent.putExtra("name",hotel.get(pos).getName());
                con.startActivity(intent);
            }
        });


        return hotels;
    }

}