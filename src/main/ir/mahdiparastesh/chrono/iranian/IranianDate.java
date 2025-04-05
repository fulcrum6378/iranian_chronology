package ir.mahdiparastesh.chrono.iranian;

import java.io.Serializable;
import java.time.DateTimeException;
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

    public static IranianDate ofYearDay(int year, int dayOfYear) {
        YEAR.checkValidValue(year);
        DAY_OF_YEAR.checkValidValue(dayOfYear);
        boolean leap = IranianChronology.INSTANCE.isLeapYear(year);
        if (dayOfYear == 366 && !leap)
            throw new DateTimeException("Invalid date 'DayOfYear 366' as '" + year + "' is not a leap year");

        // TODO
        return null;
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
        return null;  // TODO
    }

    @Override
    public long until(Temporal endExclusive, TemporalUnit unit) {
        return 0;  // TODO
    }

    @Override
    public long getLong(TemporalField field) {
        return 0;  // TODO
    }
}
