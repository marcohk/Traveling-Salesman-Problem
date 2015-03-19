package com.github.marcohk.util;

import java.util.LinkedList;

/**
 * Created by Marco on 14/3/2015.
 */
public interface Initializer<E> {
    LinkedList<GenerationEntity<E>> initialize();
}
