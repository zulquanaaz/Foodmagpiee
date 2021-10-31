package com.foodmagpie.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.foodmagpie.CharityhomeActivity;
import com.foodmagpie.HotelHomeActivity;
import com.foodmagpie.R;
import com.foodmagpie.Utils;
import com.foodmagpie.api.ApiService;
import com.foodmagpie.api.RetroClient;
import com.foodmagpie.model.FoodRequest;
import com.foodmagpie.model.RequestsPojo;
import com.foodmagpie.model.ResponseData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharityMyRequestsAdaper extends BaseAdapter {
    List<FoodRequest> requestPojo;
    Context con;
    String url= "http://covidinformation.live/food/";
    ProgressDialog progressDialog;

    SharedPreferences sharedPreferences;
    String session;

    public CharityMyRequestsAdaper(List<FoodRequest> requestPojo, Context con) {
        this.requestPojo = requestPojo;
        this.con = con;
    }

    @Override
    public int getCount() {
        return requestPojo.size();
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
        View req = obj.inflate(R.layout.child_charitymyrequests, null);


        TextView tvname = (TextView) req.findViewById(R.id.tvname);
        tvname.setText("Name : " +requestPojo.get(pos).getName());

        TextView tvmessage = (TextView) req.findViewById(R.id.tvmessage);
        tvmessage.setText("Has Been Requested for : " +requestPojo.get(pos).getMessage());

        TextView tvstatus = (TextView) req.findViewById(R.id.tvstatus);
        tvstatus.setText("Status : " +requestPojo.get(pos).getStatus());

        sharedPreferences = con.getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("user_name", "def-val");


        TextView tvcall = (TextView) req.findViewById(R.id.tvcall);
        tvcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phone = requestPojo.get(pos).getPhone();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                con.startActivity(intent);
            }
        });

        TextView tvaccept = (TextView) req.findViewById(R.id.tvaccept);
        tvaccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(con);
                progressDialog.setMessage("Processing Request");
                progressDialog.show();
                ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
                Call<ResponseData> call = service.acceptfoodrequest(requestPojo.get(pos).getFid());
                call.enqueue(new Callback<ResponseData>() {
                    @Override
                    public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                        progressDialog.dismiss();
                        if(response.body()==null){
                            Toast.makeText(con,"Server issue",Toast.LENGTH_SHORT).show();
                        }else {
                            Intent intent=new Intent(con, CharityhomeActivity.class);
                            con.startActivity(intent);
                            Toast.makeText(con," Accepted successfully",Toast.LENGTH_SHORT).show();
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


        TextView tvdecline = (TextView) req.findViewById(R.id.tvdecline);
        tvdecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(con);
                progressDialog.setMessage("Processing Request");
                progressDialog.show();
                ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
                Call<ResponseData> call = service.declinefoodrequest(requestPojo.get(pos).getFid());
                call.enqueue(new Callback<ResponseData>() {
                    @Override
                    public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                        progressDialog.dismiss();
                        if(response.body()==null){
                            Toast.makeText(con,"Server issue",Toast.LENGTH_SHORT).show();
                        }else {
                            Intent intent=new Intent(con, CharityhomeActivity.class);
                            con.startActivity(intent);
                            Toast.makeText(con," Decline successfully",Toast.LENGTH_SHORT).show();
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

        return req;
    }
}