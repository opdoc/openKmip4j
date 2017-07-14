package hu.opdoc.openkmip4j.primitives;

import hu.opdoc.openkmip4j.utils.arrays.EncryptedByteArray;
import hu.opdoc.openkmip4j.utils.arrays.GuardedByteArray;

import java.util.Arrays;

/**
 * Created by peter on 2017.06.11.
 */
public class BigInteger extends SimpleValuePrimitive<java.math.BigInteger> {

    public static final Type TYPE = Type.BigInteger;

    private EncryptedByteArray encryptedValue = null;

    public BigInteger() {
        this(null);
    }

    public BigInteger(final Tag tag) {
        super(tag, TYPE, java.math.BigInteger.class);
    }

    public BigInteger(final Tag tag, final java.math.BigInteger value) {
        this(tag, new GuardedByteArray((value == null ? java.math.BigInteger.ZERO : value).toByteArray()));
    }

    public BigInteger(final Tag tag, final GuardedByteArray value) {
        super(tag, TYPE, value == null ? 0l : ceilDivMinOne(value.getValue().length, 8) * 8, java.math.BigInteger.class);
        setValue(value);
    }

    @Override
    public java.math.BigInteger getValue() {
        try (final GuardedByteArray decryptedValue = getByteArray()) {
            if (decryptedValue == null) {
                return null;
            } else if (decryptedValue.getValue().length == 0) {
                return java.math.BigInteger.ZERO;
            } else {
                return new java.math.BigInteger(decryptedValue.getValue());
            }
        }
    }

    public GuardedByteArray getByteArray() {
        return (encryptedValue == null) ? null : encryptedValue.getValue();
    }

    @Override
    public void setValue(java.math.BigInteger value) {
        try (final GuardedByteArray guardedValue = new GuardedByteArray(value.toByteArray())) {
            setValue(guardedValue);
        }
    }

    public void setValue(GuardedByteArray value) {
        final long originalLength = value.getValue().length;
        final long paddedLength = ceilDivMinOne(value.getValue().length, 8) * 8;

        final GuardedByteArray paddedValue;
        if (originalLength == paddedLength) {
            paddedValue = value;
        } else {
            paddedValue = new GuardedByteArray(new byte[Long.valueOf(paddedLength).intValue()]);
            Arrays.fill(
                    paddedValue.getValue(),
                    value.getValue()[0] < 0 ? (byte)0xFF : 0x00
            );
            System.arraycopy(
                    value.getValue(),
                    0,
                    paddedValue.getValue(),
                    Long.valueOf(paddedLength - originalLength).intValue(),
                    value.getValue().length
            );
        }
        this.encryptedValue = new EncryptedByteArray(paddedValue);
        setLength(paddedLength);
        paddedValue.destroy();
    }

    @Override
    public boolean isEmpty() {
        return encryptedValue == null;
    }

    @Override
    protected void calculateLength() {
        // In theory this won't ever decrypt and count as the length is calculated
        // each time a value is set.
        if (getLength() == null) {
            try (final GuardedByteArray value = encryptedValue.getValue()) {
                setLength(ceilDivMinOne(value.getValue().length, 8) * 8);
            }
        }
    }

    private static long ceilDivMinOne(final int x, final int y) {
        int result = Math.floorDiv(x, y);
        if (result * y < x) {
            result++;
        }
        return Math.max(1, result);
    }
}
