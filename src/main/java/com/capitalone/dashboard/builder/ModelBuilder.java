package com.capitalone.dashboard.builder;

import com.capitalone.dashboard.enums.Clazz;
import com.capitalone.dashboard.enums.HelmStatus;
import com.capitalone.dashboard.model.BaseModel;
import com.capitalone.dashboard.model.Chart;
import com.capitalone.dashboard.model.Release;
import com.capitalone.dashboard.model.Repo;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;
import java.util.Map;

/**
 * Application configuration and bootstrap
 */
public class ModelBuilder {
    public static BaseModel createModelObject(Class<?> clazz, Map<String, String> args) {
        switch (Clazz.valueOf(clazz.getSimpleName())) {
            case Chart:
                final DateTimeFormatter chartDateTimeFormatter = new DateTimeFormatterBuilder()
                        .parseCaseInsensitive()
                        .appendPattern("EEE MMM d HH:mm:ss yyyy")
                        .toFormatter(Locale.US);
                final LocalDateTime chartDateTime = LocalDateTime.parse(args.get("UPDATED").replaceAll(" {2}", " "), chartDateTimeFormatter);
                return new Chart(chartDateTime.atZone(ZoneOffset.systemDefault()).toEpochSecond(), args.get("STATUS").toUpperCase(), args.get("CHART"), args.get("APP VERSION"), args.get("DESCRIPTION"));
            case Repo:
                return new Repo(args.get("NAME"), args.get("URL"));
            case Release:
                final DateTimeFormatter releaseDateTimeFormatter = new DateTimeFormatterBuilder()
                        .parseCaseInsensitive()
                        .appendPattern("yyyy-MM-dd HH:mm:ss")
                        .appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true)
                        .appendPattern(" Z zzz")
                        .toFormatter(Locale.US);
                final ZonedDateTime releaseDateTime = ZonedDateTime.parse(args.get("UPDATED"), releaseDateTimeFormatter);
                return new Release(args.get("NAME"), args.get("APP VERSION"), releaseDateTime.toEpochSecond(), HelmStatus.valueOf(args.get("STATUS").toUpperCase()), args.get("CHART"), args.get("NAMESPACE"));
            default:
                throw new IllegalStateException("Unexpected value: " + Clazz.valueOf(clazz.getSimpleName()));
        }
    }
}
