package ir.mahdiparastesh.chrono.iranian;

import java.io.Serializable;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoPeriod;
import java.time.chrono.Chronology;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;

import static java.time.temporal.ChronoField.*;

public class IranianDate
        implements Temporal, TemporalAdjuster, ChronoLocalDate, Serializable {

    private final int year;
    private final short month;
    private final short day;

    private IranianDate(int year, int month, int day) {
        this.year = year;
        this.month = (short) month;
        this.day = (short) day;
    }

    public static IranianDate of(int year, int month, int day) {
        YEAR.checkValidValue(year);
        MONTH_OF_YEAR.checkValidValue(month);
        DAY_OF_MONTH.checkValidValue(day);
        return new IranianDate(year, month, day);
    }

    @Override
    public Chronology getChronology() {
        return IranianChronology.INSTANCE;
    }

    @Override
    public int lengthOfMonth() {
        if (month <= 6)
            return 31;
        else if (month <= 11)
            return 30;
        else
            return isLeapYear() ? 30 : 29;
    }

    @Override
    public ChronoPeriod until(ChronoLocalDate endDateExclusive) {
        return null;
    }

    @Override
    public long until(Temporal endExclusive, TemporalUnit unit) {
        return 0;
    }

    @Override
    public long getLong(TemporalField field) {
        return 0;
    }
}
