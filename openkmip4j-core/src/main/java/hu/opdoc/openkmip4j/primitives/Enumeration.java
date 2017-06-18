package hu.opdoc.openkmip4j.primitives;

import hu.opdoc.openkmip4j.message.KmipEnum;

import java.lang.*;

/**
 * Created by peter on 2017.06.11..
 */
public class Enumeration extends Primitive {

    private final String stringValue;
    private final Long numericValue;

    public Enumeration(final Tag tag, final String stringValue) {
        super(tag, Type.Enumeration);
        this.stringValue = stringValue;
        this.numericValue = null;
        validate();
    }

    public Enumeration(final Tag tag, final Long numericValue) {
        super(tag, Type.Enumeration);
        this.stringValue = null;
        this.numericValue = numericValue;
        validate();
    }

    public Enumeration(final Tag tag, final KmipEnum value) {
        super(tag, Type.Enumeration);
        this.stringValue = value.name();
        this.numericValue = value.getValue();
        validate();
        if (numericValue == null) {
            throw new IllegalArgumentException(String.format("The value of a KmipEnum must not be null: %s::%s", value.getClass().getCanonicalName(), value.name()));
        }
    }

    public Long getNumericValue() {
        return numericValue;
    }

    public <T extends KmipEnum> T getValue(final ConstantResolver<T> resolver) {
        if (numericValue != null) {
            return resolver.resolve(numericValue);
        } else {
            return resolver.resolve(stringValue);
        }
    }

    private void validate() {
        if (stringValue == null && numericValue == null) {
            throw new IllegalArgumentException(String.format("Either a numeric or a name value must be set for the %s Enumeration.", tag.toString()));
        }
    }
}
