package peterfajdiga.flexfish.evolver.chromosome.trait;

import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.InvalidConfigurationException;

public interface Trait {
    boolean isGenetic();
    Gene getSampleGene(Configuration conf) throws InvalidConfigurationException;
    Object getValue(Gene gene);
}
