package com.example.batman.DB;

import java.util.Date;

public class TransactionStockData extends TransactionData{
    private int purchasePrice;

    public int getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(int purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public TransactionStockData(String batName, boolean isCard, boolean isStock, Date date, int purchasePrice, int count, int totalPrice) {
        super(batName, isCard, isStock, date, count, totalPrice);
        this.purchasePrice = purchasePrice;
    }
}
