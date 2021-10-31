package com.foodmagpie.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.foodmagpie.EditCharityActivity;
import com.foodmagpie.EditHotelActivity;
import com.foodmagpie.HotelHomeActivity;
import com.foodmagpie.R;
import com.foodmagpie.api.ApiService;
import com.foodmagpie.api.RetroClient;
import com.foodmagpie.model.HotelPojo;
import com.foodmagpie.model.ResponseData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCharityAdapter extends BaseAdapter {
    List<HotelPojo> hotel;
    Context con;
    String url= "http://covidinformation.live/food/";
    ProgressDialog progressDialog;

    public MyCharityAdapter(List<HotelPojo> hotel, Context con) {
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
        View hotels = obj.inflate(R.layout.child_myhotel, null);

        ImageView image=(ImageView)hotels.findViewById(R.id.image);
        Glide.with(con).load(url+hotel.get(pos).getPhoto()).into(image);

        TextView tveventname = (TextView) hotels.findViewById(R.id.tvhotelname);
        tveventname.setText(hotel.get(pos).getCname());

        TextView tvphone = (TextView) hotels.findViewById(R.id.tvphone);
        tvphone.setText(hotel.get(pos).getPhone());

        TextView tvemail = (TextView) hotels.findViewById(R.id.tvemail);
        tvemail.setText(hotel.get(pos).getEmail());

        TextView tvaddress = (TextView) hotels.findViewById(R.id.tvaddress);
        tvaddress.setText(hotel.get(pos).getAddress());

        ImageView imgedit = (ImageView) hotels.findViewById(R.id.imgedit);
        imgedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(con, EditCharityActivity.class);
                intent.putExtra("hid",hotel.get(pos).getCid());
                intent.putExtra("name",hotel.get(pos).getCname());
                intent.putExtra("phone",hotel.get(pos).getPhone());
                intent.putExtra("address",hotel.get(pos).getAddress());
                intent.putExtra("photo",hotel.get(pos).getPhoto());
                con.startActivity(intent);
            }
        });

        ImageView imgdelete = (ImageView) hotels.findViewById(R.id.imgdelete);
        imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(con);
                progressDialog.setMessage("Deleting Data");
                progressDialog.show();
                ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
                Call<ResponseData> call = service.deletecharity(hotel.get(pos).getCid());
                call.enqueue(new Callback<ResponseData>() {
                    @Override
                    public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                        progressDialog.dismiss();
                        if(response.body()==null){
                            Toast.makeText(con,"Server issue",Toast.LENGTH_SHORT).show();
                        }else {
                            Intent intent=new Intent(con, HotelHomeActivity.class);
                            con.startActivity(intent);
                            Toast.makeText(con," Deleted successfully",Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseData> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(con, "Server went wrong Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return hotels;
    }

}