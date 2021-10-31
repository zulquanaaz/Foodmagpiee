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

import com.foodmagpie.ChatHotelActivity;
import com.foodmagpie.R;
import com.foodmagpie.UserHomeActivity;
import com.foodmagpie.Utils;
import com.foodmagpie.api.ApiService;
import com.foodmagpie.api.RetroClient;
import com.foodmagpie.model.DonationPojo;
import com.foodmagpie.model.RequestsPojo;
import com.foodmagpie.model.ResponseData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserMyRequestsAdapter extends BaseAdapter {
    List<RequestsPojo> requestPojo;
    Context con;
    String url= "http://covidinformation.live/food/";
    ProgressDialog progressDialog;

    SharedPreferences sharedPreferences;
    String session;

    public UserMyRequestsAdapter(List<RequestsPojo> requestPojo, Context con) {
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
        View req = obj.inflate(R.layout.child_usermyrequests, null);


        TextView tvhname = (TextView) req.findViewById(R.id.tvhname);
        tvhname.setText("Hotel Name : " +requestPojo.get(pos).getHname());

        TextView tvfoodname = (TextView) req.findViewById(R.id.tvfoodname);
        tvfoodname.setText("Food Name : " +requestPojo.get(pos).getFoodname());

        TextView tvtype = (TextView) req.findViewById(R.id.tvtype);
        tvtype.setText("Food Type : " +requestPojo.get(pos).getType());

        TextView tvquantity = (TextView) req.findViewById(R.id.tvquantity);
        tvquantity.setText("Quantity: " +requestPojo.get(pos).getQuantity());

        TextView tvstatus = (TextView) req.findViewById(R.id.tvstatus);
        tvstatus.setText("Status : " +requestPojo.get(pos).getStatus());

        final TextView tvemail = (TextView) req.findViewById(R.id.tvemail);
        tvemail.setText("Email : " +requestPojo.get(pos).getEmail());



        sharedPreferences = con.getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("user_name", "def-val");

        TextView tvchatnow = (TextView) req.findViewById(R.id.tvchatnow);

        if(requestPojo.get(pos).getStatus().equals("Confirmed"))
        {
            tvchatnow.setVisibility(View.VISIBLE);
            tvchatnow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(con, ChatHotelActivity.class);
                    intent.putExtra("hid",requestPojo.get(pos).getHname());
                    intent.putExtra("from",session);
                    intent.putExtra("to",requestPojo.get(pos).getEmail());
                    con.startActivity(intent);
                }
            });
        }
        else
        {
            tvchatnow.setVisibility(View.GONE);
        }



        return req;
    }
}