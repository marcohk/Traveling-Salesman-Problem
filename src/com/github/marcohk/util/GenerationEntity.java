package com.github.marcohk.util;

/**
 * Created by Marco on 14/3/2015.
 */
final public class GenerationEntity<E> {
    private E entity;
    private int generation;
    private float fitness;

    public GenerationEntity(E entity) {
        this(entity,0);
    }

    public GenerationEntity(E entity, int generation) {
        this(entity,generation,Float.NaN);
    }

    public GenerationEntity(E entity, int generation, float fitness) {
        this.entity = entity;
        this.generation = generation;
        this.fitness = fitness;
    }

    public E getEntity() {
        return entity;
    }

    protected void setEntity(E entity) {
        this.entity = entity;
    }

    public int getGeneration() {
        return generation;
    }

    public float getFitness() {
        return fitness;
    }

    protected void setFitness(float fitness) {
        this.fitness = fitness;
    }

    @Override
    public String toString() {
        return "GenerationEntity{" +
                "fitness=" + fitness +
                ",distance=" + 1f/fitness +
                ",entity=" + entity +
                ", generation=" + generation +
                '}';
    }
}
