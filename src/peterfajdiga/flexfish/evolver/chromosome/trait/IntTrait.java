package peterfajdiga.flexfish.evolver.chromosome.trait;

import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.IntegerGene;

public class IntTrait implements Trait {

    private final int min, max;

    public IntTrait(final int min, final int max) {
        this.min = min;
        this.max = max;
    }

    public IntTrait(final int constantValue) {
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
        return new IntegerGene(conf, min, max);
    }

    @Override
    public Object getValue(final Gene gene) {
        if (isGenetic()) {
            assert gene instanceof IntegerGene;
            return gene.getAllele();
        } else {
            assert gene == null;
            assert min == max;
            return min;
        }
    }
}
