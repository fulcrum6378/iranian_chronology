package ir.mahdiparastesh.chrono.iranian;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class IranianDateTest {

    @Test
    public void type() {
        IranianDate date = new IranianDate();
        assertInstanceOf(IranianChronology.class, date.getChronology());
    }
}
