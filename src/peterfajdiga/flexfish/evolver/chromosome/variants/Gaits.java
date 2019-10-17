package peterfajdiga.flexfish.evolver.chromosome.variants;

import peterfajdiga.flexfish.evolver.chromosome.BoolArrays;
import peterfajdiga.flexfish.evolver.chromosome.GaitFormat;
import peterfajdiga.flexfish.evolver.chromosome.expressor.*;
import peterfajdiga.flexfish.evolver.chromosome.trait.BoolTrait;
import peterfajdiga.flexfish.evolver.chromosome.trait.DoubleTrait;
import peterfajdiga.flexfish.evolver.chromosome.trait.Trait;

import java.util.Arrays;

class Gaits {
    private Gaits() {}

    static final boolean[] tuHinges = new boolean[]{false, false, false, false, false, true, false, false, false, false, true, false, false, false, true, false, false, false, false, false, true, false, false, false};

    static GaitFormat geneticFullGait(final boolean geneticHinges, final int length) {
        final Trait hingeTrait = geneticHinges ? new BoolTrait() : new BoolTrait(true);
        final int motorCount = length - 1;
        return new GaitFormat(
                new OneManyExpressor<>(hingeTrait, length),
                new OneOneExpressor<>(new DoubleTrait(0.5, 2.0)),
                new OneOneExpressor<>(new DoubleTrait(1.0)),
                new OneManyExpressor<>(new DoubleTrait(0.0, 1.0), motorCount),
                new TwoManyExpressor<>(new DoubleTrait(0.0, 1.0), new DoubleTrait(0.0), BoolArrays.setFirstTrueToFalse(BoolArrays.ones(motorCount))),
                "full"
        );
    }

    static GaitFormat geneticGait(final boolean geneticHinges, final boolean[] hinges, final String name) {
        final Trait hingeTrait = geneticHinges ? new BoolTrait() : new BoolTrait(true);
        final boolean[] motors = BoolArrays.setLastTrueToFalse(Arrays.copyOf(hinges, hinges.length-1));
        return new GaitFormat(
                oneOrTwoManyExpressor(hingeTrait, new BoolTrait(false), hinges),
                new OneOneExpressor<>(new DoubleTrait(0.5, 2.0)),
                new OneOneExpressor<>(new DoubleTrait(1.0)),
                oneOrTwoManyExpressor(new DoubleTrait(0.0, 1.0), new DoubleTrait(0.0), motors),
                oneOrTwoManyExpressor(new DoubleTrait(0.0, 1.0), new DoubleTrait(0.0), BoolArrays.setFirstTrueToFalse(motors)),
                name
        );
    }

    static GaitFormat geneticTailGait(final boolean geneticHinges, final int length, final int tailLength) {
        assert tailLength <= length;
        return geneticGait(geneticHinges, BoolArrays.onesZeros(length, tailLength), "tail");
    }

    static GaitFormat threeGeneGait(final boolean geneticHinges, final boolean[] hinges) {
        final Trait hingeTrait = geneticHinges ? new BoolTrait() : new BoolTrait(true);
        final boolean[] motors = BoolArrays.setLastTrueToFalse(Arrays.copyOf(hinges, hinges.length-1));
        return new GaitFormat(
                oneOrTwoExpressor(hingeTrait, new BoolTrait(false), hinges),
                new OneOneExpressor<>(new DoubleTrait(0.5, 2.0)),
                new OneOneExpressor<>(new DoubleTrait(1.0)),
                new OneOneExpressor<>(new DoubleTrait(1.0, 1.0)),
                new ArithmeticExpressor(new DoubleTrait(0.0, 1.0), motors.length),
                "3gen"
        );
    }

    static GaitFormat sparseGait(final boolean geneticHinges, final boolean[] hinges) {
        final Trait hingeTrait = geneticHinges ? new BoolTrait() : new BoolTrait(true);
        final boolean[] motors = BoolArrays.setLastTrueToFalse(Arrays.copyOf(hinges, hinges.length-1));
        return new GaitFormat(
                oneOrTwoExpressor(hingeTrait, new BoolTrait(false), hinges),
                new OneOneExpressor<>(new DoubleTrait(0.5, 2.0)),
                new OneOneExpressor<>(new DoubleTrait(1.0)),
                new LerpExpressor(new DoubleTrait(0.0, 1.0), new DoubleTrait(0.0, 1.0)),
                new ArithmeticExpressor(new DoubleTrait(0.0, 1.0), motors.length),
                "sparse"
        );
    }

    private static <T> Expressor<T> oneOrTwoExpressor(final Trait traitTrue, final Trait traitFalse, final boolean[] filter) {
        if (BoolArrays.sum(filter) == filter.length) {
            return new OneOneExpressor<>(traitTrue);
        } else {
            return new TwoTwoExpressor<>(traitTrue, traitFalse, filter);
        }
    }

    private static <T> Expressor<T> oneOrTwoManyExpressor(final Trait traitTrue, final Trait traitFalse, final boolean[] filter) {
        if (BoolArrays.sum(filter) == filter.length) {
            return new OneManyExpressor<>(traitTrue, filter.length);
        } else {
            return new TwoManyExpressor<>(traitTrue, traitFalse, filter);
        }
    }
}
