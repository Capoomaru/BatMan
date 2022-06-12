package com.example.batman.db;

import java.util.Date;

public class BatteryDB extends BatteryData {

    private Date lastUpdate;

    public Date getLastUpdate() {return lastUpdate;}
    public void setLastUpdate(Date lastUpdate) { this.lastUpdate = lastUpdate; }

    BatteryDB() {
        super();
    }
}
