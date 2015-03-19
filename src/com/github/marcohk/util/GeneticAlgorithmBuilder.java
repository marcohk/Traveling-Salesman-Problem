package com.github.marcohk.util;

/**
 * Created by Marco on 14/3/2015.
 *
 * Builder for GeneticAlgorithm class
 *
 * Example:
 *
 * new GeneticAlgorithm<ArrayList<City>, ArrayList<City>>()
 * .with(...)
 * .generation(...)
 * .geneticize(...)
 * .degeneticize(...)
 * .fitness(...)
 * .selector(...)
 * .crossover()
 * .crossoverRate(...)
 * .mutate(...)
 * .mutateRate(...)
 * .seed(...)
 * .debug(...)
 * .build(...);
 */
final public class GeneticAlgorithmBuilder<E,G> {
    private Initializer<E> initializer;
    private Geneticizor<E,G> geneticizor;
    private Degeneticizor<E,G> degeneticizor;
    private Fitness<E> fitness;
    private Selector<E> selector;
    private Crossover<G> crossover;
    private Mutation<G> mutation;

    private int generation = 100;
    private float crossoverRate = 0.5f;
    private float mutateRate = 0.3f;
    private int seed = 0;
    private boolean debug = false;

    public GeneticAlgorithmBuilder<E,G> with(Initializer<E> initializer) {
        this.initializer = initializer;
        return this;
    }

    public GeneticAlgorithmBuilder<E,G> generation(int generation) {
        this.generation = generation;
        return this;
    }

    public GeneticAlgorithmBuilder<E,G> geneticize(Geneticizor<E,G> geneticizor) {
        this.geneticizor = geneticizor;
        return this;
    }

    public GeneticAlgorithmBuilder<E,G> degeneticize(Degeneticizor<E,G> degeneticizor) {
        this.degeneticizor = degeneticizor;
        return this;
    }

    public GeneticAlgorithmBuilder<E,G> fitness(Fitness<E> fitness) {
        this.fitness = fitness;
        return this;
    }

    public GeneticAlgorithmBuilder<E,G> selector(Selector<E> selector) {
        this.selector = selector;
        return this;
    }

    public GeneticAlgorithmBuilder<E,G> crossover(Crossover<G> crossover) {
        this.crossover = crossover;
        return this;
    }

    public GeneticAlgorithmBuilder<E,G> crossoverRate(float crossoverRate) {
        this.crossoverRate = crossoverRate;
        return this;
    }

    public GeneticAlgorithmBuilder<E,G> mutate(Mutation<G> mutation) {
        this.mutation = mutation;
        return this;
    }

    public GeneticAlgorithmBuilder<E,G> mutateRate(float mutateRate) {
        this.mutateRate = mutateRate;
        return this;
    }

    public GeneticAlgorithmBuilder<E,G> seed(int seed) {
        this.seed = seed;
        return this;
    }

    public GeneticAlgorithmBuilder<E,G> debug(boolean debug) {
        this.debug = debug;
        return this;
    }


    public GeneticAlgorithm<E,G> build() {
        if(initializer == null ||
            geneticizor == null ||
            degeneticizor == null ||
            fitness == null ||
            selector == null ||
            crossover == null ||
            mutation == null
        ) {
                throw new NullPointerException();
        }

        return new GeneticAlgorithm<E,G>(initializer,generation,geneticizor,degeneticizor,fitness,selector, crossover,crossoverRate, mutation,
                mutateRate, seed, debug);
    }



}
