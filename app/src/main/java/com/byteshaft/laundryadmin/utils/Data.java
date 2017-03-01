package com.byteshaft.laundryadmin.utils;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by s9iper1 on 2/27/17.
 */

public class Data {

    private JSONObject address;
    private int id;
    private JSONArray orderDetail;
    private int userId;
    private String laundryType;
    private String dropTime;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLaundryType() {
        return laundryType;
    }

    public void setLaundryType(String laundryType) {
        this.laundryType = laundryType;
    }


    public String getDropTime() {
        return dropTime;
    }

    public void setDropTime(String dropTime) {
        this.dropTime = dropTime;
    }


    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public boolean isApprovedForProcessing() {
        return approvedForProcessing;
    }

    public void setApprovedForProcessing(boolean approvedForProcessing) {
        this.approvedForProcessing = approvedForProcessing;
    }

    private String createdTime;
    private boolean approvedForProcessing;

    public JSONArray getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(JSONArray orderDetail) {
        this.orderDetail = orderDetail;
    }

    public boolean isServiceDone() {
        return serviceDone;
    }

    public void setServiceDone(boolean serviceDone) {
        this.serviceDone = serviceDone;
    }

    private boolean serviceDone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public JSONObject getAddress() {
        return address;
    }

    public void setAddress(JSONObject address) {
        this.address = address;
    }
}
