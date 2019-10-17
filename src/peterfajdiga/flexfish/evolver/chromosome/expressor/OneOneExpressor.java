package peterfajdiga.flexfish.evolver.chromosome.expressor;

import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.InvalidConfigurationException;
import peterfajdiga.flexfish.evolver.chromosome.trait.Trait;

import java.util.Arrays;
import java.util.List;

// 1 trait, 0 or 1 gene, n fields
public class OneOneExpressor<T> implements Expressor<T> {
    private final Trait trait;

    public OneOneExpressor(final Trait trait) {
        this.trait = trait;
    }

    @Override
    public void express(final T[] dest, final SequentialListView<Gene> genes) {
        final Gene gene = trait.isGenetic() ? genes.read() : null;
        final T value = (T)trait.getValue(gene);
        Arrays.fill(dest, value);
    }

    @Override
    public void code(final SequentialListView<Gene> dest, final T[] source) {
        if (trait.isGenetic()) {
            dest.read().setAllele(source[0]); // all values in source should be the same // TODO: assert
        }
    }

    @Override
    public void generateGenes(final List<Gene> genesOut, final Configuration conf) throws InvalidConfigurationException {
        if (trait.isGenetic()) {
            genesOut.add(trait.getSampleGene(conf));
        }
    }
}
