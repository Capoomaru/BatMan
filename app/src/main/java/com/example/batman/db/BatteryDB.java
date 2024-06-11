package com.example.batman.db;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class BatteryDB extends BatteryData {

    private Date lastUpdate;

}
