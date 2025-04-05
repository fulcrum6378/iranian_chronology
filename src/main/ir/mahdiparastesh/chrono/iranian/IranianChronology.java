package ir.mahdiparastesh.chrono.iranian;

import java.io.Serializable;
import java.time.chrono.AbstractChronology;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Era;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.ValueRange;
import java.util.List;

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
    public ChronoLocalDate date(int prolepticYear, int month, int dayOfMonth) {
        return null;
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
    public ChronoLocalDate date(TemporalAccessor temporal) {
        return null;
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
}
