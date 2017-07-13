package hu.opdoc.openkmip4j.primitives;

import hu.opdoc.openkmip4j.utils.EncryptedByteArray;
import hu.opdoc.openkmip4j.utils.GuardedByteArray;

import java.util.Arrays;

/**
 * Created by peter on 2017.06.11..
 */
public class BigInteger extends Primitive {

    public static final Type TYPE = Type.BigInteger;

    private final EncryptedByteArray encryptedValue;

    public BigInteger(final Tag tag, final java.math.BigInteger value) {
        this(tag, new GuardedByteArray((value == null ? java.math.BigInteger.ZERO : value).toByteArray()));
    }

    public BigInteger(final Tag tag, final GuardedByteArray value) {
        super(tag, Type.BigInteger, value == null ? 0l : ceilDivMinOne(value.getValue().length, 8) * 8);
        final GuardedByteArray paddedValue;
        if (length.equals(Long.valueOf(value.getValue().length))) {
            paddedValue = value;
        } else {
            paddedValue = new GuardedByteArray(new byte[length.intValue()]);
            Arrays.fill(
                    paddedValue.getValue(),
                    value.getValue()[0] < 0 ? (byte)0xFF : 0x00
            );
            System.arraycopy(
                    value.getValue(),
                    0,
                    paddedValue.getValue(),
                    length.intValue() - value.getValue().length,
                    value.getValue().length
            );
        }
        this.encryptedValue = new EncryptedByteArray(paddedValue);
    }

    public java.math.BigInteger getValue() {
        try (final GuardedByteArray decryptedValue = getByteArray()) {
            return (decryptedValue.getValue().length == 0) ? java.math.BigInteger.ZERO : new java.math.BigInteger(decryptedValue.getValue());
        }
    }

    public GuardedByteArray getByteArray() {
        return encryptedValue.getValue();
    }

    private static long ceilDivMinOne(final int x, final int y) {
        int result = Math.floorDiv(x, y);
        if (result * y < x) {
            result++;
        }
        return Math.max(1, result);
    }
}
