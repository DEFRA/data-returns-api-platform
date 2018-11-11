package uk.gov.defra.datareturns.data;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * Base repository for all repositories
 *
 * @param <E>  the type of the entity
 * @param <ID> the type of the entity's primary key
 * @author Sam Gardner-Dell
 */
@NoRepositoryBean
public interface BaseRepository<E, ID extends Serializable> extends JpaRepository<E, ID> {
    /**
     * @return JPA entity information to be retrieved for this repository
     */
    JpaEntityInformation<E, ID> getEntityInformation();

    /**
     * @param <E>  the type of the entity
     *             Default implementation for the {@link BaseRepository}
     * @param <ID> the type of the entity's primary key
     */
    @Slf4j
    class BaseRepositoryImpl<E, ID extends Serializable> extends SimpleJpaRepository<E, ID> implements BaseRepository<E, ID> {
        private final JpaEntityInformation<E, ID> entityInformation;
        private final EntityManager entityManager;

        /**
         * Default constructor (as per spring data framework)
         *
         * @param entityInformation the {@link JpaEntityInformation} for the entity targeted by this repository
         * @param entityManager     the JPA entity manager.
         */
        public BaseRepositoryImpl(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") final JpaEntityInformation<E, ID>
                                          entityInformation,
                                  final EntityManager entityManager) {
            super(entityInformation, entityManager);
            this.entityInformation = entityInformation;
            this.entityManager = entityManager;
        }

        @Override
        public JpaEntityInformation<E, ID> getEntityInformation() {
            return this.entityInformation;
        }

        /**
         * Allow subclasses to retrieve the entity manager
         *
         * @return the {@link EntityManager} backing the repository
         */
        protected EntityManager getEntityManager() {
            return entityManager;
        }
    }
}
