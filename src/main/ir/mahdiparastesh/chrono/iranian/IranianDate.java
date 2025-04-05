package ir.mahdiparastesh.chrono.iranian;

import java.io.Serializable;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoPeriod;
import java.time.chrono.Chronology;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;

public class IranianDate
        implements Temporal, TemporalAdjuster, ChronoLocalDate, Serializable {

    @Override
    public Chronology getChronology() {
        return IranianChronology.INSTANCE;
    }

    @Override
    public int lengthOfMonth() {
        return 0;
    }

    @Override
    public ChronoPeriod until(ChronoLocalDate endDateExclusive) {
        return null;
    }

    @Override
    public long until(Temporal endExclusive, TemporalUnit unit) {
        return 0;
    }

    @Override
    public long getLong(TemporalField field) {
        return 0;
    }
}
