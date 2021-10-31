package com.foodmagpie.api;


import com.foodmagpie.model.Chat;
import com.foodmagpie.model.DonationPojo;
import com.foodmagpie.model.FoodRequest;
import com.foodmagpie.model.HotelPojo;
import com.foodmagpie.model.ProfilePojo;
import com.foodmagpie.model.RequestsPojo;
import com.foodmagpie.model.ResponseData;


import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;


public interface ApiService {

    @GET("/food/registration.php?")
    Call<ResponseData> registration(
            @Query("name") String name,
            @Query("email") String email,
            @Query("pass") String pass,
            @Query("phone") String phone,
            @Query("type") String type);

    @GET("/food/login.php?")
    Call<ResponseData> login(
            @Query("email") String email,
            @Query("pass") String pass,
            @Query("type") String type
            );


    @GET("/food/reset.php?")
    Call<ResponseData> reset(
            @Query("email") String email,
            @Query("pass") String pass
    );



    @GET("/food/hotellogin.php?")
    Call<ResponseData> hotellogin(
            @Query("email") String email,
            @Query("pass") String pass,
            @Query("type") String type
    );

    @GET("/food/charitylogin.php?")
    Call<ResponseData> charitylogin(
            @Query("email") String email,
            @Query("pass") String pass,
            @Query("type") String type
    );

    @GET("/food/myprofile.php?")
    Call<List<ProfilePojo>> myprofile(
            @Query("email") String email);


    @GET("/food/updateprofile.php?")
    Call<ResponseData> updateprofile(
            @Query("name") String name,
            @Query("email") String email,
            @Query("phone") String phone,
            @Query("pass") String pass);


    @GET("/food/adddonation.php?")
    Call<ResponseData> donatenow(
            @Query("foodname") String foodname,
            @Query("quantity") String quantity,
            @Query("type") String type,
            @Query("hid") String hid,
            @Query("email") String email);



    @GET("/food/updatedonation.php?")
    Call<ResponseData> updatedonation(
            @Query("foodname") String foodname,
            @Query("quantity") String quantity,
            @Query("type") String type,
            @Query("hname") String hname,
            @Query("id") String id);


    @Multipart
    @POST("food/addhotel.php")
    Call<ResponseData> addhotel(
            @Part MultipartBody.Part file,
            @PartMap Map<String, String> partMap
    );



    @Multipart
    @POST("food/addcharity.php")
    Call<ResponseData> addcharity(
            @Part MultipartBody.Part file,
            @PartMap Map<String, String> partMap
    );


    @GET("food/myorganisations.php?")
    Call<List<HotelPojo>> myorganisations(@Query("email") String email);


    @GET("/food/updateHotel.php?")
    Call<ResponseData> updateHotel(
            @Query("id") String id,
            @Query("name") String name,
            @Query("phone") String phone,
            @Query("address") String address
            );



    @GET("/food/updateCharity.php?")
    Call<ResponseData> updateCharity(
            @Query("id") String id,
            @Query("name") String name,
            @Query("phone") String phone,
            @Query("address") String address
    );

    @GET("/food/deletehotel.php")
    Call<ResponseData> deletehotel(@Query("id") String id);

    @GET("/food/deletecharity.php")
    Call<ResponseData> deletecharity(@Query("id") String id);

    @GET("/food/deletedonation.php")
    Call<ResponseData> deletedonation(@Query("id") String id);


    @GET("food/myhotels.php?")
    Call<List<HotelPojo>> myhotels(@Query("email") String email);

    @GET("food/getmydonations.php?")
    Call<List<DonationPojo>> getmydonations(@Query("email") String email);

    @GET("food/getHotels.php?")
    Call<List<HotelPojo>> getHotels();

    @GET("food/getcharity.php?")
    Call<List<HotelPojo>> getcharity();


    @GET("food/getDonations.php?")
    Call<List<DonationPojo>> getDonations(
            @Query("hname") String hname
    );

    @GET("/food/request.php?")
    Call<ResponseData> request(
            @Query("did") String did,
            @Query("user") String user
    );

    @GET("food/getmyrequests.php?")
    Call<List<RequestsPojo>> getmyrequests(
            @Query("email") String email
    );


    @GET("food/getmycharityrequests.php?")
    Call<List<FoodRequest>> getmycharityrequests(
            @Query("email") String email
    );

    @GET("food/getcharityrequests.php?")
    Call<List<FoodRequest>> getcharityrequests(
            @Query("email") String email
    );


    @GET("food/getmyuserrequests.php?")
    Call<List<RequestsPojo>> getmyuserrequests(
            @Query("email") String email
    );


    @GET("food/acceptrequest.php?")
    Call<ResponseData> acceptrequest(
            @Query("id") String id
    );


    @GET("food/declinerequest.php?")
    Call<ResponseData> declinerequest(
            @Query("id") String id
    );

    @GET("food/acceptfoodrequest.php?")
    Call<ResponseData> acceptfoodrequest(
            @Query("id") String id
    );


    @GET("food/declinefoodrequest.php?")
    Call<ResponseData> declinefoodrequest(
            @Query("id") String id
    );


    @GET("/food/chat.php")
    Call<ResponseData> msglist(@Query("frm") String frm,
                               @Query("eto") String eto,
                               @Query("hid") String hid,
                               @Query("message") String message);


    @GET("/food/getchat.php")
    Call<List<Chat>> getchat(@Query("frm") String from,
                             @Query("eto") String to,
                             @Query("hid") String hid);


    @GET("/food/hotelchat.php?")
    Call<List<Chat>> hotelchat(@Query("email") String email);


    @GET("/food/getUserChat.php?")
    Call<List<Chat>> getUserChat(@Query("frm") String from,
                                        @Query("eto") String to);




    @GET("/food/requestfood.php?")
    Call<ResponseData> requestfood(
            @Query("name") String name,
            @Query("email") String email,
            @Query("msg") String msg,
            @Query("phone") String phone,
            @Query("cid") String cid);


}
