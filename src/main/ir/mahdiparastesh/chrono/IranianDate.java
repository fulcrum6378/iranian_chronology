package ir.mahdiparastesh.chrono;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoPeriod;
import java.time.chrono.Chronology;
import java.time.temporal.*;

import static java.time.temporal.ChronoField.*;

public class IranianDate
        implements Temporal, TemporalAdjuster, ChronoLocalDate {  // TODO Serializable

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
        IranianChronology.INSTANCE.range(DAY_OF_MONTH).checkValidValue(day, DAY_OF_MONTH);
        // TODO WHAT IF I CREATE 6404/12/30?

        return new IranianDate(year, month, day);
    }

    public static IranianDate ofYearDay(int year, int dayOfYear) {
        YEAR.checkValidValue(year);
        DAY_OF_YEAR.checkValidValue(dayOfYear);

        boolean leap = IranianChronology.INSTANCE.isLeapYear(year);
        if (dayOfYear == 366 && !leap)
            throw new DateTimeException("Invalid date 'DayOfYear 366' as '" + year + "' is not a leap year");

        int month = 1;
        int maxDaysInMonth = 31;

        while (dayOfYear > maxDaysInMonth) {
            dayOfYear -= maxDaysInMonth;
            month++;
            maxDaysInMonth = (month < 7) ? 31 : 30;
        }

        return new IranianDate(year, month, dayOfYear);
    }

    // TODO: now()

    public static IranianDate ofEpochDay(long epochDay) {
        int y, yearLength;

        if (epochDay >= 79L) {  // after 6348
            epochDay -= 78L;
            y = 6349;
            while (true) {
                yearLength = IranianChronology.INSTANCE.isLeapYear(y) ? 366 : 365;
                if (epochDay > yearLength)
                    epochDay -= yearLength;
                else
                    return ofYearDay(y, (int) epochDay);
                y++;
            }

        } else if (epochDay < -286L) {  // before 6348
            epochDay = -epochDay - 287L;
            y = 6347;
            while (true) {
                yearLength = IranianChronology.INSTANCE.isLeapYear(y) ? 366 : 365;
                if (epochDay > yearLength)
                    epochDay -= yearLength;
                else
                    return ofYearDay(y, (int) (yearLength - epochDay));
                y--;
            }

        } else {  // during 6348 (1969-1970)
            return ofYearDay(6348, (int) epochDay + 287);
        }
    }

    @Override
    public Chronology getChronology() {
        return IranianChronology.INSTANCE;
    }

    //-----------------------------------------------------------------------

    @Override
    public int lengthOfMonth() {
        if (month <= 6)
            return 31;
        else if (month <= 11)
            return 30;
        else
            return isLeapYear() ? 30 : 29;
    }

    //-----------------------------------------------------------------------

    @Override
    public ChronoPeriod until(ChronoLocalDate endDateExclusive) {
        return null;  // TODO
    }

    @Override
    public long until(Temporal endExclusive, TemporalUnit unit) {
        return 0;  // TODO
    }

    //-----------------------------------------------------------------------

    @Override
    public long getLong(TemporalField field) {
        if (field instanceof ChronoField) {
            if (field == EPOCH_DAY) {
                return toEpochDay();
            }
            if (field == PROLEPTIC_MONTH) {
                return getProlepticMonth();
            }
            return get0(field);
        }
        return field.getFrom(this);
    }

    private int get0(TemporalField field) {
        return switch ((ChronoField) field) {
            case DAY_OF_WEEK -> getDayOfWeek().getValue();
            case ALIGNED_DAY_OF_WEEK_IN_MONTH -> ((day - 1) % 7) + 1;
            case ALIGNED_DAY_OF_WEEK_IN_YEAR -> ((getDayOfYear() - 1) % 7) + 1;
            case DAY_OF_MONTH -> day;
            case DAY_OF_YEAR -> getDayOfYear();
            case EPOCH_DAY -> throw new UnsupportedTemporalTypeException(
                    "Invalid field 'EpochDay' for get() method, use getLong() instead");
            case ALIGNED_WEEK_OF_MONTH -> ((day - 1) / 7) + 1;
            case ALIGNED_WEEK_OF_YEAR -> ((getDayOfYear() - 1) / 7) + 1;
            case MONTH_OF_YEAR -> month;
            case PROLEPTIC_MONTH -> throw new UnsupportedTemporalTypeException(
                    "Invalid field 'ProlepticMonth' for get() method, use getLong() instead");
            case YEAR -> year;
            case ERA, YEAR_OF_ERA -> throw new IranianChronology.EraNotSupportedException();
            default -> throw new UnsupportedTemporalTypeException("Unsupported field: " + field);
        };
    }

    public DayOfWeek getDayOfWeek() {
        int dow0 = Math.floorMod(toEpochDay() + 3, 7);
        return DayOfWeek.of(dow0 + 1);
    }

    public int getDayOfYear() {
        int doy = 0;
        int m = 1;
        while (m < month) {
            if (m <= 6)
                doy += 31;
            else if (m <= 11)
                doy += 30;
            m++;
        }
        doy += day;
        return doy;
    }

    private long getProlepticMonth() {
        return (year * 12L + month - 1);
    }

    @Override
    public long toEpochDay() {
        if (year > 6348) {  // after 6348
            long days = 79L;
            int y = 6349;
            while (y != year) {
                days += IranianChronology.INSTANCE.isLeapYear(y) ? 366 : 365;
                y++;
            }
            days += getDayOfYear() - 1;
            return days;

        } else if (year < 6348) {  // before 6348
            long days = -286L;
            int y = 6347;
            while (y != year) {
                days -= IranianChronology.INSTANCE.isLeapYear(y) ? 366 : 365;
                y--;
            }
            days -= (IranianChronology.INSTANCE.isLeapYear(y) ? 366 : 365) - (getDayOfYear() - 1);
            return days;

        } else {  // during 6348 (1969-1970)
            return getDayOfYear() - 287L;
        }
    }

    //-----------------------------------------------------------------------

    @Override
    public int compareTo(ChronoLocalDate other) {
        if (other instanceof IranianDate) {
            return compareTo0((IranianDate) other);
        }
        return ChronoLocalDate.super.compareTo(other);
    }

    int compareTo0(IranianDate otherDate) {
        int cmp = (year - otherDate.year);
        if (cmp == 0) {
            cmp = (month - otherDate.month);
            if (cmp == 0) {
                cmp = (day - otherDate.day);
            }
        }
        return cmp;
    }

    @Override
    public boolean isAfter(ChronoLocalDate other) {
        if (other instanceof IranianDate) {
            return compareTo0((IranianDate) other) > 0;
        }
        return ChronoLocalDate.super.isAfter(other);
    }

    @Override
    public boolean isBefore(ChronoLocalDate other) {
        if (other instanceof IranianDate) {
            return compareTo0((IranianDate) other) < 0;
        }
        return ChronoLocalDate.super.isBefore(other);
    }

    @Override
    public boolean isEqual(ChronoLocalDate other) {
        if (other instanceof IranianDate) {
            return compareTo0((IranianDate) other) == 0;
        }
        return ChronoLocalDate.super.isEqual(other);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof IranianDate) {
            return compareTo0((IranianDate) obj) == 0;
        }
        return false;
    }

    @Override
    public String toString() {
        var buf = new StringBuilder(10);
        formatTo(buf);
        return buf.toString();
    }

    @Override
    public int hashCode() {
        return (year & 0xFFFFF800) ^ ((year << 11) + (month << 6) + day);
    }

    void formatTo(StringBuilder buf) {
        int yearValue = year;
        int monthValue = month;
        int dayValue = day;
        int absYear = Math.abs(yearValue);
        if (absYear < 1000) {
            if (yearValue < 0) {
                buf.append('-');
            }
            buf.repeat('0', absYear < 10 ? 3 : absYear < 100 ? 2 : 1);
            buf.append(absYear);
        } else {
            if (yearValue > 9999) {
                buf.append('+');
            }
            buf.append(yearValue);
        }
        buf.append(monthValue < 10 ? "-0" : "-")
                .append(monthValue)
                .append(dayValue < 10 ? "-0" : "-")
                .append(dayValue);
    }
}
