package peterfajdiga.flexfish.evolver.chromosome;

import org.jgap.*;
import peterfajdiga.flexfish.evolver.chromosome.expressor.SequentialListView;

import java.util.ArrayList;
import java.util.List;

public class SwimmerBuilder {
    private final ShapeFormat shape;
    private final GaitFormat gait;
    final String name;

    public SwimmerBuilder(final ShapeFormat shapeFormat, final GaitFormat gaitFormat, final String name) {
        this.shape = shapeFormat;
        this.gait = gaitFormat;
        this.name = name;
    }

    public SwimmerBuilder(final ShapeFormat shapeFormat, final GaitFormat gaitFormat) {
        this(shapeFormat, gaitFormat, shapeFormat.name + "-" + gaitFormat.name);
    }

    public Swimmer build(final Gene[] genes) {
        final Swimmer swimmer = new Swimmer(shape.length);
        SequentialListView<Gene> geneList = new SequentialListView<>(genes);
        shape.heights   .express(swimmer.heights    , geneList);
        shape.widths    .express(swimmer.widths     , geneList);
        gait.hinges     .express(swimmer.hinges     , geneList);
        gait.frequencies.express(swimmer.frequencies, geneList);
        gait.powers     .express(swimmer.powers     , geneList);
        gait.amplitudes .express(swimmer.amplitudes , geneList);
        gait.phases     .express(swimmer.phases     , geneList);
        assert !geneList.hasNext();
        assert swimmer.isValid();
        return swimmer;
    }

    public IChromosome getSampleChromosome(final Configuration conf) throws InvalidConfigurationException {
        final List<Gene> genes = new ArrayList<>();
        shape.heights.generateGenes(genes, conf);
        shape.widths.generateGenes(genes, conf);
        gait.hinges.generateGenes(genes, conf);
        gait.frequencies.generateGenes(genes, conf);
        gait.powers.generateGenes(genes, conf);
        gait.amplitudes.generateGenes(genes, conf);
        gait.phases.generateGenes(genes, conf);
        return new Chromosome(conf, genes.toArray(new Gene[0]));
    }

    void code(final Gene[] genes, final Swimmer swimmer) {
        assert swimmer.isValid();
        SequentialListView<Gene> geneList = new SequentialListView<>(genes);
        shape.heights   .code(geneList, swimmer.heights    );
        shape.widths    .code(geneList, swimmer.widths     );
        gait.hinges     .code(geneList, swimmer.hinges     );
        gait.frequencies.code(geneList, swimmer.frequencies);
        gait.powers     .code(geneList, swimmer.powers     );
        gait.amplitudes .code(geneList, swimmer.amplitudes );
        gait.phases     .code(geneList, swimmer.phases     );
        assert !geneList.hasNext();
    }
}
