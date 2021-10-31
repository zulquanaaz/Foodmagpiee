package com.foodmagpie.model;

import com.google.gson.annotations.SerializedName;

public class RequestsPojo {
    @SerializedName("hid")
    private String hid;

    @SerializedName("rid")
    private String rid;

    @SerializedName("user")
    private String user;

    @SerializedName("hname")
    private String hname;

    @SerializedName("did")
    private String did;

    @SerializedName("foodname")
    private String foodname;

    @SerializedName("type")
    private String type;

    @SerializedName("quantity")
    private String quantity;

    @SerializedName("email")
    private String email;

    @SerializedName("status")
    private String status;

    public String getHid() {
        return hid;
    }

    public void setHid(String hid) {
        this.hid = hid;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHname() {
        return hname;
    }

    public void setHname(String hname) {
        this.hname = hname;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
