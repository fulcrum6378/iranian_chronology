package ir.mahdiparastesh.chrono.iranian;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IranianChronologyTest {

    @Test
    public void type() {
        assertEquals("Iranian", IranianChronology.INSTANCE.getId());
        assertEquals("iranian", IranianChronology.INSTANCE.getCalendarType());
    }

    @Test
    public void isLeapYear() {
        assertTrue(IranianChronology.INSTANCE.isLeapYear(6403));
        assertFalse(IranianChronology.INSTANCE.isLeapYear(6404));
    }
}
