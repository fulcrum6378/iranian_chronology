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
    public IranianDate date(int prolepticYear, int month, int dayOfMonth) {
        return IranianDate.of(prolepticYear, month, dayOfMonth);
    }

    @Override
    public IranianDate dateYearDay(int prolepticYear, int dayOfYear) {
        return IranianDate.ofYearDay(prolepticYear, dayOfYear);
    }

    @Override
    public IranianDate dateEpochDay(long epochDay) {
        return null;  //TODO IranianDate.ofEpochDay(epochDay);
    }

    @Override
    public IranianDate date(TemporalAccessor temporal) {
        return null;  //TODO IranianDate.from(temporal);
    }

    @Override
    public boolean isLeapYear(long prolepticYear) {
        while (prolepticYear < 0) prolepticYear += 2820L;
        long periodicYear = (prolepticYear - 5474L) % 2820L;
        long test = ((periodicYear + 38L) * 682L) % 2816L;
        return test < 682;
    }

    @Override
    public int prolepticYear(Era era, int yearOfEra) {
        return yearOfEra;
    }

    @Override
    public Era eraOf(int eraValue) {
        return null;
    }

    @Override
    public List<Era> eras() {
        return null;
    }

    @Override
    public ValueRange range(ChronoField field) {
        return null;  // TODO
    }
}
