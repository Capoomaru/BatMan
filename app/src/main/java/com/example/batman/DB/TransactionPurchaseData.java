package com.example.batman.DB;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class TransactionPurchaseData extends TransactionData implements Serializable, Cloneable {
    private int purchasePrice;

    public int getPrice() {
        return purchasePrice;
    }

    public void setPrice(int Price) {
        this.purchasePrice = Price;
    }

    public int getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(int purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public TransactionPurchaseData() {super();}

    public TransactionPurchaseData(String batName, boolean isCard, boolean isStock, Date date, int purchasePrice, int count, int totalPrice) {
        super(batName, isCard, isStock, date, count, totalPrice);
        this.purchasePrice = purchasePrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        TransactionPurchaseData that = (TransactionPurchaseData) o;

        return purchasePrice == that.purchasePrice;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + purchasePrice;
        return result;
    }

    public static ArrayList<TransactionPurchaseData> cloneList(ArrayList<TransactionPurchaseData> sourceList) {
        ArrayList<TransactionPurchaseData> destList = new ArrayList<>();
        for(TransactionPurchaseData source : sourceList) {
            try {
                destList.add((TransactionPurchaseData) source.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        return destList;
    }
}
