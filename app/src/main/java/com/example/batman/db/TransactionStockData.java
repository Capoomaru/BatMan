package com.example.batman.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class TransactionStockData extends TransactionData implements Serializable, Cloneable {
    private int purchasePrice;

    public int getPrice() {
        return purchasePrice;
    }

    public void setPrice(int price) {
        this.purchasePrice = price;
    }

    public TransactionStockData(String batName, boolean isCard, boolean isStock, Date date, int purchasePrice, int count, int totalPrice) {
        super(batName, isCard, isStock, date, count, totalPrice);
        this.purchasePrice = purchasePrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        TransactionStockData that = (TransactionStockData) o;

        return purchasePrice == that.purchasePrice;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + purchasePrice;
        return result;
    }

    public static ArrayList<TransactionStockData> cloneList(ArrayList<TransactionStockData> sourceList) {
        ArrayList<TransactionStockData> destList = new ArrayList<>();
        for (TransactionStockData source : sourceList) {
            try {
                destList.add((TransactionStockData) source.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        return destList;
    }
}
