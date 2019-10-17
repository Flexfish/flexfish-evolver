package peterfajdiga.flexfish.evolver;

import peterfajdiga.flexfish.evolver.chromosome.ChromosomeHandler;
import org.jgap.*;
import org.jgap.impl.DoubleGene;
import peterfajdiga.flexfish.evolver.chromosome.variants.Swimmers;

import java.io.File;
import java.io.IOException;

public class TestChromosome {
    private static final String TEST_CHROMOSOME_PATH = "data/test.swimmer";

    public static void main(final String[] args) {
        System.out.println(test(Swimmers.Full()));
        System.out.println(test(Swimmers.Tail()));
        System.out.println(test(Swimmers.Tape()));
        System.out.println(test(Swimmers.Fish()));
        System.out.println(test(Swimmers.Tu()));
        System.out.println(test(Swimmers.Simple()));
    }

    private static boolean test(final ChromosomeHandler chromosomeHandler) {
        try {
            Configuration.reset();
            final Configuration conf = new EvolverJGAPConfiguration(new MockFitnessFunction());
            final IChromosome randomChromosome = initializeRandomChromosome(conf, chromosomeHandler);
            chromosomeHandler.saveChromosome(randomChromosome, TEST_CHROMOSOME_PATH);
            final IChromosome readChromosome = chromosomeHandler.loadChromosome(conf, new File(TEST_CHROMOSOME_PATH));
            return equalChromosomes(randomChromosome, readChromosome);
        } catch (final InvalidConfigurationException exception) {
            System.err.println("Invalid JGAP configuration");
            exception.printStackTrace();
            System.exit(1);
            return false;
        } catch (final IOException exception) {
            System.err.println("Error saving or loading chromosome");
            exception.printStackTrace();
            System.exit(1);
            return false;
        }
    }

    private static IChromosome initializeRandomChromosome(final Configuration conf, final ChromosomeHandler chromosomeHandler) throws InvalidConfigurationException {
        conf.setSampleChromosome(chromosomeHandler.getSampleChromosome(conf));
        conf.setPopulationSize(1);
        return Chromosome.randomInitialChromosome(conf);
    }

    private static boolean equalChromosomes(final IChromosome a, final IChromosome b) {
        final Gene[] genesA = a.getGenes();
        final Gene[] genesB = b.getGenes();
        if (genesA.length != genesB.length) {
            return false;
        }
        final int n = genesA.length;
        for (int i = 0; i < n; i++) {
            final Gene geneA = genesA[i];
            final Gene geneB = genesB[i];
            /*if (!geneA.getClass().equals(geneB.getClass())) {
                return false;
            }*/
            if (geneA instanceof DoubleGene) {
                final double valueA = ((DoubleGene) geneA).doubleValue();
                final double valueB = ((DoubleGene) geneB).doubleValue();
                if (!almostEqual(valueA, valueB)) {
                    return false;
                }
            } else if (!geneA.equals(geneB)) {
                return false;
            }
        }
        return true;
    }

    private static boolean almostEqual(final double a, final double b) {
        return Math.abs(a-b) < 1e-5;
    }

    private static final class MockFitnessFunction extends FitnessFunction {

        @Override
        protected double evaluate(IChromosome iChromosome) {
            return 0;
        }
    }
}
