package com.example.batman.db;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionData implements Serializable {
    private String batName;        //배터리명
    private boolean isCard;        //결제 수단 | 0 : 현금 / 1 : 카드
    private boolean isStock;       //거래 종류 | 0 : 판매 / 1 : 매입
    private Date date;             //날짜
    private int count;             //수량
    private int totalPrice;        //판매 총액 = 가격 * 수량

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransactionData that = (TransactionData) o;

        if (isCard != that.isCard) return false;
        if (isStock != that.isStock) return false;
        if (count != that.count) return false;
        if (totalPrice != that.totalPrice) return false;
        if (!batName.equals(that.batName)) return false;
        if (!date.equals(that.date)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = batName.hashCode();
        result = 31 * result + (isCard ? 1 : 0);
        result = 31 * result + (isStock ? 1 : 0);
        result = 31 * result + date.hashCode();
        result = 31 * result + count;
        result = 31 * result + totalPrice;
        return result;
    }
}
