package peterfajdiga.flexfish.evolver;

import peterfajdiga.flexfish.evolver.chromosome.ChromosomeHandler;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.IChromosome;
import org.jgap.impl.DoubleGene;
import peterfajdiga.flexfish.evolver.io.StreamGobbler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.Timestamp;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ExternalFitnessFunction extends FitnessFunction {

    private static final double EVALUATE_OFFSET = 100.0;  // ensures a positive fitness value
    private static final int N_RETRIES = 5;

    private Process evaluator = null;
    private PrintStream evaluatorStdin = null;
    private Scanner evaluatorStdout = null;

    private final String evaluatorCmd;
    private final ChromosomeHandler chromosomeHandler;
    private final OutputStream evaluatorErrTarget;

    public ExternalFitnessFunction(final String evaluatorCmd, final ChromosomeHandler chromosomeHandler) {
        this(evaluatorCmd, chromosomeHandler, null);
    }

    public ExternalFitnessFunction(final String evaluatorCmd, final ChromosomeHandler chromosomeHandler, final OutputStream evaluatorErrTarget) {
        this.evaluatorCmd = evaluatorCmd;
        this.chromosomeHandler = chromosomeHandler;
        this.evaluatorErrTarget = evaluatorErrTarget;
    }

    @Override
    public double evaluate(final IChromosome subject) {
        return 0.0;
    }

    private void startEvaluator() {
        try {
            evaluator = Runtime.getRuntime().exec(evaluatorCmd);
        } catch (final IOException e) {
            throw new RuntimeException("Error running evaluator");
        }
        evaluatorStdin = new PrintStream(evaluator.getOutputStream());
        evaluatorStdout = new Scanner(evaluator.getInputStream());
        evaluatorStdout.useLocale(Locale.US);
        if (evaluatorErrTarget != null) {
            new StreamGobbler(evaluator.getErrorStream(), evaluatorErrTarget);
        }
    }

    private void cleanup() {
        if (evaluatorStdin != null) {
            evaluatorStdin.close();
        }
        if (evaluatorStdout != null) {
            evaluatorStdout.close();
        }
    }

    private static void log(final String message) {
        System.err.println(new Timestamp(System.currentTimeMillis()).toString() + "\n\t" + message);
    }

    private static boolean preskip(final IChromosome chromosome) {
        final Gene[] genes = chromosome.getGenes();
        double maxAmplitude = 0.0;
        for (int i = 1; i < genes.length; i+=2) {
            final double amplitude = ((DoubleGene)genes[i]).doubleValue();
            final double phase = ((DoubleGene)genes[i+1]).doubleValue();

            if (amplitude > maxAmplitude) {
                maxAmplitude = amplitude;
            }
        }
        return maxAmplitude < 0.2;
    }
}
