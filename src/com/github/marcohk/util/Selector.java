package com.github.marcohk.util;

import java.util.LinkedList;

/**
 * Created by Marco on 14/3/2015.
 */
public interface Selector<E> {
    LinkedList<GenerationEntity<E>> select(LinkedList<GenerationEntity<E>> population, float totalFitness, int generation);
}
