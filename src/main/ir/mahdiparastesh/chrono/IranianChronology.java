/*
 * Copyright 2025 Mahdi Parastesh
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the “Software”),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom
 * the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package ir.mahdiparastesh.chrono;

import java.time.chrono.AbstractChronology;
import java.time.chrono.Era;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;
import java.util.List;

public class IranianChronology extends AbstractChronology {

    public static final IranianChronology INSTANCE = new IranianChronology();

    private static final double LEAP_THRESHOLD = 0.24219858156028368;

    @Override
    public String getId() {
        return "Iranian";
    }

    @Override
    public String getCalendarType() {
        return "iranian";
    }

    @Override
    public IranianDate date(int prolepticYear, int month, int dayOfMonth) {
        return IranianDate.of(prolepticYear, month, dayOfMonth);
    }

    @Override
    public IranianDate dateYearDay(int prolepticYear, int dayOfYear) {
        return IranianDate.ofYearDay(prolepticYear, dayOfYear);
    }

    @Override
    public IranianDate dateEpochDay(long epochDay) {
        return IranianDate.ofEpochDay(epochDay);
    }

    @Override
    public IranianDate date(TemporalAccessor temporal) {
        return dateEpochDay(temporal.getLong(ChronoField.EPOCH_DAY));
    }

    @Override
    public boolean isLeapYear(long prolepticYear) {
        return ((prolepticYear - 2654) * LEAP_THRESHOLD % 1) < LEAP_THRESHOLD;
    }

    @Override
    public int prolepticYear(Era era, int yearOfEra) {
        return yearOfEra;
    }

    @Override
    public Era eraOf(int eraValue) {
        return null;
    }

    @Override
    public List<Era> eras() {
        return null;
    }

    @Override
    public ValueRange range(ChronoField field) {
        return switch (field) {
            case ChronoField.DAY_OF_MONTH -> ValueRange.of(1, 29, 31);
            case ChronoField.ALIGNED_WEEK_OF_MONTH -> ValueRange.of(1, 5);
            default -> field.range();
        };
    }

    public static class EraNotSupportedException
            extends UnsupportedTemporalTypeException {
        private static final String msg = "Eras are not supported.";

        public EraNotSupportedException() {
            super(msg);
        }
    }
}
