package com.example.batman.DB;

import android.provider.BaseColumns;

public final class TransactionContract {
    public static class TransactionEntry implements BaseColumns {
        public static final String TRANS_ID = "trans_id";
        public static final String BAT_NAME = "bat_name";
        public static final String COUNT = "count";
        public static final String IS_CREDIT = "is_credit";
        public static final String DATE = "date";
        public static final String PRICE = "price";
        public static final String CAR_NUMBER = "car_number";
        public static final String CAR_CATEGORY = "car_category";
        public static final String PHONE_NUMBER = "phone_number";
        public static final String TABLE_NAME = "transactionDB";
        public static final String SQL_CREATE_TABLE = "create table if not exists "+TABLE_NAME+"("
                +_ID+" integer primary key autoincrement, "
                +TRANS_ID+" Integer not null , "
                +BAT_NAME+" text not null , "
                +COUNT+" Integer not null , "
                +IS_CREDIT+" Integer not null , "
                +DATE+" text not null , "
                +PRICE+" Integer not null , "
                +CAR_NUMBER+" Integer , "
                +CAR_CATEGORY+" text , "
                +PHONE_NUMBER+" text );";
        public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + TransactionEntry.TABLE_NAME;
    }
}
