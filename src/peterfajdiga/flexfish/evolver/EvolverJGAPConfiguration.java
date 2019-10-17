package peterfajdiga.flexfish.evolver;

import peterfajdiga.flexfish.evolver.chromosome.ChromosomeHandler;
import org.jgap.*;
import org.jgap.event.EventManager;
import org.jgap.impl.*;

public class EvolverJGAPConfiguration extends Configuration {

    private ChromosomeHandler chromosomeHandler;

    public EvolverJGAPConfiguration(final FitnessFunction fitnessFunction) {
        super("", "evolver configuration");
        try {
            setBreeder(new GABreeder());
            setRandomGenerator(new StockRandomGenerator());
            setEventManager(new EventManager());

            final BestChromosomesSelector bestChromosomesSelector = new BestChromosomesSelector(this, 0.2d);
            bestChromosomesSelector.setDoubletteChromosomesAllowed(false);
            addNaturalSelector(bestChromosomesSelector, true);

            final WeightedRouletteSelector weightedRouletteSelector = new WeightedRouletteSelector(this);
            addNaturalSelector(weightedRouletteSelector, false);

            setKeepPopulationSizeConstant(false);
            setFitnessEvaluator(new DefaultFitnessEvaluator());
            setChromosomePool(new ChromosomePool());
            addGeneticOperator(new CrossoverOperator(this, 0.35d));
            addGeneticOperator(new MutationOperator(this, 25));
            addGeneticOperator(new GaussianMutationOperator(this));

            setPreservFittestIndividual(false);
            setFitnessFunction(fitnessFunction);
        } catch (InvalidConfigurationException e) {
            System.err.println("JGAP invalid configuration exception");
        }
    }
}
