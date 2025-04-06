package ir.mahdiparastesh.chrono;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.UnsupportedTemporalTypeException;

import static java.time.temporal.ChronoField.*;
import static org.junit.jupiter.api.Assertions.*;

public class IranianDateTest {

    IranianDate testingDate = IranianDate.of(6404, 1, 16);
    LocalDate greTestingDate = LocalDate.of(2025, 4, 5);

    @Test
    public void creation() {
        assertEquals(
                IranianDate.of(6404, 12, 29),
                IranianDate.ofYearDay(6404, 365)
        );
        assertInstanceOf(IranianChronology.class, testingDate.getChronology());
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
        assertEquals(  // Epoch time
                LocalDate.of(1970, 1, 1).toEpochDay(),
                IranianDate.of(6348, 10, 11).toEpochDay()
        );
        assertEquals(  // Apollo 11 launch date
                LocalDate.of(1969, 7, 24).toEpochDay(),
                IranianDate.of(6348, 5, 2).toEpochDay()
        );
        assertEquals(  // Albert Einstein's birthday
                LocalDate.of(1879, 3, 14).toEpochDay(),
                IranianDate.of(6257, 12, 23).toEpochDay()
        );
        assertEquals(  // Charles Darwin's birthday
                LocalDate.of(1809, 2, 12).toEpochDay(),
                IranianDate.of(6187, 11, 23).toEpochDay()
        );
    }

    @Test
    public void comparison() {
        assertTrue(IranianDate.of(6404, 12, 29).isAfter(testingDate));
        assertTrue(IranianDate.of(6403, 12, 29).isBefore(testingDate));
        assertTrue(IranianDate.of(6404, 1, 16).isEqual(testingDate));
    }
}
