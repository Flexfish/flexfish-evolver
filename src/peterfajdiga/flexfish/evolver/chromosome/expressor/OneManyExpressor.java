package peterfajdiga.flexfish.evolver.chromosome.expressor;

import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.InvalidConfigurationException;
import peterfajdiga.flexfish.evolver.chromosome.trait.Trait;

import java.util.Arrays;
import java.util.List;

// 1 trait, 0 or n genes, n fields
public class OneManyExpressor<T> implements Expressor<T> {
    private final Trait trait;
    private final int n;

    public OneManyExpressor(final Trait trait, final int n) {
        this.trait = trait;
        this.n = n;
    }

    @Override
    public void express(final T[] dest, final SequentialListView<Gene> genes) {
        assert dest.length == n;
        if (!trait.isGenetic()) {
            final T value = (T)trait.getValue(null);
            Arrays.fill(dest, value);
            return;
        }

        for (int i = 0; i < n; i++) {
            dest[i] = (T)trait.getValue(genes.read());
        }
    }

    @Override
    public void code(final SequentialListView<Gene> dest, final T[] source) {
        assert source.length == n;
        if (!trait.isGenetic()) {
            return;
        }

        for (int i = 0; i < n; i++) {
            dest.read().setAllele(source[i]);
        }
    }

    @Override
    public void generateGenes(final List<Gene> genesOut, final Configuration conf) throws InvalidConfigurationException {
        if (!trait.isGenetic()) {
            return;
        }
        for (int i = 0; i < n; i++) {
            genesOut.add(trait.getSampleGene(conf));
        }
    }
}
