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

import com.foodmagpie.HotelHomeActivity;
import com.foodmagpie.R;
import com.foodmagpie.Utils;
import com.foodmagpie.api.ApiService;
import com.foodmagpie.api.RetroClient;
import com.foodmagpie.model.FoodRequest;
import com.foodmagpie.model.ResponseData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserMyCharityRequestsAdapter extends BaseAdapter {
    List<FoodRequest> requestPojo;
    Context con;
    String url= "http://covidinformation.live/food/";
    ProgressDialog progressDialog;

    SharedPreferences sharedPreferences;
    String session;

    public UserMyCharityRequestsAdapter(List<FoodRequest> requestPojo, Context con) {
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
        tvname.setText("Charity Name : " +requestPojo.get(pos).getCname());

        TextView tvmessage = (TextView) req.findViewById(R.id.tvmessage);
        tvmessage.setText("Requested for : " +requestPojo.get(pos).getMessage());

        TextView tvstatus = (TextView) req.findViewById(R.id.tvstatus);
        tvstatus.setText("Status : " +requestPojo.get(pos).getStatus());

        sharedPreferences = con.getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("user_name", "def-val");


        TextView tvcall = (TextView) req.findViewById(R.id.tvcall);
        tvcall.setVisibility(View.GONE);

        TextView tvaccept = (TextView) req.findViewById(R.id.tvaccept);
        tvaccept.setVisibility(View.GONE);

        TextView tvdecline = (TextView) req.findViewById(R.id.tvdecline);
        tvdecline.setVisibility(View.GONE);
        return req;
    }
}