package ir.mahdiparastesh.chrono.iranian;

import java.io.Serializable;
import java.time.chrono.AbstractChronology;

public class IranianChronology extends AbstractChronology implements Serializable {

    /**
     * Singleton instance for Iranian chronology.
     */
    public static final IranianChronology INSTANCE = new IranianChronology();

    /**
     * Restricted constructor.
     */
    private IranianChronology() {
    }

    /**
     * Gets the ID of the chronology.
     * <p>
     * The ID uniquely identifies the {@code Chronology}.
     * It can be used to lookup the {@code Chronology} using {@link #of(String)}.
     *
     * @return the chronology ID, not null
     * @see #getCalendarType()
     */
    @Override
    public String getId() {
        return "Iranian";
    }

    /**
     * Gets the calendar type of the calendar system.
     * <p>
     * The calendar type is an identifier defined by the CLDR and
     * <em>Unicode Locale Data Markup Language (LDML)</em> specifications
     * to uniquely identify a calendar.
     * The {@code getCalendarType} is the concatenation of the CLDR calendar type
     * and the variant, if applicable, is appended separated by "-".
     * The calendar type is used to lookup the {@code Chronology} using {@link #of(String)}.
     *
     * @return the calendar system type, null if the calendar is not defined by CLDR/LDML
     * @see #getId()
     */
    @Override
    public String getCalendarType() {
        return "iranian";
    }

    /**
     * Obtains a local date in this chronology from the proleptic-year,
     * month-of-year and day-of-month fields.
     *
     * @param prolepticYear the chronology proleptic-year
     * @param month         the chronology month-of-year
     * @param dayOfMonth    the chronology day-of-month
     * @return the local date in this chronology, not null
     * @throws DateTimeException if unable to create the date
     */
    @Override
    public IranianDate date(int prolepticYear, int month, int dayOfMonth) {
        return null;
    }
}
