package hu.opdoc.openkmip4j.primitives;

import hu.opdoc.openkmip4j.message.Tag;

import java.lang.*;
import java.lang.Integer;

/**
 * Created by peter on 2017.06.11..
 */
public class TagValue {

    private String stringValue;
    private java.lang.Integer numericValue = null;

    public TagValue(String stringValue, Integer numericValue) {
        this.stringValue = stringValue;
        this.numericValue = numericValue;
        validate();
    }

    public TagValue(final String stringValue) {
        this.stringValue = stringValue;
        validate();
    }

    public <T extends Tag> T getTag(final ConstantResolver<T> resolver) {
        final T result;
        if (numericValue != null) {
            result = resolver.resolve(numericValue);
            stringValue = result.name();
        } else {
            result = resolver.resolve(stringValue);
            numericValue = result.getValue();
        }

        return result;
    }

    public String getStringValue() {
        return stringValue;
    }

    public Integer getNumericValue() {
        return numericValue;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(getClass().getSimpleName());
        sb.append("{");
        if (stringValue != null) {
            sb.append(stringValue);
        } else {
            sb.append("0x").append(java.lang.Integer.toHexString(numericValue));
        }
        sb.append("}");
        return sb.toString();
    }

    private void validate() {
        if (stringValue != null && stringValue.length() == 0) {
            stringValue = null;
        }
        if (stringValue == null && numericValue == null) {
            throw new IllegalArgumentException("At least the name or the numeric value of the Tag must specified.");
        }
        if (stringValue != null && stringValue.startsWith("0x")) {
            numericValue = java.lang.Integer.parseUnsignedInt(stringValue.substring(2), 16);
            stringValue = null;
        }
    }
}
