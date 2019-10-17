package peterfajdiga.flexfish.evolver.chromosome.expressor;

import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.InvalidConfigurationException;
import peterfajdiga.flexfish.evolver.chromosome.trait.Trait;

import java.util.List;

// 2 traits, 0 to n genes, n fields
public class TwoManyExpressor<T> implements Expressor<T> {
    private final Trait traitTrue;
    private final Trait traitFalse;
    private final boolean[] filter;
    private final boolean anyGenes;

    public TwoManyExpressor(final Trait traitTrue, final Trait traitFalse, final boolean[] filter) {
        this.traitTrue = traitTrue;
        this.traitFalse = traitFalse;
        this.filter = filter;
        this.anyGenes = traitTrue.isGenetic() || traitFalse.isGenetic();
    }

    @Override
    public void express(final T[] dest, final SequentialListView<Gene> genes) {
        assert dest.length == filter.length;
        for (int i = 0; i < dest.length; i++) {
            final Trait trait = filter[i] ? traitTrue : traitFalse;
            final Gene gene = trait.isGenetic() ? genes.read() : null;
            dest[i] = (T)trait.getValue(gene);
        }
    }

    @Override
    public void code(final SequentialListView<Gene> dest, final T[] source) {
        assert source.length == filter.length;
        if (!anyGenes) {
            return;
        }

        for (int i = 0; i < filter.length; i++) {
            final Trait trait = filter[i] ? traitTrue : traitFalse;
            if (trait.isGenetic()) {
                dest.read().setAllele(source[i]);
            }
        }
    }

    @Override
    public void generateGenes(final List<Gene> genesOut, final Configuration conf) throws InvalidConfigurationException {
        if (!anyGenes) {
            return;
        }

        for (final boolean selector : filter) {
            final Trait trait = selector ? traitTrue : traitFalse;
            if (trait.isGenetic()) {
                genesOut.add(trait.getSampleGene(conf));
            }
        }
    }
}
