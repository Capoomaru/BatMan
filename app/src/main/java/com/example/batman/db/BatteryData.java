package com.example.batman.db;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class BatteryData implements Serializable, Cloneable {
    private String batName;
    private int purchasePrice;
    private int sellingPrice;
    private int count;

    public BatteryData() {
        this("", 0, 0, 0);
    }

    @NonNull
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
