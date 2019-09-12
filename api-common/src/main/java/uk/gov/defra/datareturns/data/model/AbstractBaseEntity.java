package uk.gov.defra.datareturns.data.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

/**
 * Base class for all entities represented by this API
 *
 * @author Sam Gardner-Dell
 */
@MappedSuperclass
@EntityListeners(
        {
                AuditingEntityListener.class
        }
)
@Getter
public abstract class AbstractBaseEntity<ID> implements Identifiable<ID> {
    /**
     * Creation date of the entity
     */
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    @JsonProperty("_created")
    @SuppressFBWarnings("EI_EXPOSE_REP")
    private Date created;

    /**
     * Last modified date of the entity
     */
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @JsonProperty("_lastModified")
    @SuppressFBWarnings("EI_EXPOSE_REP")
    private Date lastModified;

    /**
     * Version of the entity (ETag support)
     */
    @Version
    @Column(nullable = false)
    @SuppressFBWarnings("EI_EXPOSE_REP")
    private Timestamp version;

    @Override
    public final boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (getId() == null) {
            return false;
        }
        final AbstractBaseEntity that = (AbstractBaseEntity) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        if (getId() == null) {
            return System.identityHashCode(this);
        }
        return Objects.hashCode(getId());
    }
}
