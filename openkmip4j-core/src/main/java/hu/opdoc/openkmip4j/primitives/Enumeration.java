package hu.opdoc.openkmip4j.primitives;

import java.lang.*;

/**
 * Created by peter on 2017.06.11..
 */
public class Enumeration extends Primitive {

    private final String stringValue;
    private final java.lang.Integer numericValue;

    public Enumeration(final Tag tag, final String stringValue) {
        super(tag, Type.Enumeration);
        this.stringValue = stringValue;
        this.numericValue = null;
        vaildate();
    }

    public Enumeration(final Tag tag, final java.lang.Integer numericValue) {
        super(tag, Type.Enumeration);
        this.stringValue = null;
        this.numericValue = numericValue;
        vaildate();
    }

    public <T> T getValue(final ConstantResolver<T> resolver) {
        if (numericValue != null) {
            return resolver.resolve(numericValue);
        } else {
            return resolver.resolve(stringValue);
        }
    }

    private void vaildate() {
        if (stringValue == null && numericValue == null) {
            throw new IllegalArgumentException(String.format("Either a numeric or a name value must be set for the %s Enumeration.", tag.toString()));
        }
    }
}
