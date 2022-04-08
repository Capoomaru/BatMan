package com.example.batman;

import android.provider.BaseColumns;

public final class DataBaseEX {

    public static final class CreateDB implements BaseColumns {
        public static final String USERID = "userid";
        public static final String NAME = "userid";
        public static final String AGE = "userid";
        public static final String GENDER = "userid";
        public static final String _TABLENAME0 = "usertable";
        public static final String _CREATE0 = "create table if not exists "+_TABLENAME0+"("
                +_ID+" integer primary key autoincrement, "
                +USERID+" text not null , "
                +NAME+" text not null , "
                +AGE+" integer not null , "
                +GENDER+" text not null );";
    }
}
