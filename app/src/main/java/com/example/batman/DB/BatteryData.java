package com.example.batman.DB;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class BatteryData implements Serializable {
    private String batName;

    public String getBatName() {
        return batName;
    }

    public void setBatName(String batName) {
        this.batName = batName;
    }

    public int getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(int purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public int getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(int sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getBatID() {
        return batID;
    }

    public void setBatID(String batID) {
        this.batID = batID;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private int purchasePrice;
    private int sellingPrice;
    private String batID;
    private int count;

    public BatteryData(String batName, int purchasePrice, int sellingPrice, String batID, int count) {
        this.batName = batName;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
        this.batID = batID;
        this.count = count;
    }
}
