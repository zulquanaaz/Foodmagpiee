package com.foodmagpie.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.foodmagpie.EditDonationActivity;
import com.foodmagpie.HotelHomeActivity;
import com.foodmagpie.R;
import com.foodmagpie.UserHomeActivity;
import com.foodmagpie.Utils;
import com.foodmagpie.api.ApiService;
import com.foodmagpie.api.RetroClient;
import com.foodmagpie.model.DonationPojo;
import com.foodmagpie.model.ResponseData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonationsListAdapter extends BaseAdapter {
    List<DonationPojo> donation;
    Context con;
    String url= "http://covidinformation.live/food/";
    ProgressDialog progressDialog;

    SharedPreferences sharedPreferences;
    String session;

    public DonationsListAdapter(List<DonationPojo> donation, Context con) {
        this.donation = donation;
        this.con = con;
    }

    @Override
    public int getCount() {
        return donation.size();
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
        View donations = obj.inflate(R.layout.child_donationslist, null);

        TextView tvfoodname = (TextView) donations.findViewById(R.id.tvfoodname);
        tvfoodname.setText("Food Name : " +donation.get(pos).getFoodname());

        TextView tvtype = (TextView) donations.findViewById(R.id.tvtype);
        tvtype.setText("Food Type : " +donation.get(pos).getType());

        TextView tvquantity = (TextView) donations.findViewById(R.id.tvquantity);
        tvquantity.setText("Quantity: " +donation.get(pos).getQuantity());

        TextView tvstatus = (TextView) donations.findViewById(R.id.tvstatus);
        tvstatus.setText("Status : " +donation.get(pos).getStatus());

        sharedPreferences = con.getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("user_name", "def-val");

        TextView btnrequest=(TextView) donations.findViewById(R.id.btnrequest);
        btnrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(con);
                progressDialog.setMessage("Please wait");
                progressDialog.show();
                ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
                Call<ResponseData> call = service.request(donation.get(pos).getDid(),session);
                call.enqueue(new Callback<ResponseData>() {
                    @Override
                    public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                        progressDialog.dismiss();
                        if (response.body() == null) {
                            Toast.makeText(con, "Server issue", Toast.LENGTH_SHORT).show();
                        } else if(response.body().status=="true"){
                            Intent intent = new Intent(con, UserHomeActivity.class);
                            con.startActivity(intent);
                            Toast.makeText(con, response.body().message.toString(), Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Intent intent = new Intent(con, UserHomeActivity.class);
                            con.startActivity(intent);
                            Toast.makeText(con, response.body().message.toString(), Toast.LENGTH_SHORT).show();
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

        return donations;
    }

}