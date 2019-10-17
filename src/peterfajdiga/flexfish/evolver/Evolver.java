package peterfajdiga.flexfish.evolver;

import org.jgap.*;
import peterfajdiga.flexfish.evolver.chromosome.ChromosomeHandler;
import peterfajdiga.flexfish.evolver.chromosome.variants.Swimmers;
import peterfajdiga.flexfish.evolver.io.GitInfo;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Evolver {

    private static final String DIR_OUT_BASE = "data";
    private static final String EVOLVER_REPO_PATH = ".";
    private static final String EVALUATOR_REPO_PATH = "../flexfish/";
    private static final String EVALUATOR_PATH = EVALUATOR_REPO_PATH + "run_64x.bat";

    private static final int ERR_CODE_ARGS = 1;
    private static final int ERR_CODE_IO   = 2;
    private static final int ERR_CODE_JGAP = 3;
    private static final int ERR_CODE_EVAL = 4;

    private static final int N_POPULATION = 50;
    private static final int N_GENERATIONS = 100000;

    public static void main(final String[] args) {

        // read arguments
        int firstGenerationNumber = 0;
        String existingPopulationDirectory = null;
        switch (args.length) {
            case 2: firstGenerationNumber = Integer.parseInt(args[1]);  // TODO: handle exception
            case 1: existingPopulationDirectory = args[0];
            case 0: break;
            default: {
                System.err.println("Too many arguments");
                System.exit(ERR_CODE_ARGS);
                return;
            }
        }

        // setup chromosome handler and directory names
        final ChromosomeHandler chromosomeHandler = Swimmers.Eel();
        final String dirOut = String.format("%s/%s %s",
                DIR_OUT_BASE,
                new Timestamp(System.currentTimeMillis()).toString().replaceAll(":", "."),
                chromosomeHandler.getName()
        );
        final String formatOut = String.format("%s/%s",
                dirOut,
                "gen%0" + (int)Math.ceil(Math.log10(N_GENERATIONS)) + "d.swimmer"
        );
        final String dirOutPopulation = dirOut + "/population";

        // setup directories
        try {
            Files.createDirectories(Paths.get(dirOut));
            Files.createDirectory(Paths.get(dirOutPopulation));
        } catch (final IOException e) {
            System.err.println("Could not create directories");
            e.printStackTrace();
            System.exit(ERR_CODE_IO);
            return;
        }

        // write git info
        try {
            saveGitInfo(EVOLVER_REPO_PATH, dirOut + "/evolver.gitinfo");
            saveGitInfo(EVALUATOR_REPO_PATH, dirOut + "/evaluator.gitinfo");
        } catch (final IOException e) {
            System.err.println("Could not write git info to file");
            e.printStackTrace();
            System.exit(ERR_CODE_IO);
            return;
        }

        /*// setup evaluator log file
        final FileOutputStream evaluatorLogger;
        try {
            evaluatorLogger = new FileOutputStream(dirOut + "/evaluator.log");
        } catch (final FileNotFoundException e) {
            System.err.println("Could not create evaluator log file");
            e.printStackTrace();
            System.exit(ERR_CODE_IO);
            return;
        }*/

        // setup JGAP
        final Genotype genotype;
        try {
            final FitnessFunction fitnessFunction = new ExternalFitnessFunction(EVALUATOR_PATH + " -fishstop -fishstdin", chromosomeHandler, null);
            final Configuration conf = new EvolverJGAPConfiguration(fitnessFunction);
            Configuration.reset();
            conf.setSampleChromosome(chromosomeHandler.getSampleChromosome(conf));
            if (existingPopulationDirectory == null) {
                conf.setPopulationSize(N_POPULATION);
                genotype = Genotype.randomInitialGenotype(conf);
            } else {
                try {
                    final List<IChromosome> chromosomeList = readPopulation(conf, existingPopulationDirectory, chromosomeHandler);
                    final IChromosome[] chromosomes = chromosomeList.toArray(new IChromosome[0]);
                    conf.setPopulationSize(chromosomes.length);
                    genotype = new Genotype(conf, new Population(conf, chromosomes));
                } catch (final FileNotFoundException e) {
                    System.err.println("Could not read population from " + existingPopulationDirectory);
                    e.printStackTrace();
                    System.exit(ERR_CODE_IO);
                    return;
                }
            }
        } catch (final InvalidConfigurationException e) {
            System.err.println("JGAP invalid configuration exception");
            e.printStackTrace();
            System.exit(ERR_CODE_JGAP);
            return;
        }

        // evolve
        try {
            for (int i = firstGenerationNumber; i < N_GENERATIONS; i++) {
                genotype.evolve();
                chromosomeHandler.saveChromosome(genotype.getFittestChromosome(), String.format(formatOut, i));
                //savePopulation(genotype.getPopulation().getChromosomes(), dirOutPopulation, chromosomeHandler);
            }
        } catch (final IOException e) {
            System.err.println("Can't write to directory " + dirOut);
            e.printStackTrace();
            System.exit(ERR_CODE_IO);
        }
    }

    private static List<IChromosome> readPopulation(final Configuration conf, final String directory, final ChromosomeHandler chromosomeHandler) throws InvalidConfigurationException, FileNotFoundException {
        final File dir = new File(directory);
        if (!dir.exists()) {
            throw new FileNotFoundException();
        }
        final List<IChromosome> chromosomes = new ArrayList<>();
        for (final File file : dir.listFiles()) {
            if (file.isFile() && file.toString().endsWith(".swimmer")) {
                chromosomes.add(chromosomeHandler.loadChromosome(conf, file));
            }
        }
        return chromosomes;
    }

    private static void savePopulation(final List<IChromosome> population, final String directory, final ChromosomeHandler chromosomeHandler) throws IOException {
        final int n = population.size();
        for (int i = 0; i < n; i++) {
            chromosomeHandler.saveChromosome(population.get(i), String.format("%s/%d.swimmer", directory, i));
        }
    }

    private static String stringToValidFilename(final String string) {
        return clipString(string, 16).replaceAll("[^A-Za-z0-9. ]", "");
    }

    private static String clipString(final String string, final int maxLength) {
        if (string.length() > maxLength) {
            return string.substring(0, maxLength-1) + "…";
        }
        return string;
    }

    private static void saveGitInfo(final String gitRepo, final String targetFilename) throws IOException {
        final GitInfo gitInfo = new GitInfo(gitRepo);
        final FileOutputStream fileOutputStream = new FileOutputStream(targetFilename);
        final PrintStream fileWriter = new PrintStream(fileOutputStream);
        fileWriter.println(gitInfo.getHeadHash());
        fileWriter.println(gitInfo.getHeadMessage());
        gitInfo.streamDiff().transferTo(fileOutputStream);
        fileWriter.close();
    }
}
