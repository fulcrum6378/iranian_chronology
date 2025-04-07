package ir.mahdiparastesh.chrono;

import org.junit.jupiter.api.Test;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IranianDateMutationTest {

    @Test
    public void plus() {
        assertEquals(
                IranianDate.of(6404, 1, 18),
                IranianDate.of(6404, 1, 16).plus(2, DAYS)
        );
    }

    @Test
    public void minus() {
        assertEquals(
                IranianDate.of(6404, 1, 16),
                IranianDate.of(6404, 1, 18).minus(2, DAYS)
        );
    }
}
