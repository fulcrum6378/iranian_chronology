package ir.mahdiparastesh.chrono;

import org.junit.jupiter.api.Test;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.WEEKS;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IranianDateMutationTest {

    @Test
    public void with() {
        assertEquals(
                IranianDate.of(6404, 1, 18),
                IranianDate.of(6404, 1, 16)
                        .with(IranianDate.of(6404, 1, 18))
        );

        assertEquals(
                IranianDate.of(6404, 1, 18),
                IranianDate.of(6404, 1, 16).with(DAY_OF_MONTH, 18)
        );
        assertEquals(
                IranianDate.of(6404, 12, 29),
                IranianDate.of(6403, 12, 30).with(YEAR, 6404)
        );
    }

    @Test
    public void plus() {
        assertEquals(
                IranianDate.of(6404, 1, 18),
                IranianDate.of(6404, 1, 16).plus(2, DAYS)
        );
        assertEquals(
                IranianDate.of(6404, 1, 1),
                IranianDate.of(6403, 12, 30).plus(1, DAYS)
        );
        assertEquals(
                IranianDate.of(6404, 1, 7),
                IranianDate.of(6403, 12, 30).plus(1, WEEKS)
        );
    }

    @Test
    public void minus() {
        assertEquals(
                IranianDate.of(6404, 1, 16),
                IranianDate.of(6404, 1, 18).minus(2, DAYS)
        );
        assertEquals(
                IranianDate.of(6403, 12, 30),
                IranianDate.of(6404, 1, 1).minus(1, DAYS)
        );
        assertEquals(
                IranianDate.of(6403, 12, 30),
                IranianDate.of(6404, 1, 7).minus(1, WEEKS)
        );
    }
}
