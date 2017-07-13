package hu.opdoc.openkmip4j;

import hu.opdoc.openkmip4j.message.KmipMessageFormatException;
import hu.opdoc.openkmip4j.primitives.*;
import hu.opdoc.openkmip4j.primitives.Boolean;
import hu.opdoc.openkmip4j.primitives.Integer;
import hu.opdoc.openkmip4j.representation.KmipTypeMismatchesPrimitiveClassException;

/**
 * Created by peter on 2017.07.13..
 */
public abstract class KmipObject {

    protected final Tag tag;

    protected KmipObject(Tag tag) {
        this.tag = tag;
    }

    public Tag getTag() {
        return tag;
    }

    public abstract Primitive toPrimitive();

    protected BigInteger assertBigInteger(final Primitive checkIt) {
        return assertPrimitive(checkIt, BigInteger.class);
    }

    protected Boolean assertBoolean(final Primitive checkIt) {
        return assertPrimitive(checkIt, Boolean.class);
    }

    protected ByteString assertByteString(final Primitive checkIt) {
        return assertPrimitive(checkIt, ByteString.class);
    }

    protected DateTime assertDateTime(final Primitive checkIt) {
        return assertPrimitive(checkIt, DateTime.class);
    }

    protected Enumeration assertEnumeration(final Primitive checkIt) {
        return assertPrimitive(checkIt, Enumeration.class);
    }

    protected Integer assertInteger(final Primitive checkIt) {
        return assertPrimitive(checkIt, Integer.class);
    }

    protected Interval assertInterval(final Primitive checkIt) {
        return assertPrimitive(checkIt, Interval.class);
    }

    protected LongInteger assertLongInteger(final Primitive checkIt) {
        return assertPrimitive(checkIt, LongInteger.class);
    }

    protected Structure assertStructure(final Primitive checkIt) {
        return assertPrimitive(checkIt, Structure.class);
    }

    protected TextString assertTextString(final Primitive checkIt) {
        return assertPrimitive(checkIt, TextString.class);
    }

    protected <T extends Primitive> T assertPrimitive(final Primitive checkIt, final Class<T> expectedClass) {
        if (checkIt == null) {
            return null;
        }
        if (expectedClass == null) {
            throw new NullPointerException("The expected Primitive Class must be specified.");
        }

        final Type expectedType;
        try {
            expectedType = (Type) (expectedClass.getField("TYPE").get(null));
        } catch (NoSuchFieldException|IllegalAccessException cause) {
            throw new KmipRuntimeException("Invalid Primitive Class that contains no TYPE static field: " + expectedClass.getSimpleName());
        }
        if (!expectedType.getPrimitiveClass().equals(expectedClass)) {
            throw new KmipRuntimeException(String.format("The Type (%s) specified in Primitive Class %s is not related to the class.", expectedType, expectedClass.getSimpleName()));
        }

        if (expectedType != checkIt.getType()) {
            throw new KmipMessageFormatException(String.format("The expected type for Tag %s is %s but found %s instead.", checkIt.getTag(), expectedType, checkIt.getType()));
        }
        if (!expectedClass.isAssignableFrom(checkIt.getClass())) {
            throw new KmipTypeMismatchesPrimitiveClassException(checkIt);
        }

        return (T)checkIt;
    }

    protected void assertNotRepeated(final Object readValue, final String valueName) {
        if (readValue != null) {
            throw new KmipMessageFormatException(String.format("%s must not be repeated in %s.", valueName, tag.name()));
        }
    }
}
