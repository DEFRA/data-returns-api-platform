package uk.gov.defra.datareturns.data.model;

/**
 * Interface for jpa entities
 *
 * @param <T> the generic type of the identifiable
 * @author Sam Gardner-Dell
 */
public interface Identifiable<T> {
    /**
     * @return the entity identifier
     */
    T getId();
}
