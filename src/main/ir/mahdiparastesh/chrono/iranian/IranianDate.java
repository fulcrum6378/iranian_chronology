package ir.mahdiparastesh.chrono.iranian;

import java.io.*;
import java.time.*;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Era;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.*;
import java.util.Objects;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.time.temporal.ChronoField.*;

public class IranianDate
        implements Temporal, TemporalAdjuster, ChronoLocalDate, Serializable {

    /**
     * The minimum supported {@code IranianDate}, '-999999999-01-01'.
     * This could be used by an application as a "far past" date.
     */
    public static final IranianDate MIN = IranianDate.of(Year.MIN_VALUE, 1, 1);
    /**
     * The maximum supported {@code IranianDate}, '+999999999-12-29'.
     * This could be used by an application as a "far future" date.
     */
    public static final IranianDate MAX = IranianDate.of(Year.MAX_VALUE, 12, 29);


    private final int year;
    private final short month;
    private final short day;

    //-----------------------------------------------------------------------

    /**
     * Obtains the current date from the system clock in the default time-zone.
     * <p>
     * This will query the {@link Clock#systemDefaultZone() system clock} in the default
     * time-zone to obtain the current date.
     * <p>
     * Using this method will prevent the ability to use an alternate clock for testing
     * because the clock is hard-coded.
     *
     * @return the current date using the system clock and default time-zone, not null
     */
    public static IranianDate now() {
        return now(Clock.systemDefaultZone());
    }

    /**
     * Obtains the current date from the system clock in the specified time-zone.
     * <p>
     * This will query the {@link Clock#system(ZoneId) system clock} to obtain the current date.
     * Specifying the time-zone avoids dependence on the default time-zone.
     * <p>
     * Using this method will prevent the ability to use an alternate clock for testing
     * because the clock is hard-coded.
     *
     * @param zone the zone ID to use, not null
     * @return the current date using the system clock, not null
     */
    public static IranianDate now(ZoneId zone) {
        return now(Clock.system(zone));
    }

    /**
     * Obtains the current date from the specified clock.
     * <p>
     * This will query the specified clock to obtain the current date - today.
     * Using this method allows the use of an alternate clock for testing.
     * The alternate clock may be introduced using {@link Clock dependency injection}.
     *
     * @param clock the clock to use, not null
     * @return the current date, not null
     */
    public static IranianDate now(Clock clock) {
        Objects.requireNonNull(clock, "clock");
        final Instant now = clock.instant();  // called once
        return ofInstant(now, clock.getZone());
    }

    //-----------------------------------------------------------------------

    public static IranianDate of(int year, int month, int day) {
        YEAR.checkValidValue(year);
        MONTH_OF_YEAR.checkValidValue(month);
        DAY_OF_MONTH.checkValidValue(day);
        return new IranianDate(year, month, day);
    }

    //-----------------------------------------------------------------------

    /**
     * Obtains an instance of {@code IranianDate} from a year and day-of-year.
     * <p>
     * This returns a {@code IranianDate} with the specified year and day-of-year.
     * The day-of-year must be valid for the year, otherwise an exception will be thrown.
     *
     * @param year      the year to represent, from MIN_YEAR to MAX_YEAR
     * @param dayOfYear the day-of-year to represent, from 1 to 366
     * @return the local date, not null
     * @throws DateTimeException if the value of any field is out of range,
     *                           or if the day-of-year is invalid for the year
     */
    public static IranianDate ofYearDay(int year, int dayOfYear) {
        YEAR.checkValidValue(year);
        DAY_OF_YEAR.checkValidValue(dayOfYear);
        boolean leap = IranianChronology.INSTANCE.isLeapYear(year);
        if (dayOfYear == 366 && leap == false) {
            throw new DateTimeException("Invalid date 'DayOfYear 366' as '" + year + "' is not a leap year");
        }
        Month moy = Month.of((dayOfYear - 1) / 31 + 1);
        int monthEnd = moy.firstDayOfYear(leap) + moy.length(leap) - 1;
        if (dayOfYear > monthEnd) {
            moy = moy.plus(1);
        }
        int dom = dayOfYear - moy.firstDayOfYear(leap) + 1;
        return new IranianDate(year, moy.getValue(), dom);
    }

    //-----------------------------------------------------------------------

    /**
     * Obtains an instance of {@code IranianDate} from an {@code Instant} and zone ID.
     * <p>
     * This creates a local date based on the specified instant.
     * First, the offset from UTC/Greenwich is obtained using the zone ID and instant,
     * which is simple as there is only one valid offset for each instant.
     * Then, the instant and offset are used to calculate the local date.
     *
     * @param instant the instant to create the date from, not null
     * @param zone    the time-zone, which may be an offset, not null
     * @return the local date, not null
     * @throws DateTimeException if the result exceeds the supported range
     * @since 9
     */
    public static IranianDate ofInstant(Instant instant, ZoneId zone) {
        /*Objects.requireNonNull(instant, "instant");
        Objects.requireNonNull(zone, "zone");
        ZoneRules rules = zone.getRules();
        ZoneOffset offset = rules.getOffset(instant);
        long localSecond = instant.getEpochSecond() + offset.getTotalSeconds();
        long localEpochDay = Math.floorDiv(localSecond, SECONDS_PER_DAY);
        return ofEpochDay(localEpochDay);*/
        throw new Exception("TODO");
    }

    //-----------------------------------------------------------------------

    /**
     * Obtains an instance of {@code IranianDate} from the epoch day count.
     * <p>
     * This returns a {@code IranianDate} with the specified epoch-day.
     * The {@link ChronoField#EPOCH_DAY EPOCH_DAY} is a simple incrementing count
     * of days where day 0 is 1970-01-01. Negative numbers represent earlier days.
     *
     * @param epochDay the Epoch Day to convert, based on the epoch 1970-01-01
     * @return the local date, not null
     * @throws DateTimeException if the epoch day exceeds the supported date range
     */
    public static IranianDate ofEpochDay(long epochDay) {
        /*EPOCH_DAY.checkValidValue(epochDay);
        long zeroDay = epochDay + DAYS_0000_TO_1970;
        // find the march-based year
        zeroDay -= 60;  // adjust to 0000-03-01 so leap day is at end of four year cycle
        long adjust = 0;
        if (zeroDay < 0) {
            // adjust negative years to positive for calculation
            long adjustCycles = (zeroDay + 1) / DAYS_PER_CYCLE - 1;
            adjust = adjustCycles * 400;
            zeroDay += -adjustCycles * DAYS_PER_CYCLE;
        }
        long yearEst = (400 * zeroDay + 591) / DAYS_PER_CYCLE;
        long doyEst = zeroDay - (365 * yearEst + yearEst / 4 - yearEst / 100 + yearEst / 400);
        if (doyEst < 0) {
            // fix estimate
            yearEst--;
            doyEst = zeroDay - (365 * yearEst + yearEst / 4 - yearEst / 100 + yearEst / 400);
        }
        yearEst += adjust;  // reset any negative year
        int marchDoy0 = (int) doyEst;

        // convert march-based values back to january-based
        int marchMonth0 = (marchDoy0 * 5 + 2) / 153;
        int month = marchMonth0 + 3;
        if (month > 12) {
            month -= 12;
        }
        int dom = marchDoy0 - (marchMonth0 * 306 + 5) / 10 + 1;
        if (marchDoy0 >= 306) {
            yearEst++;
        }

        return new IranianDate((int)yearEst, month, dom);*/
        throw new Exception("TODO");
    }

    //-----------------------------------------------------------------------

    public static final TemporalQuery<IranianDate> TEMPORAL_QUERY = new TemporalQuery<>() {
        @Override
        public IranianDate queryFrom(TemporalAccessor temporal) {
            if (temporal.isSupported(EPOCH_DAY)) {
                return IranianDate.ofEpochDay(temporal.getLong(EPOCH_DAY));
            }
            return null;
        }

        @Override
        public String toString() {
            return "IranianDate";
        }
    };

    /**
     * Obtains an instance of {@code IranianDate} from a temporal object.
     * <p>
     * This obtains a local date based on the specified temporal.
     * A {@code TemporalAccessor} represents an arbitrary set of date and time information,
     * which this factory converts to an instance of {@code IranianDate}.
     * <p>
     * The conversion uses the {@link IranianDate#TEMPORAL_QUERY} query, which relies
     * on extracting the {@link ChronoField#EPOCH_DAY EPOCH_DAY} field.
     * <p>
     * This method matches the signature of the functional interface {@link TemporalQuery}
     * allowing it to be used as a query via method reference, {@code IranianDate::from}.
     *
     * @param temporal the temporal object to convert, not null
     * @return the local date, not null
     * @throws DateTimeException if unable to convert to a {@code IranianDate}
     */
    public static IranianDate from(TemporalAccessor temporal) {
        Objects.requireNonNull(temporal, "temporal");
        IranianDate date = temporal.query(TEMPORAL_QUERY);
        if (date == null) {
            throw new DateTimeException("Unable to obtain IranianDate from TemporalAccessor: " +
                    temporal + " of type " + temporal.getClass().getName());
        }
        return date;
    }

    //-----------------------------------------------------------------------

    /**
     * Obtains an instance of {@code IranianDate} from a text string such as {@code 2007-12-03}.
     * <p>
     * The string must represent a valid date and is parsed using
     * {@link java.time.format.DateTimeFormatter#ISO_LOCAL_DATE}.
     *
     * @param text the text to parse such as "2007-12-03", not null
     * @return the parsed local date, not null
     * @throws DateTimeParseException if the text cannot be parsed
     */
    public static IranianDate parse(CharSequence text) {
        //return parse(text, DateTimeFormatter.ISO_LOCAL_DATE);
        throw new Exception("TODO");
    }

    /**
     * Obtains an instance of {@code IranianDate} from a text string using a specific formatter.
     * <p>
     * The text is parsed using the formatter, returning a date.
     *
     * @param text      the text to parse, not null
     * @param formatter the formatter to use, not null
     * @return the parsed local date, not null
     * @throws DateTimeParseException if the text cannot be parsed
     */
    public static IranianDate parse(CharSequence text, DateTimeFormatter formatter) {
        Objects.requireNonNull(formatter, "formatter");
        return formatter.parse(text, IranianDate::from);
    }

    //-----------------------------------------------------------------------

    /**
     * Resolves the date, resolving days past the end of month.
     *
     * @param year  the year to represent, validated from MIN_YEAR to MAX_YEAR
     * @param month the month-of-year to represent, validated from 1 to 12
     * @param day   the day-of-month to represent, validated from 1 to 31
     * @return the resolved date, not null
     */
    private static IranianDate resolvePreviousValid(int year, int month, int day) {
        switch (month) {
            case 12 -> day = Math.min(day, IranianChronology.INSTANCE.isLeapYear(year) ? 30 : 29);
            case 7, 8, 9, 10, 11 -> day = Math.min(day, 30);
        }
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

    /**
     * Eras are not supported for the Iranian calendar.
     */
    @Override
    public Era getEra() {
        throw new EraNotSupportedException();
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

    public DayOfWeek getDayOfWeek() {
        int dow0 = Math.floorMod(toEpochDay() + 3, 7);
        return DayOfWeek.of(dow0 + 1);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public IranianDate with(TemporalAdjuster adjuster) {
        // optimizations
        if (adjuster instanceof IranianDate) {
            return (IranianDate) adjuster;
        }
        return (IranianDate) adjuster.adjustInto(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IranianDate with(TemporalField field, long newValue) {
        if (field instanceof ChronoField chronoField) {
            chronoField.checkValidValue(newValue);
            return switch (chronoField) {
                case DAY_OF_WEEK -> plusDays(newValue - getDayOfWeek().getValue());
                case ALIGNED_DAY_OF_WEEK_IN_MONTH -> plusDays(
                        newValue - getLong(ALIGNED_DAY_OF_WEEK_IN_MONTH));
                case ALIGNED_DAY_OF_WEEK_IN_YEAR -> plusDays(
                        newValue - getLong(ALIGNED_DAY_OF_WEEK_IN_YEAR));
                case DAY_OF_MONTH -> withDayOfMonth((int) newValue);
                case DAY_OF_YEAR -> withDayOfYear((int) newValue);
                case EPOCH_DAY -> IranianDate.ofEpochDay(newValue);
                case ALIGNED_WEEK_OF_MONTH -> plusWeeks(newValue - getLong(ALIGNED_WEEK_OF_MONTH));
                case ALIGNED_WEEK_OF_YEAR -> plusWeeks(newValue - getLong(ALIGNED_WEEK_OF_YEAR));
                case MONTH_OF_YEAR -> withMonth((int) newValue);
                case PROLEPTIC_MONTH -> plusMonths(newValue - getProlepticMonth());
                case YEAR -> withYear((int) newValue);
                default -> throw new UnsupportedTemporalTypeException("Unsupported field: " + field);
            };
        }
        return field.adjustInto(this, newValue);
    }

    //-----------------------------------------------------------------------

    /**
     * Returns a copy of this {@code IranianDate} with the year altered.
     * <p>
     * If the day-of-month is invalid for the year, it will be changed to the last valid day of the month.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * @param year the year to set in the result, from MIN_YEAR to MAX_YEAR
     * @return a {@code IranianDate} based on this date with the requested year, not null
     * @throws DateTimeException if the year value is invalid
     */
    public IranianDate withYear(int year) {
        if (this.year == year) {
            return this;
        }
        YEAR.checkValidValue(year);
        return resolvePreviousValid(year, month, day);
    }

    /**
     * Returns a copy of this {@code IranianDate} with the month-of-year altered.
     * <p>
     * If the day-of-month is invalid for the year, it will be changed to the last valid day of the month.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * @param month the month-of-year to set in the result, from 1 (January) to 12 (December)
     * @return a {@code IranianDate} based on this date with the requested month, not null
     * @throws DateTimeException if the month-of-year value is invalid
     */
    public IranianDate withMonth(int month) {
        if (this.month == month) {
            return this;
        }
        MONTH_OF_YEAR.checkValidValue(month);
        return resolvePreviousValid(year, month, day);
    }

    /**
     * Returns a copy of this {@code IranianDate} with the day-of-month altered.
     * <p>
     * If the resulting date is invalid, an exception is thrown.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * @param dayOfMonth the day-of-month to set in the result, from 1 to 28-31
     * @return a {@code IranianDate} based on this date with the requested day, not null
     * @throws DateTimeException if the day-of-month value is invalid,
     *                           or if the day-of-month is invalid for the month-year
     */
    public IranianDate withDayOfMonth(int dayOfMonth) {
        if (this.day == dayOfMonth) {
            return this;
        }
        return of(year, month, dayOfMonth);
    }

    /**
     * Returns a copy of this {@code IranianDate} with the day-of-year altered.
     * <p>
     * If the resulting date is invalid, an exception is thrown.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * @param dayOfYear the day-of-year to set in the result, from 1 to 365-366
     * @return a {@code IranianDate} based on this date with the requested day, not null
     * @throws DateTimeException if the day-of-year value is invalid,
     *                           or if the day-of-year is invalid for the year
     */
    public IranianDate withDayOfYear(int dayOfYear) {
        if (this.getDayOfYear() == dayOfYear) {
            return this;
        }
        return ofYearDay(year, dayOfYear);
    }

    //-----------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public IranianDate plus(TemporalAmount amountToAdd) {
        if (amountToAdd instanceof Period periodToAdd) {
            return plusMonths(periodToAdd.toTotalMonths()).plusDays(periodToAdd.getDays());
        }
        Objects.requireNonNull(amountToAdd, "amountToAdd");
        return (IranianDate) amountToAdd.addTo(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IranianDate plus(long amountToAdd, TemporalUnit unit) {
        if (unit instanceof ChronoUnit chronoUnit) {
            return switch (chronoUnit) {
                case DAYS -> plusDays(amountToAdd);
                case WEEKS -> plusWeeks(amountToAdd);
                case MONTHS -> plusMonths(amountToAdd);
                case YEARS -> plusYears(amountToAdd);
                case DECADES -> plusYears(Math.multiplyExact(amountToAdd, 10));
                case CENTURIES -> plusYears(Math.multiplyExact(amountToAdd, 100));
                case MILLENNIA -> plusYears(Math.multiplyExact(amountToAdd, 1000));
                default -> throw new UnsupportedTemporalTypeException("Unsupported unit: " + unit);
            };
        }
        return unit.addTo(this, amountToAdd);
    }

    //-----------------------------------------------------------------------

    /**
     * Returns a copy of this {@code IranianDate} with the specified number of years added.
     * <p>
     * This method adds the specified amount to the years field in three steps:
     * <ol>
     * <li>Add the input years to the year field</li>
     * <li>Check if the resulting date would be invalid</li>
     * <li>Adjust the day-of-month to the last valid day if necessary</li>
     * </ol>
     * <p>
     * For example, 2008-02-29 (leap year) plus one year would result in the
     * invalid date 2009-02-29 (standard year). Instead of returning an invalid
     * result, the last valid day of the month, 2009-02-28, is selected instead.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * @param yearsToAdd the years to add, may be negative
     * @return a {@code IranianDate} based on this date with the years added, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public IranianDate plusYears(long yearsToAdd) {
        if (yearsToAdd == 0) {
            return this;
        }
        int newYear = YEAR.checkValidIntValue(year + yearsToAdd);  // safe overflow
        return resolvePreviousValid(newYear, month, day);
    }

    /**
     * Returns a copy of this {@code IranianDate} with the specified number of months added.
     * <p>
     * This method adds the specified amount to the months field in three steps:
     * <ol>
     * <li>Add the input months to the month-of-year field</li>
     * <li>Check if the resulting date would be invalid</li>
     * <li>Adjust the day-of-month to the last valid day if necessary</li>
     * </ol>
     * <p>
     * For example, 2007-03-31 plus one month would result in the invalid date
     * 2007-04-31. Instead of returning an invalid result, the last valid day
     * of the month, 2007-04-30, is selected instead.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * @param monthsToAdd the months to add, may be negative
     * @return a {@code IranianDate} based on this date with the months added, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public IranianDate plusMonths(long monthsToAdd) {
        if (monthsToAdd == 0) {
            return this;
        }
        long monthCount = year * 12L + (month - 1);
        long calcMonths = monthCount + monthsToAdd;  // safe overflow
        int newYear = YEAR.checkValidIntValue(Math.floorDiv(calcMonths, 12));
        int newMonth = Math.floorMod(calcMonths, 12) + 1;
        return resolvePreviousValid(newYear, newMonth, day);
    }

    /**
     * Returns a copy of this {@code IranianDate} with the specified number of weeks added.
     * <p>
     * This method adds the specified amount in weeks to the days field incrementing
     * the month and year fields as necessary to ensure the result remains valid.
     * The result is only invalid if the maximum/minimum year is exceeded.
     * <p>
     * For example, 2008-12-31 plus one week would result in 2009-01-07.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * @param weeksToAdd the weeks to add, may be negative
     * @return a {@code IranianDate} based on this date with the weeks added, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public IranianDate plusWeeks(long weeksToAdd) {
        return plusDays(Math.multiplyExact(weeksToAdd, 7));
    }

    /**
     * Returns a copy of this {@code IranianDate} with the specified number of days added.
     * <p>
     * This method adds the specified amount to the days field incrementing the
     * month and year fields as necessary to ensure the result remains valid.
     * The result is only invalid if the maximum/minimum year is exceeded.
     * <p>
     * For example, 2008-12-31 plus one day would result in 2009-01-01.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * @param daysToAdd the days to add, may be negative
     * @return a {@code IranianDate} based on this date with the days added, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public IranianDate plusDays(long daysToAdd) {
        if (daysToAdd == 0) {
            return this;
        }
        long dom = day + daysToAdd;
        if (dom > 0) {
            if (dom <= 28) { // FIXME
                return new IranianDate(year, month, (int) dom);
            } else if (dom <= 59) { // 59th Jan is 28th Feb, 59th Feb is 31st Mar
                long monthLen = lengthOfMonth();
                if (dom <= monthLen) {
                    return new IranianDate(year, month, (int) dom);
                } else if (month < 12) {
                    return new IranianDate(year, month + 1, (int) (dom - monthLen));
                } else {
                    YEAR.checkValidValue(year + 1);
                    return new IranianDate(year + 1, 1, (int) (dom - monthLen));
                }
            }
        }

        long mjDay = Math.addExact(toEpochDay(), daysToAdd);
        return IranianDate.ofEpochDay(mjDay);
    }

    //-----------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public IranianDate minus(TemporalAmount amountToSubtract) {
        if (amountToSubtract instanceof Period periodToSubtract) {
            return minusMonths(periodToSubtract.toTotalMonths()).minusDays(periodToSubtract.getDays());
        }
        Objects.requireNonNull(amountToSubtract, "amountToSubtract");
        return (IranianDate) amountToSubtract.subtractFrom(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IranianDate minus(long amountToSubtract, TemporalUnit unit) {
        return (amountToSubtract == Long.MIN_VALUE
                ? plus(Long.MAX_VALUE, unit).plus(1, unit)
                : plus(-amountToSubtract, unit));
    }

    //-----------------------------------------------------------------------

    /**
     * Returns a copy of this {@code IranianDate} with the specified number of years subtracted.
     * <p>
     * This method subtracts the specified amount from the years field in three steps:
     * <ol>
     * <li>Subtract the input years from the year field</li>
     * <li>Check if the resulting date would be invalid</li>
     * <li>Adjust the day-of-month to the last valid day if necessary</li>
     * </ol>
     * <p>
     * For example, 2008-02-29 (leap year) minus one year would result in the
     * invalid date 2007-02-29 (standard year). Instead of returning an invalid
     * result, the last valid day of the month, 2007-02-28, is selected instead.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * @param yearsToSubtract the years to subtract, may be negative
     * @return a {@code IranianDate} based on this date with the years subtracted, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public IranianDate minusYears(long yearsToSubtract) {
        return (yearsToSubtract == Long.MIN_VALUE
                ? plusYears(Long.MAX_VALUE).plusYears(1)
                : plusYears(-yearsToSubtract));
    }

    /**
     * Returns a copy of this {@code IranianDate} with the specified number of months subtracted.
     * <p>
     * This method subtracts the specified amount from the months field in three steps:
     * <ol>
     * <li>Subtract the input months from the month-of-year field</li>
     * <li>Check if the resulting date would be invalid</li>
     * <li>Adjust the day-of-month to the last valid day if necessary</li>
     * </ol>
     * <p>
     * For example, 2007-03-31 minus one month would result in the invalid date
     * 2007-02-31. Instead of returning an invalid result, the last valid day
     * of the month, 2007-02-28, is selected instead.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * @param monthsToSubtract the months to subtract, may be negative
     * @return a {@code IranianDate} based on this date with the months subtracted, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public IranianDate minusMonths(long monthsToSubtract) {
        return (monthsToSubtract == Long.MIN_VALUE
                ? plusMonths(Long.MAX_VALUE).plusMonths(1)
                : plusMonths(-monthsToSubtract));
    }

    /**
     * Returns a copy of this {@code IranianDate} with the specified number of weeks subtracted.
     * <p>
     * This method subtracts the specified amount in weeks from the days field decrementing
     * the month and year fields as necessary to ensure the result remains valid.
     * The result is only invalid if the maximum/minimum year is exceeded.
     * <p>
     * For example, 2009-01-07 minus one week would result in 2008-12-31.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * @param weeksToSubtract the weeks to subtract, may be negative
     * @return a {@code IranianDate} based on this date with the weeks subtracted, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public IranianDate minusWeeks(long weeksToSubtract) {
        return (weeksToSubtract == Long.MIN_VALUE
                ? plusWeeks(Long.MAX_VALUE).plusWeeks(1)
                : plusWeeks(-weeksToSubtract));
    }

    /**
     * Returns a copy of this {@code IranianDate} with the specified number of days subtracted.
     * <p>
     * This method subtracts the specified amount from the days field decrementing the
     * month and year fields as necessary to ensure the result remains valid.
     * The result is only invalid if the maximum/minimum year is exceeded.
     * <p>
     * For example, 2009-01-01 minus one day would result in 2008-12-31.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * @param daysToSubtract the days to subtract, may be negative
     * @return a {@code IranianDate} based on this date with the days subtracted, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public IranianDate minusDays(long daysToSubtract) {
        return (daysToSubtract == Long.MIN_VALUE
                ? plusDays(Long.MAX_VALUE).plusDays(1)
                : plusDays(-daysToSubtract));
    }

    //-----------------------------------------------------------------------


    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public <R> R query(TemporalQuery<R> query) {
        if (query == TEMPORAL_QUERY) {
            return (R) this;
        }
        return ChronoLocalDate.super.query(query);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Temporal adjustInto(Temporal temporal) {
        return ChronoLocalDate.super.adjustInto(temporal);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long until(Temporal endExclusive, TemporalUnit unit) {
        IranianDate end = IranianDate.from(endExclusive);
        if (unit instanceof ChronoUnit chronoUnit) {
            return switch (chronoUnit) {
                case DAYS -> daysUntil(end);
                case WEEKS -> daysUntil(end) / 7;
                case MONTHS -> monthsUntil(end);
                case YEARS -> monthsUntil(end) / 12;
                case DECADES -> monthsUntil(end) / 120;
                case CENTURIES -> monthsUntil(end) / 1200;
                case MILLENNIA -> monthsUntil(end) / 12000;
                case ERAS -> end.getLong(ERA) - getLong(ERA);
                default -> throw new UnsupportedTemporalTypeException("Unsupported unit: " + unit);
            };
        }
        return unit.between(this, end);
    }

    long daysUntil(IranianDate end) {
        return end.toEpochDay() - toEpochDay();  // no overflow
    }

    private long monthsUntil(IranianDate end) {
        long packed1 = getProlepticMonth() * 32L + getDayOfMonth();  // no overflow
        long packed2 = end.getProlepticMonth() * 32L + end.getDayOfMonth();  // no overflow
        return (packed2 - packed1) / 32;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Period until(ChronoLocalDate endDateExclusive) {
        IranianDate end = IranianDate.from(endDateExclusive);
        long totalMonths = end.getProlepticMonth() - this.getProlepticMonth();  // safe
        int days = end.day - this.day;
        if (totalMonths > 0 && days < 0) {
            totalMonths--;
            IranianDate calcDate = this.plusMonths(totalMonths);
            days = (int) (end.toEpochDay() - calcDate.toEpochDay());  // safe
        } else if (totalMonths < 0 && days > 0) {
            totalMonths++;
            days -= end.lengthOfMonth();
        }
        long years = totalMonths / 12;  // safe
        int months = (int) (totalMonths % 12);  // safe
        return Period.of(Math.toIntExact(years), months, days);
    }

    /**
     * Returns a sequential ordered stream of dates. The returned stream starts from this date
     * (inclusive) and goes to {@code endExclusive} (exclusive) by an incremental step of 1 day.
     * <p>
     * This method is equivalent to {@code datesUntil(endExclusive, Period.ofDays(1))}.
     *
     * @param endExclusive the end date, exclusive, not null
     * @return a sequential {@code Stream} for the range of {@code IranianDate} values
     * @throws IllegalArgumentException if end date is before this date
     * @since 9
     */
    public Stream<IranianDate> datesUntil(IranianDate endExclusive) {
        long end = endExclusive.toEpochDay();
        long start = toEpochDay();
        if (end < start) {
            throw new IllegalArgumentException(endExclusive + " < " + this);
        }
        return LongStream.range(start, end).mapToObj(IranianDate::ofEpochDay);
    }

    /**
     * Returns a sequential ordered stream of dates by given incremental step. The returned stream
     * starts from this date (inclusive) and goes to {@code endExclusive} (exclusive).
     * <p>
     * The n-th date which appears in the stream is equal to {@code this.plus(step.multipliedBy(n))}
     * (but the result of step multiplication never overflows). For example, if this date is
     * {@code 2015-01-31}, the end date is {@code 2015-05-01} and the step is 1 month, then the
     * stream contains {@code 2015-01-31}, {@code 2015-02-28}, {@code 2015-03-31}, and
     * {@code 2015-04-30}.
     *
     * @param endExclusive the end date, exclusive, not null
     * @param step         the non-zero, non-negative {@code Period} which represents the step.
     * @return a sequential {@code Stream} for the range of {@code IranianDate} values
     * @throws IllegalArgumentException if step is zero, or {@code step.getDays()} and
     *                                  {@code step.toTotalMonths()} have opposite sign,
     *                                  or end date is before this date and step is positive,
     *                                  or end date is after this date and step is negative
     * @since 9
     */
    public Stream<IranianDate> datesUntil(IranianDate endExclusive, Period step) {
        if (step.isZero()) {
            throw new IllegalArgumentException("step is zero");
        }
        long end = endExclusive.toEpochDay();
        long start = toEpochDay();
        long until = end - start;
        long months = step.toTotalMonths();
        long days = step.getDays();
        if ((months < 0 && days > 0) || (months > 0 && days < 0)) {
            throw new IllegalArgumentException("period months and days are of opposite sign");
        }
        if (until == 0) {
            return Stream.empty();
        }
        int sign = months > 0 || days > 0 ? 1 : -1;
        if (sign < 0 ^ until < 0) {
            throw new IllegalArgumentException(endExclusive + (sign < 0 ? " > " : " < ") + this);
        }
        if (months == 0) {
            long steps = (until - sign) / days; // non-negative
            return LongStream.rangeClosed(0, steps).mapToObj(
                    n -> IranianDate.ofEpochDay(start + n * days));
        }
        // 48699/1600 = 365.2425/12, no overflow, non-negative result
        long steps = until * 1600 / (months * 48699 + days * 1600) + 1;
        long addMonths = months * steps;
        long addDays = days * steps;
        long maxAddMonths = months > 0 ? MAX.getProlepticMonth() - getProlepticMonth()
                : getProlepticMonth() - MIN.getProlepticMonth();
        // adjust steps estimation
        if (addMonths * sign > maxAddMonths
                || (plusMonths(addMonths).toEpochDay() + addDays) * sign >= end * sign) {
            steps--;
            addMonths -= months;
            addDays -= days;
            if (addMonths * sign > maxAddMonths
                    || (plusMonths(addMonths).toEpochDay() + addDays) * sign >= end * sign) {
                steps--;
            }
        }
        return LongStream.rangeClosed(0, steps).mapToObj(
                n -> this.plusMonths(months * n).plusDays(days * n));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String format(DateTimeFormatter formatter) {
        Objects.requireNonNull(formatter, "formatter");
        return formatter.format(this);
    }

    //-----------------------------------------------------------------------

    // TODO TIME

    //-----------------------------------------------------------------------

    // TODO EPOCH

    //-----------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAfter(ChronoLocalDate other) {
        if (other instanceof IranianDate) {
            return compareTo0((IranianDate) other) > 0;
        }
        return ChronoLocalDate.super.isAfter(other);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBefore(ChronoLocalDate other) {
        if (other instanceof IranianDate) {
            return compareTo0((IranianDate) other) < 0;
        }
        return ChronoLocalDate.super.isBefore(other);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEqual(ChronoLocalDate other) {
        if (other instanceof IranianDate) {
            return compareTo0((IranianDate) other) == 0;
        }
        return ChronoLocalDate.super.isEqual(other);
    }

    //-----------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return (year & 0xFFFFF800) ^ ((year << 11) + (month << 6) + day);
    }

    //-----------------------------------------------------------------------

    /**
     * Outputs this date as a {@code String}, such as {@code 2007-12-03}.
     * <p>
     * The output will be in the ISO-8601 format {@code uuuu-MM-dd}.
     *
     * @return a string representation of this date, not null
     */
    @Override
    public String toString() {
        var buf = new StringBuilder(10);
        formatTo(buf);
        return buf.toString();
    }

    /**
     * Prints the toString result to the given buf, avoiding extra string allocations.
     * Requires extra capacity of 10 to avoid StringBuilder reallocation.
     */
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

    //-----------------------------------------------------------------------

    /**
     * Writes the object using a
     * <a href="{@docRoot}/serialized-form.html#java.time.Ser">dedicated serialized form</a>.
     *
     * @return the instance of {@code Ser}, not null
     * @serialData <pre>
     *  out.writeByte(3);  // identifies an IranianDate
     *  out.writeInt(year);
     *  out.writeByte(month);
     *  out.writeByte(day);
     * </pre>
     */
    @java.io.Serial
    private Object writeReplace() {
        //return new Ser(Ser.LOCAL_DATE_TYPE, this);
        throw new Exception("TODO");
    }

    /**
     * Defend against malicious streams.
     *
     * @param s the stream to read
     * @throws InvalidObjectException always
     */
    @java.io.Serial
    private void readObject(ObjectInputStream s) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    void writeExternal(DataOutput out) throws IOException {
        out.writeInt(year);
        out.writeByte(month);
        out.writeByte(day);
    }

    static IranianDate readExternal(DataInput in) throws IOException {
        int year = in.readInt();
        int month = in.readByte();
        int dayOfMonth = in.readByte();
        return IranianDate.of(year, month, dayOfMonth);
    }

    //-----------------------------------------------------------------------


    static class EraNotSupportedException extends UnsupportedTemporalTypeException {
        private static final String msg = "Eras are not supported.";

        public EraNotSupportedException() {
            super(msg);
        }
    }
}
