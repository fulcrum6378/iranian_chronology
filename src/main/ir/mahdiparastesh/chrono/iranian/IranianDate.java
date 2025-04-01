package ir.mahdiparastesh.chrono.iranian;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Year;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.*;

import static java.time.temporal.ChronoField.*;

public class IranianDate
        implements Temporal, TemporalAdjuster, ChronoLocalDate, Serializable {

    /**
     * The minimum supported {@code LocalDate}, '-999999999-01-01'.
     * This could be used by an application as a "far past" date.
     */
    public static final LocalDate MIN = LocalDate.of(Year.MIN_VALUE, 1, 1);
    /**
     * The maximum supported {@code LocalDate}, '+999999999-12-29'.
     * This could be used by an application as a "far future" date.
     */
    public static final LocalDate MAX = LocalDate.of(Year.MAX_VALUE, 12, 29);


    private final int year;
    private final short month;
    private final short day;

    public static IranianDate of(int year, int month, int day) {
        YEAR.checkValidValue(year);
        MONTH_OF_YEAR.checkValidValue(month);
        DAY_OF_MONTH.checkValidValue(day);
        return new IranianDate(year, month, day);
    }

    private IranianDate(int year, int month, int day) {
        this.year = year;
        this.month = (short) month;
        this.day = (short) day;
    }


    //-----------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSupported(TemporalField field) {
        return switch (field) {
            case ERA, YEAR_OF_ERA -> throw new EraNotSupportedException();
            default -> ChronoLocalDate.super.isSupported(field);
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSupported(TemporalUnit unit) {
        if (unit == ChronoUnit.ERAS)
            throw new EraNotSupportedException();

        return ChronoLocalDate.super.isSupported(unit);
    }

    //-----------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public ValueRange range(TemporalField field) {
        if (field instanceof ChronoField chronoField) {
            if (chronoField.isDateBased()) {
                return switch (chronoField) {
                    case DAY_OF_MONTH -> ValueRange.of(1, lengthOfMonth());
                    case DAY_OF_YEAR -> ValueRange.of(1, lengthOfYear());
                    case ALIGNED_WEEK_OF_MONTH -> ValueRange.of(1, 5);
                    case ERA, YEAR_OF_ERA -> throw new EraNotSupportedException();
                    default -> field.range();
                };
            }
            throw new UnsupportedTemporalTypeException("Unsupported field: " + field);
        }
        return field.rangeRefinedBy(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int get(TemporalField field) {
        if (field instanceof ChronoField) {
            return get0(field);
        }
        return ChronoLocalDate.super.get(field);
    }

    /**
     * {@inheritDoc}
     */
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
            case ERA, YEAR_OF_ERA -> throw new EraNotSupportedException();
            case YEAR -> year;
            default -> throw new UnsupportedTemporalTypeException("Unsupported field: " + field);
        };
    }

    private long getProlepticMonth() {
        return (year * 12L + month - 1);
    }

    //-----------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public IranianChronology getChronology() {
        return IranianChronology.INSTANCE;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDayOfMonth() {
        return day;
    }

    //-----------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLeapYear() {
        return IranianChronology.INSTANCE.isLeapYear(year);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int lengthOfMonth() {
        if (month <= 6)
            return 31;
        else if (month <= 11)
            return 30;
        else
            return isLeapYear() ? 30 : 29;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int lengthOfYear() {
        return isLeapYear() ? 366 : 365;
    }

    //-----------------------------------------------------------------------


    static class EraNotSupportedException extends UnsupportedTemporalTypeException {
        private static final String msg = "Eras are not supported.";

        public EraNotSupportedException() {
            super(msg);
        }
    }
}
