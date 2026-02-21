package com.ded.BTS.config;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DateTimeConfig {

    @Value("${app.timezone:UTC}")
    private String timezone;

    @Bean
    ZoneId appZoneId() {
        return ZoneId.of(timezone);
    }

    @Bean
    DateTimeFormatter instantFormatter(ZoneId zoneId) {
        return DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm:ss")
                .withZone(zoneId);
    }
}

