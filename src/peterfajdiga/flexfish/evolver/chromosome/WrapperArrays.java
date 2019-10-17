package peterfajdiga.flexfish.evolver.chromosome;

import java.io.PrintStream;
import java.util.Scanner;

public class WrapperArrays {
    private WrapperArrays() {}

    public static int min(final Integer[] values) {
        int min = Integer.MAX_VALUE;
        for (final int value : values) {
            if (value < min) {
                min = value;
            }
        }
        return min;
    }

    public static int max(final Integer[] values) {
        int max = Integer.MIN_VALUE;
        for (final int value : values) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    public static double min(final Double[] values) {
        double min = Double.POSITIVE_INFINITY;
        for (final double value : values) {
            if (value < min) {
                min = value;
            }
        }
        return min;
    }

    public static double max(final Double[] values) {
        double max = Double.NEGATIVE_INFINITY;
        for (final double value : values) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    public static int sum(final Boolean[] values) {
        int count = 0;
        for (final boolean value : values) {
            if (value) {
                count++;
            }
        }
        return count;
    }

    public static void scanDoubles(final Double[] dest, final Scanner scanner) {
        for (int i = 0; i < dest.length; i++) {
            dest[i] = scanner.nextDouble();
        }
    }

    public static void printDoubles(final Double[] source, final PrintStream out) {
        for (final Double value : source) {
            out.print(value);
            out.print(' ');
        }
        out.println();
    }
}
