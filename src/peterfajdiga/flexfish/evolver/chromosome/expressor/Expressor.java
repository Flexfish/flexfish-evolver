package peterfajdiga.flexfish.evolver.chromosome.expressor;

import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.InvalidConfigurationException;

import java.util.List;

public interface Expressor<T> {
    /**
     * Expresses the effects of genes according to traits. This is the opposite of code().
     * @param dest array to fill
     * @param genes genes to read
     */
    void express(T[] dest, SequentialListView<Gene> genes);

    /**
     * Codes the swimmer into genes. This is the opposite of express().
     * @param dest genes to fill
     * @param source to read
     */
    void code(SequentialListView<Gene> dest, T[] source);

    /**
     * Generates sample genes according to traits.
     * @param genesOut generated genes are added to this list
     * @param conf     needed for gene creation
     * @throws InvalidConfigurationException gene creation may throw this
     */
    void generateGenes(List<Gene> genesOut, Configuration conf) throws InvalidConfigurationException;
}
