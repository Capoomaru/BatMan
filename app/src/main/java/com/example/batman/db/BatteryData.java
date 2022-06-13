package com.example.batman.db;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

public class BatteryData implements Serializable, Cloneable {
    private String batName;
    private int purchasePrice;
    private int sellingPrice;
    private int count;

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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public BatteryData() {
        this("", 0, 0, 0);
    }

    public BatteryData(String batName, int purchasePrice, int sellingPrice, int count) {
        this.batName = batName;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
        this.count = count;
    }

    @Override
    public String toString() {
        return batName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BatteryData that = (BatteryData) o;

        if (purchasePrice != that.purchasePrice) return false;
        if (sellingPrice != that.sellingPrice) return false;
        if (count != that.count) return false;
        return batName.equals(that.batName);
    }

    @Override
    public int hashCode() {
        int result = batName.hashCode();
        result = 31 * result + purchasePrice;
        result = 31 * result + sellingPrice;
        result = 31 * result + count;
        return result;
    }

    @NonNull
    @Override
    protected BatteryData clone() throws CloneNotSupportedException {
        return (BatteryData) super.clone();
    }

    public static ArrayList<BatteryData> cloneList(ArrayList<BatteryData> sourceList) {
        ArrayList<BatteryData> destList = new ArrayList<>();
        for (BatteryData source : sourceList) {
            try {
                destList.add(source.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        return destList;
    }
}
