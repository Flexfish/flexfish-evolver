package peterfajdiga.flexfish.evolver.chromosome;

import org.jgap.*;

import java.io.*;
import java.util.Locale;
import java.util.Scanner;

public class ChromosomeHandler {

    private final SwimmerBuilder swimmerBuilder;

    public ChromosomeHandler(final SwimmerBuilder swimmerBuilder) {
        this.swimmerBuilder = swimmerBuilder;
    }

    public IChromosome getSampleChromosome(final Configuration conf) throws InvalidConfigurationException {
        return swimmerBuilder.getSampleChromosome(conf);
    }

    public String getName() {
        return swimmerBuilder.name;
    }

    public void loadChromosome(final IChromosome chromosome, final File file) throws FileNotFoundException, InvalidConfigurationException {
        final Scanner reader = new Scanner(file);
        reader.useLocale(Locale.US);
        final Swimmer swimmer = new Swimmer(reader);

        final Gene[] genes = chromosome.getGenes();
        swimmerBuilder.code(genes, swimmer);

        chromosome.setGenes(genes);
        reader.close();
    }

    public void writeChromosome(final IChromosome chromosome, final PrintStream out) {
        final Gene[] genes = chromosome.getGenes();
        swimmerBuilder.build(genes).print(out);
        out.flush();
    }

    public IChromosome loadChromosome(final Configuration conf, final File file) throws InvalidConfigurationException, FileNotFoundException {
        final IChromosome chromosome = getSampleChromosome(conf);
        loadChromosome(chromosome, file);
        return chromosome;
    }

    public void saveChromosome(final IChromosome chromosome, final String filename) throws IOException {
        final FileOutputStream fileOutputStream = new FileOutputStream(filename);
        final PrintStream out = new PrintStream(fileOutputStream);
        writeChromosome(chromosome, out);
        out.close();
    }

    public boolean preskip(final IChromosome chromosome) {
        final Gene[] genes = chromosome.getGenes();
        final Swimmer swimmer = swimmerBuilder.build(genes);
        return WrapperArrays.sum(swimmer.hinges) < 2 || WrapperArrays.max(swimmer.amplitudes) < 0.05;
    }
}
