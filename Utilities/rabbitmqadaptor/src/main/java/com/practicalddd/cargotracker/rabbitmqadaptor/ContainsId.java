package com.practicalddd.cargotracker.rabbitmqadaptor;


/**
 * <p>Events that implement this interface are enabled to carry
 * arbitrary identifiers within an event. This interface behaves
 * exactly like {@link ContainsContent} and is only purposed
 * for better readability in the code.</p>
 *
 * @see ContainsContent
 * @author christian.bick
 */
public interface ContainsId<T> {
    void setId(T id);
    T getId();
}
