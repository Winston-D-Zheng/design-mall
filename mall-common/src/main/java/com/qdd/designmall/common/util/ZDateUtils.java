package com.qdd.designmall.common.util;

import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@UtilityClass
public class ZDateUtils
{
    public Long toTimeStamp(LocalDateTime date){
        ZonedDateTime zonedDateTime = date.atZone(ZoneId.systemDefault());
        Instant instant = zonedDateTime.toInstant();
        return instant.toEpochMilli();
    }
}
