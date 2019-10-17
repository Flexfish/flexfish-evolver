package peterfajdiga.flexfish.evolver.chromosome.expressor;

import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.InvalidConfigurationException;
import peterfajdiga.flexfish.evolver.chromosome.trait.DoubleTrait;

import java.util.List;

public class LerpExpressor implements Expressor<Double> {
    private final DoubleTrait traitStart;
    private final DoubleTrait traitEnd;

    public LerpExpressor(final DoubleTrait traitStart, final DoubleTrait traitEnd) {
        this.traitStart = traitStart;
        this.traitEnd = traitEnd;
    }

    @Override
    public void express(final Double[] dest, final SequentialListView<Gene> genes) {
        final double valueStart = (double)traitStart.getValue(traitStart.isGenetic() ? genes.read() : null);
        final double valueEnd   = (double)traitEnd  .getValue(traitEnd  .isGenetic() ? genes.read() : null);

        final int n = dest.length;
        final double maxIndex = n-1;
        for (int i = 0; i < n; i++) {
            dest[i] = lerp(valueStart, valueEnd, i/maxIndex);
        }
    }

    @Override
    public void code(final SequentialListView<Gene> dest, final Double[] source) {
        if (traitStart.isGenetic()) {
            dest.read().setAllele(source[0]);
        }
        if (traitEnd.isGenetic()) {
            dest.read().setAllele(source[source.length - 1]);
        }
    }

    @Override
    public void generateGenes(final List<Gene> genesOut, final Configuration conf) throws InvalidConfigurationException {
        if (traitStart.isGenetic()) {
            genesOut.add(traitStart.getSampleGene(conf));
        }
        if (traitEnd.isGenetic()) {
            genesOut.add(traitEnd.getSampleGene(conf));
        }
    }

    private static double lerp(final double minOut, final double maxOut, final double in01) {
        return minOut + in01 * (maxOut - minOut);
    }

    private static double lerp(final double minOut, final double maxOut, final int minIn, final int maxIn, final int in) {
        assert in >= minIn && in <= maxIn;
        return lerp(minOut, maxOut, (double)(in-minIn) / (double)(maxIn-minIn));
    }
}
