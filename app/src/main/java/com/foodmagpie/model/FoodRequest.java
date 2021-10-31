package com.foodmagpie.model;

import com.google.gson.annotations.SerializedName;

public class FoodRequest {
    @SerializedName("fid")
    private String fid;

    @SerializedName("name")
    private String name;

    @SerializedName("uemail")
    private String uemail;

    @SerializedName("phone")
    private String phone;

    @SerializedName("message")
    private String message;

    @SerializedName("cid")
    private String cid;

    @SerializedName("cname")
    private String cname;

    @SerializedName("status")
    private String status;



    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUemail() {
        return uemail;
    }

    public void setUemail(String uemail) {
        this.uemail = uemail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
