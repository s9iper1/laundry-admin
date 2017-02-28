package com.byteshaft.laundryadmin.utils;

/**
 * Created by s9iper1 on 2/27/17.
 */

public class Data {

    private boolean dropOnPickLocation;
    private String houseNumber;
    private String pickUpAddress;
    private String zipCode;
    private String dropHouseNumber;
    private String dropAddress;
    private String dropZip;
    private String dropStreetNumber;
    private String orderTime;
    private String pickUpTime;
    private String dropTime;
    private String dropLatLng;
    private String pickUpLatLng;
    private int id;
    private String orderDetail;
    private String location;

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

    public String getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(String orderDetail) {
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isDropOnPickLocation() {
        return dropOnPickLocation;
    }

    public void setDropOnPickLocation(boolean dropOnPickLocation) {
        this.dropOnPickLocation = dropOnPickLocation;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getPickUpAddress() {
        return pickUpAddress;
    }

    public void setPickUpAddress(String pickUpAddress) {
        this.pickUpAddress = pickUpAddress;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getDropHouseNumber() {
        return dropHouseNumber;
    }

    public void setDropHouseNumber(String dropHouseNumber) {
        this.dropHouseNumber = dropHouseNumber;
    }

    public String getDropAddress() {
        return dropAddress;
    }

    public void setDropAddress(String dropAddress) {
        this.dropAddress = dropAddress;
    }

    public String getDropZip() {
        return dropZip;
    }

    public void setDropZip(String dropZip) {
        this.dropZip = dropZip;
    }

    public String getDropStreetNumber() {
        return dropStreetNumber;
    }

    public void setDropStreetNumber(String dropStreetNumber) {
        this.dropStreetNumber = dropStreetNumber;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getPickUpTime() {
        return pickUpTime;
    }

    public void setPickUpTime(String pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public String getDropTime() {
        return dropTime;
    }

    public void setDropTime(String dropTime) {
        this.dropTime = dropTime;
    }

    public String getDropLatLng() {
        return dropLatLng;
    }

    public void setDropLatLng(String dropLatLng) {
        this.dropLatLng = dropLatLng;
    }

    public String getPickUpLatLng() {
        return pickUpLatLng;
    }

    public void setPickUpLatLng(String pickUpLatLng) {
        this.pickUpLatLng = pickUpLatLng;
    }
}
