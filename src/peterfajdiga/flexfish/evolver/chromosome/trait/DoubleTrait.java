package peterfajdiga.flexfish.evolver.chromosome.trait;

import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.DoubleGene;

public class DoubleTrait implements Trait {

    private final double min, max;

    public DoubleTrait(final double min, final double max) {
        assert min <= max;
        this.min = min;
        this.max = max;
    }

    public DoubleTrait(final double constantValue) {
        this.min = constantValue;
        this.max = constantValue;
    }

    @Override
    public boolean isGenetic() {
        return min != max;
    }

    @Override
    public Gene getSampleGene(final Configuration conf) throws InvalidConfigurationException {
        assert isGenetic();
        return new DoubleGene(conf, min, max);
    }

    @Override
    public Object getValue(final Gene gene) {
        if (isGenetic()) {
            assert gene instanceof DoubleGene;
            return gene.getAllele();
        } else {
            assert gene == null;
            assert min == max;
            return min;
        }
    }
}
