package com.example.batman.db;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class TransactionSellData extends TransactionData implements Serializable, Cloneable {

    private int sellPrice;
    private String carNumber;
    private String carCategory;
    private String phoneNumber;
    private String findString;

    public int getPrice() {
        return sellPrice;
    }

    public void setPrice(int Price) {
        this.sellPrice = Price;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getCarCategory() {
        return carCategory;
    }

    public void setCarCategory(String carCategory) {
        this.carCategory = carCategory;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFindString() {
        this.findString = carNumber+carCategory+phoneNumber;
        return findString;
    }

    public void setFindString(String findString) {
        this.findString = findString;
    }
    public void setFindString() { this.findString = carNumber+carCategory+phoneNumber; }

    public TransactionSellData() {super();}

    public TransactionSellData(String batName, boolean isCard, boolean isStock, Date date, int sellPrice, int count, int totalPrice) {
        super(batName, isCard, isStock, date, count, totalPrice);
        this.sellPrice = sellPrice;
    }

    public TransactionSellData(String batName, boolean isCard, boolean isStock, Date date, int price, int count, int totalPrice, String carNumber, String carCategory, String phoneNumber) {
        this(batName, isCard, isStock, date, price, count, totalPrice);
        this.carNumber = carNumber;
        this.carCategory = carCategory;
        this.phoneNumber = phoneNumber;
        this.findString = carNumber+carCategory+phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        TransactionSellData that = (TransactionSellData) o;

        if (sellPrice != that.sellPrice) return false;
        if (!carNumber.equals(that.carNumber)) return false;
        if (!carCategory.equals(that.carCategory)) return false;
        if (!phoneNumber.equals(that.phoneNumber)) return false;
        return findString.equals(that.findString);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + sellPrice;
        result = 31 * result + carNumber.hashCode();
        result = 31 * result + carCategory.hashCode();
        result = 31 * result + phoneNumber.hashCode();
        result = 31 * result + findString.hashCode();
        return result;
    }

    @NonNull
    @Override
    protected TransactionSellData clone() throws CloneNotSupportedException {
        return (TransactionSellData) super.clone();
    }

    public static ArrayList<TransactionSellData> cloneList(ArrayList<TransactionSellData> sourceList) {
        ArrayList<TransactionSellData> destList = new ArrayList<>();
        for(TransactionSellData source : sourceList) {
            try {
                destList.add(source.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        return destList;
    }

    public boolean equalCustomer(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        TransactionSellData that = (TransactionSellData) o;

        if (!carNumber.equals(that.carNumber)) return false;
        if (!phoneNumber.equals(that.phoneNumber)) return false;
        return true;
    }
}
