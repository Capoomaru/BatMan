package com.example.batman.db;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BatteryDB extends BatteryData {

    private Date lastUpdate;

    BatteryDB() {
        super();
    }
}
