package com.foodmagpie.model;

import com.google.gson.annotations.SerializedName;

public class Chat {

    @SerializedName("chat_id")
    private
    String chat_id;
    @SerializedName("message")
    private
    String message;
    @SerializedName("frm")
    private
    String frm;
    @SerializedName("eto")
    private
    String eto;
    @SerializedName("hid")
    private
    String hid;

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFrm() {
        return frm;
    }

    public void setFrm(String frm) {
        this.frm = frm;
    }

    public String getEto() {
        return eto;
    }

    public void setEto(String eto) {
        this.eto = eto;
    }


    public String getHid() {
        return hid;
    }

    public void setHid(String hid) {
        this.hid = hid;
    }
}
