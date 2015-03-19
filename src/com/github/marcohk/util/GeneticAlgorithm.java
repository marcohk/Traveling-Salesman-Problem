package com.github.marcohk.util;

import java.util.*;

/**
 * Created by Marco on 14/3/2015.
 *
 * GeneticAlgorithm is a library class for running genetic algorithm in Java 8.
 * This should be instantiated by GeneticAlgorithmBuilder.
 */
final public class GeneticAlgorithm<E,G> {
    private Initializer<E> initializer;
    private Geneticizor<E,G> geneticizor;
    private Degeneticizor<E,G> degeneticizor;
    private Fitness<E> fitness;
    private Selector<E> selector;
    private Crossover<G> crossover;
    private Mutation<G> mutation;
    private Random random;

    private int generation = 100;
    private float crossoverRate = 0.5f;
    private float mutateRate = 0.3f;
    private int seed = 0;
    private boolean debug = false;

    protected GeneticAlgorithm(Initializer<E> initializer,
                            int generation,
                            Geneticizor<E, G> geneticizor,
                            Degeneticizor<E, G> degeneticizor,
                            Fitness<E> fitness,
                            Selector<E> selector,
                            Crossover<G> crossover,
                            float crossoverRate,
                            Mutation<G> mutation,
                            float mutateRate,
                            int seed,
                            boolean debug) {
        this.initializer = initializer;
        this.generation = generation;
        this.geneticizor = geneticizor;
        this.degeneticizor = degeneticizor;
        this.fitness = fitness;
        this.selector = selector;
        this.crossover = crossover;
        this.crossoverRate = crossoverRate;
        this.mutation = mutation;
        this.mutateRate = mutateRate;
        this.seed = seed;
        this.random = new Random(seed);
        this.debug = debug;
    }

    /**
     * Perform genetic algorithm.
     *
     * @return final population after processing the genetic algorithm
     */
    public LinkedList<GenerationEntity<E>> process() {

        LinkedList<GenerationEntity<E>> population = initializer.initialize();

        log("original population: " + population);

        for (int i = 0; i < generation; i++) {
            float totalFitness = 0;


            for (GenerationEntity<E> e : population) {
                float score =0;
                if(Float.isNaN(e.getFitness())) {
                    score = fitness.fitness(e.getEntity());
                    e.setFitness(score);
                }
                else {
                    score = e.getFitness();
                }
                totalFitness += score;
            }

            population = selector.select(population, totalFitness, i);

            for (int j = 0; j < population.size(); j++) {
                if(crossoverRate >= random.nextFloat()) {
                    E e1 = population.get(j).getEntity();
                    E e2 = population.get(random.nextInt(population.size())).getEntity();
                    G gen1 = geneticizor.toGenetic(e1);
                    G gen2 = geneticizor.toGenetic(e2);
                    G childGen = crossover.crossover(gen1, gen2);
                    E child = degeneticizor.fromGenetic(childGen);
                    population.add(new GenerationEntity<E>(child, i, fitness.fitness(child)));
                }

                if(mutateRate >= random.nextFloat()) {
                    GenerationEntity<E> ge = population.get(j);
                    G gen = geneticizor.toGenetic(ge.getEntity());
                    G mutatedGen = mutation.mutate(gen);
                    E mutatedE = degeneticizor.fromGenetic(mutatedGen);
                    ge.setEntity(mutatedE);
                    ge.setFitness(fitness.fitness(mutatedE));
                }
            }

            log(i + "-th population: " + population);
        }

        log("final population: " + population);

        return population;
    }

    private void log(String str){
        if(!debug) return;

        System.out.println(str);
    }
}
