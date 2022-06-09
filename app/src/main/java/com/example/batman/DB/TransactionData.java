package com.example.batman.DB;

import java.util.Date;

public class TransactionData {
    private String batName;        //배터리명
    private boolean isCard;        //결제 수단 | 0 : 현금 / 1 : 카드
    private boolean isStock;       //거래 종류 | 0 : 판매 / 1 : 매입
    private Date date;             //날짜
    private int count;             //수량
    private int totalPrice;        //판매 총액 = 가격 * 수량

    public String getBatName() {
        return batName;
    }
    public void setBatName(String batName) {
        this.batName = batName;
    }

    public boolean isCard() {
        return isCard;
    }
    public void setCard(boolean card) {
        isCard = card;
    }

    public boolean isStock() {
        return isStock;
    }
    public void setStock(boolean stock) {
        isStock = stock;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public TransactionData(String batName, boolean isCard, boolean isStock, Date date, int count, int totalPrice) {
        this.batName = batName;
        this.isCard = isCard;
        this.isStock = isStock;
        this.date = date;
        this.count = count;
        this.totalPrice = totalPrice;
    }

}
