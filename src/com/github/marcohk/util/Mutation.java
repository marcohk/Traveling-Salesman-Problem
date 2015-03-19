package com.github.marcohk.util;

/**
 * Created by Marco on 14/3/2015.
 */
public interface Mutation<G> {
    G mutate(G genetic);
}
