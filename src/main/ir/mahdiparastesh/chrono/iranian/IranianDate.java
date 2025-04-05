package ir.mahdiparastesh.chrono.iranian;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.chrono.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.*;

public class IranianDate
        implements Temporal, TemporalAdjuster, ChronoLocalDate, Serializable {

    @Override
    public Chronology getChronology() {
        return IranianChronology.INSTANCE;
    }

    @Override
    public Era getEra() {
        return ChronoLocalDate.super.getEra();
    }

    @Override
    public boolean isLeapYear() {
        return ChronoLocalDate.super.isLeapYear();
    }

    @Override
    public int lengthOfMonth() {
        return 0;
    }

    @Override
    public int lengthOfYear() {
        return ChronoLocalDate.super.lengthOfYear();
    }

    @Override
    public boolean isSupported(TemporalField field) {
        return ChronoLocalDate.super.isSupported(field);
    }

    @Override
    public ValueRange range(TemporalField field) {
        //return Temporal.super.range(field);
        try {
            throw new Exception("TODO");  // TODO
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int get(TemporalField field) {
        //return Temporal.super.get(field);
        try {
            throw new Exception("TODO");  // TODO
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long getLong(TemporalField field) {
        return 0;
    }

    @Override
    public boolean isSupported(TemporalUnit unit) {
        return ChronoLocalDate.super.isSupported(unit);
    }

    @Override
    public ChronoLocalDate with(TemporalAdjuster adjuster) {
        return ChronoLocalDate.super.with(adjuster);
    }

    @Override
    public ChronoLocalDate with(TemporalField field, long newValue) {
        return ChronoLocalDate.super.with(field, newValue);
    }

    @Override
    public ChronoLocalDate plus(TemporalAmount amount) {
        return ChronoLocalDate.super.plus(amount);
    }

    @Override
    public ChronoLocalDate plus(long amountToAdd, TemporalUnit unit) {
        return ChronoLocalDate.super.plus(amountToAdd, unit);
    }

    @Override
    public ChronoLocalDate minus(TemporalAmount amount) {
        return ChronoLocalDate.super.minus(amount);
    }

    @Override
    public ChronoLocalDate minus(long amountToSubtract, TemporalUnit unit) {
        return ChronoLocalDate.super.minus(amountToSubtract, unit);
    }

    @Override
    public long until(Temporal endExclusive, TemporalUnit unit) {
        return 0;
    }

    @Override
    public <R> R query(TemporalQuery<R> query) {
        return ChronoLocalDate.super.query(query);
    }

    @Override
    public Temporal adjustInto(Temporal temporal) {
        return ChronoLocalDate.super.adjustInto(temporal);
    }

    @Override
    public ChronoPeriod until(ChronoLocalDate endDateExclusive) {
        return null;
    }

    @Override
    public String format(DateTimeFormatter formatter) {
        return ChronoLocalDate.super.format(formatter);
    }

    @Override
    public ChronoLocalDateTime<?> atTime(LocalTime localTime) {
        return ChronoLocalDate.super.atTime(localTime);
    }

    @Override
    public long toEpochDay() {
        return ChronoLocalDate.super.toEpochDay();
    }

    @Override
    public int compareTo(ChronoLocalDate other) {
        return ChronoLocalDate.super.compareTo(other);
    }

    @Override
    public boolean isAfter(ChronoLocalDate other) {
        return ChronoLocalDate.super.isAfter(other);
    }

    @Override
    public boolean isBefore(ChronoLocalDate other) {
        return ChronoLocalDate.super.isBefore(other);
    }

    @Override
    public boolean isEqual(ChronoLocalDate other) {
        return ChronoLocalDate.super.isEqual(other);
    }
}
