package ir.mahdiparastesh.chrono.iranian;

import java.time.LocalDate;

/**
 * Calculates epoch days from a given date.
 * 1970/01/01 == 6348/10/11 (Thursday; both normal years)
 */
public class EpochTimeTravel {
    private final LocalDate epochStart = LocalDate.of(1970, 1, 1);

    public static void main(String[] args) {
        int destination;
        try {
            destination = Integer.parseInt(args[0]);
        } catch (Exception ignored) {
            throw new IllegalArgumentException("Please provide one number as the destination.");
        }
        int year = 6403;  // TODO IranianDate.now().get(ChronoField.YEAR)

        if (year <= destination)
            throw new IllegalArgumentException("Destination cannot be later than now.");
        System.out.println("Destination is set to " + destination +
                " which is" + (IranianChronology.INSTANCE.isLeapYear(destination) ? " " : " not ") + "a leap year." +
                "\n");

        long days = 0;
        while (year > destination) {
            if ((year % 10) == 0)
                System.out.println(days + " days since year " + year + "...");
            days += IranianChronology.INSTANCE.isLeapYear(year) ? 366 : 365;
            year--;
        }
        System.out.println("\nFinally: " + days + " days!");
        /*if (destination == 6349)
            System.out.println("Plus days until : " + days + " days!");*/
    }
}
