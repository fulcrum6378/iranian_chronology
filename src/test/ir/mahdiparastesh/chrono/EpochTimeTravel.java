package ir.mahdiparastesh.chrono;

import java.time.LocalDate;

/**
 * Calculates epoch days from a given date.
 */
@SuppressWarnings("unused")
public class EpochTimeTravel {

    private static final LocalDate epochStart =
            LocalDate.of(1970, 1, 1);  // Thursday
    private static final LocalDate irEpochStart =
            LocalDate.of(6348, 10, 11);

    private static final LocalDate thisNowruz =
            LocalDate.of(2025, 3, 21);

    public static void main(String[] args) {
        int destination;
        try {
            destination = Integer.parseInt(args[0]);
        } catch (Exception ignored) {
            throw new IllegalArgumentException("Please provide one number as the destination.");
        }
        int year = 6403;  // TO-DO IranianDate.now().get(ChronoField.YEAR)

        if (year <= destination)
            throw new IllegalArgumentException("Destination cannot be later than now.");
        System.out.println("Destination is set to " + destination +
                " which is" + (IranianChronology.INSTANCE.isLeapYear(destination) ? " " : " not ") + "a leap year." +
                "\n");

        long days = 0;
        while (year >= destination) {
            days += IranianChronology.INSTANCE.isLeapYear(year) ? 366 : 365;
            year--;
        }
        System.out.println("Finally: " + days + " + 79 = " + (days + 79) + " days");
        System.out.println("While 2025/3/21 == " + thisNowruz.toEpochDay() + " epoch days");
    }
}
