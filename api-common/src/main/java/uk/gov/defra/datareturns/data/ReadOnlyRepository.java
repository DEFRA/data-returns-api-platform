package uk.gov.defra.datareturns.data;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.Optional;

/**
 * Interface to be used by repositories that should be treated as read-only
 *
 * @author Sam Gardner-Dell
 */
@NoRepositoryBean
public interface ReadOnlyRepository<T, I extends Serializable> extends Repository<T, I> {

    /**
     * Retrieves an entity by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal null} if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    Optional<T> findOne(I id);

    /**
     * Returns whether an entity with the given id exists.
     *
     * @param id must not be {@literal null}.
     * @return true if an entity with the given id exists, alse otherwise
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    boolean exists(I id);

    /**
     * Returns all instances of the type.
     *
     * @return all entities
     */
    Iterable<T> findAll();

    /**
     * Returns all instances of the type with the given IDs.
     *
     * @param ids
     * @return
     */
    Iterable<T> findAll(Iterable<I> ids);

    /**
     * Returns the number of entities available.
     *
     * @return the number of entities
     */
    long count();
}
