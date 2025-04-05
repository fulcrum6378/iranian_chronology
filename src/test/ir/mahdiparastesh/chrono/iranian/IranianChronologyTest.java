package ir.mahdiparastesh.chrono.iranian;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IranianChronologyTest {

    @Test
    public void type() {
        assertEquals("Iranian", IranianChronology.INSTANCE.getId());
        assertEquals("iranian", IranianChronology.INSTANCE.getCalendarType());
    }
}
