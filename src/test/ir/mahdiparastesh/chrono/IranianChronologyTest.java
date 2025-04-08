package ir.mahdiparastesh.chrono;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class IranianChronologyTest {

    @Test
    public void type() {
        assertEquals("Iranian", IranianChronology.INSTANCE.getId());
        assertEquals("iranian", IranianChronology.INSTANCE.getCalendarType());
    }

    @Test
    public void date() {
        assertEquals(
                IranianDate.of(6404, 1, 16),
                IranianChronology.INSTANCE.date(6404, 1, 16)
        );
        assertEquals(
                IranianDate.of(6404, 12, 29),
                IranianChronology.INSTANCE.dateYearDay(6404, 365)
        );
        assertEquals(
                IranianDate.of(6404, 1, 16),
                IranianChronology.INSTANCE.date(LocalDate.of(2025, 4, 5))
        );
    }

    @Test
    public void dateNow() {
        assertDoesNotThrow(() -> IranianChronology.INSTANCE.dateNow());
        /*assertEquals(
                IranianDate.of(6404, 1, 18),
                IranianChronology.INSTANCE.dateNow()
        );*/
    }

    @Test
    public void isLeapYear() {
        assertFalse(IranianChronology.INSTANCE.isLeapYear(1404));
        assertTrue(IranianChronology.INSTANCE.isLeapYear(1403));
        assertTrue(IranianChronology.INSTANCE.isLeapYear(1391));
    }
}
