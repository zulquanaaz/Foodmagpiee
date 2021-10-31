package com.foodmagpie.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.foodmagpie.model.RequestsPojo;
import com.foodmagpie.model.ResponseData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotelMyRequestsAdapter extends BaseAdapter {
    List<RequestsPojo> requestPojo;
    Context con;
    String url= "http://covidinformation.live/food/";
    ProgressDialog progressDialog;

    SharedPreferences sharedPreferences;
    String session;

    public HotelMyRequestsAdapter(List<RequestsPojo> requestPojo, Context con) {
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
        View req = obj.inflate(R.layout.child_hotelmyrequests, null);


        TextView tvhname = (TextView) req.findViewById(R.id.tvhname);
        tvhname.setText("Hotel Name : " +requestPojo.get(pos).getHname());

        TextView tvusername = (TextView) req.findViewById(R.id.tvusername);
        tvusername.setText("Name : " +requestPojo.get(pos).getUser());


        TextView tvfoodname = (TextView) req.findViewById(R.id.tvfoodname);
        tvfoodname.setText("Food Name : " +requestPojo.get(pos).getFoodname());

        TextView tvtype = (TextView) req.findViewById(R.id.tvtype);
        tvtype.setText("Food Type : " +requestPojo.get(pos).getType());

        TextView tvquantity = (TextView) req.findViewById(R.id.tvquantity);
        tvquantity.setText("Quantity: " +requestPojo.get(pos).getQuantity());

        TextView tvstatus = (TextView) req.findViewById(R.id.tvstatus);
        tvstatus.setText("Status : " +requestPojo.get(pos).getStatus());

        sharedPreferences = con.getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("user_name", "def-val");

        TextView tvaccept = (TextView) req.findViewById(R.id.tvaccept);
        tvaccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(con);
                progressDialog.setMessage("Processing Request");
                progressDialog.show();
                ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
                Call<ResponseData> call = service.acceptrequest(requestPojo.get(pos).getRid());
                call.enqueue(new Callback<ResponseData>() {
                    @Override
                    public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                        progressDialog.dismiss();
                        if(response.body()==null){
                            Toast.makeText(con,"Server issue",Toast.LENGTH_SHORT).show();
                        }else {
                            Intent intent=new Intent(con, HotelHomeActivity.class);
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
                Call<ResponseData> call = service.declinerequest(requestPojo.get(pos).getRid());
                call.enqueue(new Callback<ResponseData>() {
                    @Override
                    public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                        progressDialog.dismiss();
                        if(response.body()==null){
                            Toast.makeText(con,"Server issue",Toast.LENGTH_SHORT).show();
                        }else {
                            Intent intent=new Intent(con, HotelHomeActivity.class);
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