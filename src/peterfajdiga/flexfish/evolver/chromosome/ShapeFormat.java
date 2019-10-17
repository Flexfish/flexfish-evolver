package peterfajdiga.flexfish.evolver.chromosome;

import peterfajdiga.flexfish.evolver.chromosome.expressor.Expressor;

public class ShapeFormat {
    public final int length;
    final Expressor<Integer> heights;
    final Expressor<Integer> widths;
    final String name;

    public ShapeFormat(
            final int length,
            final Expressor<Integer> heights,
            final Expressor<Integer> widths,
            final String name
    ) {
        this.length = length;
        this.heights = heights;
        this.widths = widths;
        this.name = name;
    }
}
