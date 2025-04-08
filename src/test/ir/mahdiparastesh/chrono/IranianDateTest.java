package ir.mahdiparastesh.chrono;

import org.junit.jupiter.api.Test;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.chrono.ChronoPeriod;
import java.time.temporal.UnsupportedTemporalTypeException;

import static java.time.temporal.ChronoField.*;
import static java.time.temporal.ChronoUnit.*;
import static org.junit.jupiter.api.Assertions.*;

public class IranianDateTest {

    IranianDate testingDate = IranianDate.of(6404, 1, 16);
    LocalDate greTestingDate = LocalDate.of(2025, 4, 5);

    @Test
    public void builders() {
        // of
        assertThrows(DateTimeException.class, () -> IranianDate.of(6404, 13, 1));
        assertThrows(DateTimeException.class, () -> IranianDate.of(6404, 0, 1));
        assertThrows(DateTimeException.class, () -> IranianDate.of(6404, 1, 0));
        assertThrows(DateTimeException.class, () -> IranianDate.of(6404, 12, 30));

        // ofYearDay
        assertEquals(
                IranianDate.of(6404, 12, 29),
                IranianDate.ofYearDay(6404, 365)
        );

        // ofEpochDay
        assertEquals(
                IranianDate.of(6404, 1, 1),
                IranianDate.ofEpochDay(LocalDate.of(2025, 3, 21).toEpochDay())
        );
        assertEquals(
                IranianDate.of(6403, 12, 30),
                IranianDate.ofEpochDay(LocalDate.of(2025, 3, 20).toEpochDay())
        );
        assertEquals(
                IranianDate.of(6403, 12, 29),
                IranianDate.ofEpochDay(LocalDate.of(2025, 3, 19).toEpochDay())
        );
        assertEquals(  // my birthday
                IranianDate.of(6378, 11, 17),
                IranianDate.ofEpochDay(LocalDate.of(2000, 2, 6).toEpochDay())
        );
        assertEquals(  // Apollo 13 launch date
                IranianDate.of(6349, 1, 22),
                IranianDate.ofEpochDay(LocalDate.of(1970, 4, 11).toEpochDay())
        );
        assertEquals(
                IranianDate.of(6349, 1, 1),
                IranianDate.ofEpochDay(LocalDate.of(1970, 3, 21).toEpochDay())
        );
        assertEquals(
                IranianDate.of(6348, 12, 29),
                IranianDate.ofEpochDay(LocalDate.of(1970, 3, 20).toEpochDay())
        );
        assertEquals(  // Epoch time
                IranianDate.of(6348, 10, 11),
                IranianDate.ofEpochDay(LocalDate.of(1970, 1, 1).toEpochDay())
        );
        assertEquals(  // Apollo 11 launch date
                IranianDate.of(6348, 5, 2),
                IranianDate.ofEpochDay(LocalDate.of(1969, 7, 24).toEpochDay())
        );
        assertEquals(
                IranianDate.of(6348, 1, 1),
                IranianDate.ofEpochDay(LocalDate.of(1969, 3, 21).toEpochDay())
        );
        assertEquals(
                IranianDate.of(6347, 12, 29),
                IranianDate.ofEpochDay(LocalDate.of(1969, 3, 20).toEpochDay())
        );
        assertEquals(  // Sigmund Freud's birthday
                IranianDate.of(6235, 2, 16),
                IranianDate.ofEpochDay(LocalDate.of(1856, 5, 6).toEpochDay())
        );
    }


    // --- BEGIN GETTERS ---

    @Test
    public void chronology() {
        assertInstanceOf(IranianChronology.class, testingDate.getChronology());
        assertEquals(IranianChronology.INSTANCE, testingDate.getChronology());
    }

    @Test
    public void lengthOfMonth() {
        assertEquals(30, IranianDate.of(6403, 12, 1).lengthOfMonth());
        assertEquals(31, IranianDate.of(6404, 1, 1).lengthOfMonth());
        assertEquals(31, IranianDate.of(6404, 2, 1).lengthOfMonth());
        assertEquals(31, IranianDate.of(6404, 3, 1).lengthOfMonth());
        assertEquals(31, IranianDate.of(6404, 4, 1).lengthOfMonth());
        assertEquals(31, IranianDate.of(6404, 5, 1).lengthOfMonth());
        assertEquals(31, IranianDate.of(6404, 6, 1).lengthOfMonth());
        assertEquals(30, IranianDate.of(6404, 7, 1).lengthOfMonth());
        assertEquals(30, IranianDate.of(6404, 8, 1).lengthOfMonth());
        assertEquals(30, IranianDate.of(6404, 9, 1).lengthOfMonth());
        assertEquals(30, IranianDate.of(6404, 10, 1).lengthOfMonth());
        assertEquals(30, IranianDate.of(6404, 11, 1).lengthOfMonth());
        assertEquals(29, IranianDate.of(6404, 12, 1).lengthOfMonth());
    }

    @Test
    public void getInt() {
        assertEquals(6, testingDate.get(DAY_OF_WEEK));
        assertEquals(2, testingDate.get(ALIGNED_DAY_OF_WEEK_IN_MONTH));
        assertEquals(2, testingDate.get(ALIGNED_DAY_OF_WEEK_IN_YEAR));
        assertEquals(16, testingDate.get(DAY_OF_MONTH));
        assertEquals(16, testingDate.get(DAY_OF_YEAR));
        assertEquals(3, testingDate.get(ALIGNED_WEEK_OF_MONTH));
        assertEquals(3, testingDate.get(ALIGNED_WEEK_OF_YEAR));
        assertEquals(1, testingDate.get(MONTH_OF_YEAR));
        assertEquals(6404, testingDate.get(YEAR));

        assertThrows(UnsupportedTemporalTypeException.class, () -> testingDate.get(EPOCH_DAY));
        assertThrows(UnsupportedTemporalTypeException.class, () -> testingDate.get(PROLEPTIC_MONTH));
        assertThrows(IranianChronology.EraNotSupportedException.class, () -> testingDate.get(ERA));
        assertThrows(IranianChronology.EraNotSupportedException.class, () -> testingDate.get(YEAR_OF_ERA));
    }

    @SuppressWarnings("CommentedOutCode")
    @Test
    public void getLong() {
        assertEquals(76848L, testingDate.getLong(PROLEPTIC_MONTH));

        assertEquals(greTestingDate.toEpochDay(), testingDate.toEpochDay());
        assertEquals(  // my brother's birthday
                LocalDate.of(2006, 10, 4).toEpochDay(),
                IranianDate.of(6385, 7, 12).toEpochDay()
        );
        assertEquals(  // my birthday
                LocalDate.of(2000, 2, 6).toEpochDay(),
                IranianDate.of(6378, 11, 17).toEpochDay()
        );
        assertEquals(  // Mobina Jafarian's birthday
                LocalDate.of(1999, 8, 21).toEpochDay(),
                IranianDate.of(6378, 5, 30).toEpochDay()
        );
        assertEquals(  // dissolution of the Soviet Union
                LocalDate.of(1991, 12, 26).toEpochDay(),
                IranianDate.of(6370, 10, 5).toEpochDay()
        );
        assertEquals(  // Apollo 13 launch date
                LocalDate.of(1970, 4, 11).toEpochDay(),
                IranianDate.of(6349, 1, 22).toEpochDay()
        );
        assertEquals(
                LocalDate.of(1970, 3, 21).toEpochDay(),
                IranianDate.of(6349, 1, 1).toEpochDay()
        );
        assertEquals(
                LocalDate.of(1970, 3, 20).toEpochDay(),
                IranianDate.of(6348, 12, 29).toEpochDay()
        );
        assertEquals(  // Epoch time
                LocalDate.of(1970, 1, 1).toEpochDay(),
                IranianDate.of(6348, 10, 11).toEpochDay()
        );
        assertEquals(  // Apollo 11 launch date
                LocalDate.of(1969, 7, 24).toEpochDay(),
                IranianDate.of(6348, 5, 2).toEpochDay()
        );
        assertEquals(
                LocalDate.of(1969, 3, 21).toEpochDay(),
                IranianDate.of(6348, 1, 1).toEpochDay()
        );
        assertEquals(
                LocalDate.of(1969, 3, 20).toEpochDay(),
                IranianDate.of(6347, 12, 29).toEpochDay()
        );
        assertEquals(  // Albert Einstein's birthday
                LocalDate.of(1879, 3, 14).toEpochDay(),
                IranianDate.of(6257, 12, 23).toEpochDay()
        );
        assertEquals(  // Sigmund Freud's birthday
                LocalDate.of(1856, 5, 6).toEpochDay(),
                IranianDate.of(6235, 2, 16).toEpochDay()
        );
        assertEquals(  // Charles Darwin's birthday
                LocalDate.of(1809, 2, 12).toEpochDay(),
                IranianDate.of(6187, 11, 23).toEpochDay()
        );
        assertEquals(  // Isaac Newton's birthday
                LocalDate.of(1643, 1, 4).toEpochDay(),
                IranianDate.of(6021, 10, 14).toEpochDay()
                // according to {Google, PersianCalendar, Fourmilab's converter}
        );
        /*assertEquals(  // Trijntje Keever's birthday
                LocalDate.of(1616, 4, 10).toEpochDay(),  // -129196
                IranianDate.of(5995, 1, 22).toEpochDay()  // -129195
                // according to PersianCalendar & Fourmilab's converter
        );*/
        /*assertEquals(  // Avicenna's death
                LocalDate.of(1037, 7, 22).toEpochDay(),  // -340569
                IranianDate.of(5416, 4, 1).toEpochDay()  // -340614
                // according only to Fourmilab's converter; differed in PersianCalendar
        );*/
    }

    // --- END GETTERS ---


    @Test
    public void comparison() {
        assertTrue(IranianDate.of(6404, 12, 29).isAfter(testingDate));
        assertTrue(IranianDate.of(6403, 12, 29).isBefore(testingDate));
        assertTrue(IranianDate.of(6404, 1, 16).isEqual(testingDate));
    }

    @Test
    public void proximity() {
        // unfortunately we cannot access ChronoPeriodImpl returned by until(ChronoLocalDate).
        ChronoPeriod
                testingUntilOrdibehesht = testingDate.until(IranianDate.of(6404, 2, 1)),
                testingUntilPrevYear = testingDate.until(IranianDate.of(6403, 1, 16));
        assertEquals(16, testingUntilOrdibehesht.get(DAYS));
        assertEquals(0, testingUntilOrdibehesht.get(MONTHS));
        assertEquals(0, testingUntilOrdibehesht.get(YEARS));
        assertEquals(0, testingUntilPrevYear.get(DAYS));
        assertEquals(0, testingUntilPrevYear.get(MONTHS));
        assertEquals(1, testingUntilPrevYear.get(YEARS));

        assertEquals(
                greTestingDate.until(LocalDate.now(), DAYS),
                testingDate.until(IranianChronology.INSTANCE.dateNow(), DAYS)
        );
        assertEquals(
                greTestingDate.until(LocalDate.now(), WEEKS),
                testingDate.until(IranianChronology.INSTANCE.dateNow(), WEEKS)
        );
        assertEquals(
                greTestingDate.until(LocalDate.now(), MONTHS),
                testingDate.until(IranianChronology.INSTANCE.dateNow(), MONTHS)
        );
    }
}
