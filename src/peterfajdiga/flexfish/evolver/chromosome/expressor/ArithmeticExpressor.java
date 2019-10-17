package peterfajdiga.flexfish.evolver.chromosome.expressor;

import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.InvalidConfigurationException;
import peterfajdiga.flexfish.evolver.chromosome.trait.DoubleTrait;

import java.util.List;

// 1 trait, 0 or 1 gene, n fields
public class ArithmeticExpressor implements Expressor<Double> {
    private final DoubleTrait trait;
    private final int n;

    public ArithmeticExpressor(final DoubleTrait trait, final int n) {
        this.trait = trait;
        this.n = n;
    }

    @Override
    public void express(final Double[] dest, final SequentialListView<Gene> genes) {
        assert dest.length == n;
        final Gene gene = trait.isGenetic() ? genes.read() : null;
        final double step = (double)trait.getValue(gene);
        assert step >= 0.0 && step <= 1.0;

        double currentValue = 0.0;
        // loop in reverse so that phase 0.0 starts at the front of the fish
        for (int i = n-1; i >= 0; i--) {
            assert currentValue >= 0.0 && currentValue < 1.0;
            dest[i] = currentValue;
            currentValue += step;
            if (currentValue >= 1.0) {
                currentValue -= 1.0;
            }
        }
    }

    @Override
    public void code(final SequentialListView<Gene> dest, final Double[] source) {
        assert source.length == n;
        if (trait.isGenetic()) {
            final double step = getAverageStep(source);
            dest.read().setAllele(step);
        }
    }

    @Override
    public void generateGenes(final List<Gene> genesOut, final Configuration conf) throws InvalidConfigurationException {
        if (trait.isGenetic()) {
            genesOut.add(trait.getSampleGene(conf));
        }
    }

    private static double getAverageStep(final Double[] values) {
        double averageStep = 0.0;
        for (int i = values.length-2; i >= 0; i--) {
            double step = values[i] - values[i+1];
            if (step < 0.0) {
                step += 1.0;
            }
            assert step >= 0.0 && step < 1.0;
            averageStep += step;
        }
        averageStep /= values.length - 1;
        return averageStep;
    }
}
