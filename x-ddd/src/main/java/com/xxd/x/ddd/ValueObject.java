package com.xxd.x.ddd;

import java.io.Serializable;

/**
 * A value object, as described in the DDD book.
 */
public interface ValueObject<T> extends Serializable {
    boolean sameValueAs(T other);
}