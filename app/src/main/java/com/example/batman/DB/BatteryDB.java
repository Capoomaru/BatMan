package com.example.batman.DB;

import java.util.Date;

public class BatteryDB extends BatteryData {

    private Date LastUpdate;

    public Date getLastUpdate() {
        return LastUpdate;
    }
    public void setLastUpdate(Date lastUpdate) {
        LastUpdate = lastUpdate;
    }

    BatteryDB() {
        super();
    }
}
