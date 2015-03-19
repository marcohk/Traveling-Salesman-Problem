package com.github.marcohk.util;

/**
 * Created by Marco on 14/3/2015.
 */
public interface Crossover<G> {
    G crossover(G gene1, G gene2);
}
