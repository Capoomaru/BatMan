package com.example.batman.DB;

import java.util.Date;

public class TransactionSellData extends TransactionData {

    private int sellPrice;
    private String carNumber;
    private String carCategory;
    private String phoneNumber;
    private String findString;

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
}
