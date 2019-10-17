package peterfajdiga.flexfish.evolver.chromosome.expressor;

import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.InvalidConfigurationException;
import peterfajdiga.flexfish.evolver.chromosome.trait.Trait;

import java.util.List;

// n traits, 0 to n genes, n fields
public class ManyManyExpressor<T> implements Expressor<T> {
    private final Trait[] traits;

    public ManyManyExpressor(final Trait[] traits) {
        this.traits = traits;
    }

    @Override
    public void express(final T[] dest, final SequentialListView<Gene> genes) {
        assert dest.length == traits.length;
        for (int i = 0; i < dest.length; i++) {
            final Trait trait = traits[i];
            final Gene gene = trait.isGenetic() ? genes.read() : null;
            dest[i] = (T)trait.getValue(gene);
        }
    }

    @Override
    public void code(final SequentialListView<Gene> dest, final T[] source) {
        assert source.length == traits.length;
        for (int i = 0; i < traits.length; i++) {
            final Trait trait = traits[i];
            if (trait.isGenetic()) {
                dest.read().setAllele(source[i]);
            }
        }
    }

    @Override
    public void generateGenes(final List<Gene> genesOut, final Configuration conf) throws InvalidConfigurationException {
        for (final Trait trait : traits) {
            if (trait.isGenetic()) {
                genesOut.add(trait.getSampleGene(conf));
            }
        }
    }
}
