package com.example.batman.DB;

import java.util.Date;

public class TransactionData {
    private String trans_id;        //transaction id
    private String bat_name;        //배터리명
    private String count;           //수량
    private boolean is_credit;      //결제수단 0 : 현금  / 1 : 카드
    private boolean is_selling;     //판매 : 0
    private Date date;              //날짜
    private int purchase_price;     //판매가
    private String car_number;      //차량번호
    private String car_category;    //차종
    private String phone_number;    //전화번호
    private String TABLE_NAME = "transactionDB";

}
