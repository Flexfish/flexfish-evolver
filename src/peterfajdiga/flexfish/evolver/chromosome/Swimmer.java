package peterfajdiga.flexfish.evolver.chromosome;

import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Swimmer {
    final int length;
    final Integer[] heights;
    final Integer[] widths;
    final Boolean[] hinges;
    final Double[] frequencies;
    final Double[] powers;
    final Double[] amplitudes;
    final Double[] phases;

    public Swimmer(final int length) {
        this.length  = length;
        this.heights = new Integer[length];
        this.widths  = new Integer[length];
        this.hinges  = new Boolean[length];
        final int motorCount = length - 1;
        this.frequencies = new Double[motorCount];
        this.powers      = new Double[motorCount];
        this.amplitudes  = new Double[motorCount];
        this.phases      = new Double[motorCount];
    }

    public Swimmer(final Scanner scanner) {
        this(scanner.nextInt());

        for (int i = 0; i < length; i++) {
            heights[i] = scanner.nextInt();
            widths[i]  = scanner.nextInt();
        }

        for (int i = 0; i < length; i++) {
            switch (scanner.next()) {
                case "0": hinges[i] = Boolean.FALSE; break;
                case "1": hinges[i] = Boolean.TRUE; break;
                default: throw new InputMismatchException();
            }
        }

        WrapperArrays.scanDoubles(frequencies, scanner);
        WrapperArrays.scanDoubles(powers, scanner);
        WrapperArrays.scanDoubles(amplitudes, scanner);
        WrapperArrays.scanDoubles(phases, scanner);
        assert isValid();
    }

    public void print(final PrintStream out) {
        out.println(length);

        for (int i = 0; i < length; i++) {
            out.print(heights[i]);
            out.print(' ');
            out.println(widths[i]);
        }

        for (int i = 0; i < length; i++) {
            out.print(hinges[i] ? "1" : "0");
            out.print(' ');
        }
        out.println();

        WrapperArrays.printDoubles(frequencies, out);
        WrapperArrays.printDoubles(powers, out);
        WrapperArrays.printDoubles(amplitudes, out);
        WrapperArrays.printDoubles(phases, out);
    }

    public boolean isValid() {
        final int motorCount = length - 1;
        return length > 2 &&
                length == heights.length &&
                length == widths.length &&
                length == hinges.length &&
                motorCount == frequencies.length &&
                motorCount == powers.length &&
                motorCount == amplitudes.length &&
                motorCount == phases.length &&
                WrapperArrays.min(heights) > 0 &&
                WrapperArrays.min(widths) > 0 &&
                WrapperArrays.min(amplitudes) >= 0.0 &&
                WrapperArrays.max(amplitudes) <= 1.0 &&
                WrapperArrays.min(phases) >= 0.0 &&
                WrapperArrays.max(phases) <= 1.0;
    }
}
