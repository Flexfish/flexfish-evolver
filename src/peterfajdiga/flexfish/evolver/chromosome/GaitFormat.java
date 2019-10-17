package peterfajdiga.flexfish.evolver.chromosome;

import peterfajdiga.flexfish.evolver.chromosome.expressor.Expressor;

public class GaitFormat {
    final Expressor<Boolean> hinges;
    final Expressor<Double> frequencies;
    final Expressor<Double> powers;
    final Expressor<Double> amplitudes;
    final Expressor<Double> phases;
    final String name;

    public GaitFormat(
            final Expressor<Boolean> hinges,
            final Expressor<Double> frequencies,
            final Expressor<Double> powers,
            final Expressor<Double> amplitudes,
            final Expressor<Double> phases,
            final String name
    ) {
        this.hinges = hinges;
        this.frequencies = frequencies;
        this.powers = powers;
        this.amplitudes = amplitudes;
        this.phases = phases;
        this.name = name;
    }
}
