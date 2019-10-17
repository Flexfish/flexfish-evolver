package peterfajdiga.flexfish.evolver.chromosome;

public class BoolArrays {
    public static int sum(final boolean[] values) {
        int count = 0;
        for (final boolean value : values) {
            if (value) {
                count++;
            }
        }
        return count;
    }

    public static boolean[] ones(final int n) {
        final boolean[] ones = new boolean[n];
        for (int i = 0; i < n; i++) {
            ones[i] = true;
        }
        return ones;
    }

    public static boolean[] onesZeros(final int nFull, final int nOnes) {
        assert nOnes <= nFull;
        final boolean[] ones = new boolean[nFull];
        for (int i = 0; i < nOnes; i++) {
            ones[i] = true;
        }
        return ones;
    }

    public static boolean[] setEveryNthToFalse(final boolean[] original, final int n) {
        final boolean[] copy = original.clone();
        for (int i = 0; i < copy.length; i += n) {
            copy[i] = false;
        }
        return copy;
    }

    public static boolean[] setStringsOfNToFalse(final boolean[] original, final int n, final int offset) {
        assert offset < n;
        final boolean[] copy = original.clone();
        final int n1 = n-1;
        for (int i = 0; i < copy.length; i++) {
            if (i % n != offset) {
                copy[i] = false;
            }
        }
        return copy;
    }

    public static boolean[] makeClustersSize(final boolean[] original, final int clusterSize) {
        final boolean[] copy = setStringsOfNToFalse(original, clusterSize-1, 0);
        copy[0] = false;
        copy[copy.length-1] = false;
        return copy;
    }

    public static boolean[] setFirstTrueToFalse(final boolean[] original) {
        final boolean[] copy = original.clone();
        for (int i = 0; i < copy.length; i++) {
            if (copy[i]) {
                copy[i] = false;
                break;
            }
        }
        return copy;
    }

    public static boolean[] setLastTrueToFalse(final boolean[] original) {
        final boolean[] copy = original.clone();
        for (int i = copy.length-1; i >= 0; i--) {
            if (copy[i]) {
                copy[i] = false;
                break;
            }
        }
        return copy;
    }
}
