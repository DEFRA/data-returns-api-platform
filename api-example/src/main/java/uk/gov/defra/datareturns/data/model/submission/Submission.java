package uk.gov.defra.datareturns.data.model.submission;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import uk.gov.defra.datareturns.data.conversion.MonthConverter;
import uk.gov.defra.datareturns.data.model.AbstractBaseEntity;
import uk.gov.defra.datareturns.validation.submission.ValidSubmission;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.Month;

/**
 * An example submission - demonstrates API platform features
 *
 * @author Sam Gardner-Dell
 */
@Entity(name = "example_submission")
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uniq_contact_id_and_season", columnNames = {"contactId", "season"})
})
@Audited
@Getter
@Setter
@ValidSubmission
public class Submission extends AbstractBaseEntity<Long> {
    /**
     * Database sequence name for this entity
     */
    public static final String SEQUENCE = "example_submission_id_seq";

    /**
     * Primary key
     */
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = SEQUENCE, sequenceName = SEQUENCE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE)
    private Long id;

    /**
     * The contact identifier
     */
    @Column(nullable = false)
    private String contactId;

    /**
     * The season (year) pertaining to the submission
     */
    @Column(nullable = false, updatable = false)
    private Short season;

    /**
     * The month this submission  relates to
     */
    @Convert(converter = MonthConverter.class)
    private Month month;

    /**
     * The submission status
     */
    @Enumerated(EnumType.STRING)
    private SubmissionStatus status;
}
