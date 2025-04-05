package ir.mahdiparastesh.chrono.iranian;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class IranianDateTest {

    IranianDate today = IranianDate.of(6404, 1, 16);

    @Test
    public void type() {
        assertInstanceOf(IranianChronology.class, today.getChronology());
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
}
