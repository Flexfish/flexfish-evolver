package peterfajdiga.flexfish.evolver.chromosome.expressor;

import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.InvalidConfigurationException;
import peterfajdiga.flexfish.evolver.chromosome.trait.Trait;

import java.util.List;

// 2 traits, 0 to 2 genes, n fields
public class TwoTwoExpressor<T> implements Expressor<T> {
    private final Trait traitTrue;
    private final Trait traitFalse;
    private final boolean[] filter;
    private final boolean anyGenes;

    public TwoTwoExpressor(final Trait traitTrue, final Trait traitFalse, final boolean[] filter) {
        this.traitTrue = traitTrue;
        this.traitFalse = traitFalse;
        this.filter = filter;
        this.anyGenes = traitTrue.isGenetic() || traitFalse.isGenetic();
    }

    @Override
    public void express(final T[] dest, final SequentialListView<Gene> genes) {
        assert dest.length == filter.length;
        final T valueTrue = (T)traitTrue.getValue(traitTrue.isGenetic() ? genes.read() : null);
        final T valueFalse = (T)traitFalse.getValue(traitFalse.isGenetic() ? genes.read() : null);

        for (int i = 0; i < filter.length; i++) {
            dest[i] = filter[i] ? valueTrue : valueFalse;
        }
    }

    @Override
    public void code(final SequentialListView<Gene> dest, final T[] source) {
        assert source.length == filter.length;
        if (!anyGenes) {
            return;
        }

        T valueTrue = null;
        T valueFalse = null;
        for (int i = 0; i < filter.length; i++) {
            if (filter[i]) {
                valueTrue = source[i];
            } else {
                valueFalse = source[i];
            }
        }
        assert valueTrue != null && valueFalse != null;

        if (traitTrue.isGenetic()) {
            dest.read().setAllele(valueTrue);
        }
        if (traitFalse.isGenetic()) {
            dest.read().setAllele(valueFalse);
        }
    }

    @Override
    public void generateGenes(final List<Gene> genesOut, final Configuration conf) throws InvalidConfigurationException {
        if (traitTrue.isGenetic()) {
            genesOut.add(traitTrue.getSampleGene(conf));
        }
        if (traitFalse.isGenetic()) {
            genesOut.add(traitFalse.getSampleGene(conf));
        }
    }
}
