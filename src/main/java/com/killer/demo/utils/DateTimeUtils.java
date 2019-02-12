package com.killer.demo.utils;/**
 * @author killer
 * @date 2019/2/3 -  22:22
 **/

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

/**
 *@description
 *@author killer
 *@date 2019/02/03 - 22:22
 */
public class DateTimeUtils {

    private static final ZoneId ZONE_ID = ZoneId.systemDefault();

    public static Date now() {
        LocalDateTime now = LocalDateTime.now();
//      ZoneDateTime:  A date-time with a time-zone in the ISO-8601 calendar system
        Instant instant = now.atZone(ZONE_ID).toInstant();
        return Date.from(instant);
    }

    public static Date LTD2Date(LocalDateTime dateTime) {
        Instant instant = dateTime.atZone(ZONE_ID).toInstant();
        return Date.from(instant);
    }
}
