package ir.mahdiparastesh.chrono.iranian;

import java.io.Serializable;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.*;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.ValueRange;
import java.util.List;
import java.util.Locale;

public class IranianChronology extends AbstractChronology implements Serializable {

    public static final IranianChronology INSTANCE = new IranianChronology();

    @Override
    public String getId() {
        return "Iranian";
    }

    @Override
    public String getCalendarType() {
        return "iranian";
    }

    @Override
    public ChronoLocalDate date(Era era, int yearOfEra, int month, int dayOfMonth) {
        return super.date(era, yearOfEra, month, dayOfMonth);
    }

    @Override
    public ChronoLocalDate date(int prolepticYear, int month, int dayOfMonth) {
        return null;
    }

    @Override
    public ChronoLocalDate dateYearDay(Era era, int yearOfEra, int dayOfYear) {
        return super.dateYearDay(era, yearOfEra, dayOfYear);
    }

    @Override
    public ChronoLocalDate dateYearDay(int prolepticYear, int dayOfYear) {
        return null;
    }

    @Override
    public ChronoLocalDate dateEpochDay(long epochDay) {
        return null;
    }

    @Override
    public ChronoLocalDate dateNow() {
        return super.dateNow();
    }

    @Override
    public ChronoLocalDate dateNow(ZoneId zone) {
        return super.dateNow(zone);
    }

    @Override
    public ChronoLocalDate dateNow(Clock clock) {
        return super.dateNow(clock);
    }

    @Override
    public ChronoLocalDate date(TemporalAccessor temporal) {
        return null;
    }

    @Override
    public ChronoLocalDateTime<? extends ChronoLocalDate> localDateTime(TemporalAccessor temporal) {
        return super.localDateTime(temporal);
    }

    @Override
    public ChronoZonedDateTime<? extends ChronoLocalDate> zonedDateTime(TemporalAccessor temporal) {
        return super.zonedDateTime(temporal);
    }

    @Override
    public ChronoZonedDateTime<? extends ChronoLocalDate> zonedDateTime(Instant instant, ZoneId zone) {
        return super.zonedDateTime(instant, zone);
    }

    @Override
    public boolean isLeapYear(long prolepticYear) {
        return false;
    }

    @Override
    public int prolepticYear(Era era, int yearOfEra) {
        return 0;
    }

    @Override
    public Era eraOf(int eraValue) {
        return null;
    }

    @Override
    public List<Era> eras() {
        return List.of();
    }

    @Override
    public ValueRange range(ChronoField field) {
        return null;
    }

    @Override
    public String getDisplayName(TextStyle style, Locale locale) {
        return super.getDisplayName(style, locale);
    }

    @Override
    public ChronoPeriod period(int years, int months, int days) {
        return super.period(years, months, days);
    }

    @Override
    public long epochSecond(int prolepticYear, int month, int dayOfMonth, int hour, int minute, int second, ZoneOffset zoneOffset) {
        return super.epochSecond(prolepticYear, month, dayOfMonth, hour, minute, second, zoneOffset);
    }

    @Override
    public long epochSecond(Era era, int yearOfEra, int month, int dayOfMonth, int hour, int minute, int second, ZoneOffset zoneOffset) {
        return super.epochSecond(era, yearOfEra, month, dayOfMonth, hour, minute, second, zoneOffset);
    }

    @Override
    public boolean isIsoBased() {
        return super.isIsoBased();
    }
}
