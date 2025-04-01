package ir.mahdiparastesh.chrono.iranian;

import java.io.Serializable;
import java.time.chrono.AbstractChronology;

public class IranianChronology extends AbstractChronology implements Serializable {

    public static final IranianChronology INSTANCE = new IranianChronology();

    private IranianChronology() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
        return "Iranian";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCalendarType() {
        return "iranian";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IranianDate date(int prolepticYear, int month, int dayOfMonth) {
        return null; // TODO
    }
}
