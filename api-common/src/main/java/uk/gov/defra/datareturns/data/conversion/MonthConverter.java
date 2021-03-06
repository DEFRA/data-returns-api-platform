package uk.gov.defra.datareturns.data.conversion;


import javax.persistence.AttributeConverter;
import java.time.Month;

/**
 * Specific converter for the {@link Month} enumeration. Similar to using @{@link javax.persistence.Enumerated}({@link javax.persistence.EnumType}
 * .ORDINAL) except that it generates a 1-based ordinal (as SQL month functions equate 1=January)
 *
 * @author Sam Gardner-Dell
 */
public class MonthConverter implements AttributeConverter<Month, Short> {
    @Override
    public Short convertToDatabaseColumn(final Month attribute) {
        if (attribute != null) {
            return (short) attribute.getValue();
        }
        return null;
    }

    @Override
    public Month convertToEntityAttribute(final Short dbData) {
        Month month = null;
        if (dbData != null) {
            month = Month.of(dbData);
        }
        return month;
    }
}
