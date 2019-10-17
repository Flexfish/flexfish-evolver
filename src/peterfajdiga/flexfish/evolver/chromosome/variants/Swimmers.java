package peterfajdiga.flexfish.evolver.chromosome.variants;

import peterfajdiga.flexfish.evolver.chromosome.BoolArrays;
import peterfajdiga.flexfish.evolver.chromosome.ChromosomeHandler;
import peterfajdiga.flexfish.evolver.chromosome.ShapeFormat;
import peterfajdiga.flexfish.evolver.chromosome.SwimmerBuilder;

public class Swimmers {
    private Swimmers() {}

    public static ChromosomeHandler Full() {
        final int length = 18;
        final SwimmerBuilder builder = new SwimmerBuilder(
                Shapes.geneticShape(length),
                Gaits.geneticFullGait(false, length)
        );
        return new ChromosomeHandler(builder);
    }

    public static ChromosomeHandler Tail() {
        final int length = 18;
        final int tailLength = 10;
        final SwimmerBuilder builder = new SwimmerBuilder(
                Shapes.geneticShape(length),
                Gaits.geneticTailGait(false, length, tailLength)
        );
        return new ChromosomeHandler(builder);
    }

    public static ChromosomeHandler Tape() {
        final int length = 19;
        final SwimmerBuilder builder = new SwimmerBuilder(
                Shapes.boxShape(length, 5, 2),
                Gaits.sparseGait(false, BoolArrays.setEveryNthToFalse(BoolArrays.ones(length), 2))
        );
        return new ChromosomeHandler(builder);
    }

    public static ChromosomeHandler Fish() {
        final ShapeFormat fishShape = Shapes.fishShape();
        final SwimmerBuilder builder = new SwimmerBuilder(
                fishShape,
                Gaits.geneticTailGait(false, fishShape.length, fishShape.length*3/4)
        );
        return new ChromosomeHandler(builder);
    }

    public static ChromosomeHandler Tu() {
        final SwimmerBuilder builder = new SwimmerBuilder(
                Shapes.tuShape(),
                Gaits.geneticGait(false, Gaits.tuHinges, "tan"),
                "tan"
        );
        return new ChromosomeHandler(builder);
    }

    public static ChromosomeHandler Simple() {
        final int length = 17;
        final SwimmerBuilder builder = new SwimmerBuilder(
                Shapes.boxShape(length, 5, 3),
                Gaits.geneticGait(
                        false,
                        BoolArrays.makeClustersSize(BoolArrays.ones(length), 5),
                        "simple"
                ),
                "simple"
        );
        return new ChromosomeHandler(builder);
    }

    public static ChromosomeHandler Eel() {
        final int length = 33;
        final SwimmerBuilder builder = new SwimmerBuilder(
                Shapes.boxShape(length, 5, 2),
                Gaits.sparseGait(false, BoolArrays.setEveryNthToFalse(BoolArrays.ones(length), 2)),
                "eel-sparse"
        );
        return new ChromosomeHandler(builder);
    }
}
