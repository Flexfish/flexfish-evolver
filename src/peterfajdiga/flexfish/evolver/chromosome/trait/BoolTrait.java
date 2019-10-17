package peterfajdiga.flexfish.evolver.chromosome.trait;

import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.BooleanGene;

public class BoolTrait implements Trait {

    private final boolean constantValue;
    private final boolean genetic;

    public BoolTrait() {
        this.constantValue = false; // the value doesn't matter
        this.genetic = true;
    }

    public BoolTrait(final boolean constantValue) {
        this.constantValue = constantValue;
        this.genetic = false;
    }

    @Override
    public boolean isGenetic() {
        return genetic;
    }

    @Override
    public Gene getSampleGene(final Configuration conf) throws InvalidConfigurationException {
        assert isGenetic();
        return new BooleanGene(conf);
    }

    @Override
    public Object getValue(final Gene gene) {
        if (isGenetic()) {
            assert gene instanceof BooleanGene;
            return gene.getAllele();
        } else {
            assert gene == null;
            return constantValue;
        }
    }
}
