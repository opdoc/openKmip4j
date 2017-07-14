package hu.opdoc.openkmip4j.primitives;

import hu.opdoc.openkmip4j.KmipInvalidSettingException;
import hu.opdoc.openkmip4j.message.KmipEnum;

import java.lang.*;

/**
 * Created by peter on 2017.06.11..
 */
public class Enumeration extends Primitive {

    public static final Type TYPE = Type.Enumeration;

    private String stringValue;
    private Long numericValue;

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
        setValue(value);
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

    public void setValue(final KmipEnum value) {
        this.stringValue = value.name();
        this.numericValue = value.getValue();

        if (numericValue == null) {
            throw new KmipInvalidSettingException(
                    String.format(
                            "The numeric value of a KmipEnum must not be null: %s::%s",
                            value.getClass().getCanonicalName(),
                            value.name()
                    )
            );
        }
    }

    @Override
    public boolean isEmpty() {
        return stringValue == null && numericValue == null;
    }

    @Override
    protected void validate() {
        super.validate();
        if (isEmpty()) {
            throw new KmipInvalidSettingException(
                    String.format(
                            "Either a numeric or a name value must be set for the %s Enumeration.",
                            getTag()
                    )
            );
        }
    }

    @Override
    protected void calculateLength() {
        setLength(type.getLength());
    }
}
