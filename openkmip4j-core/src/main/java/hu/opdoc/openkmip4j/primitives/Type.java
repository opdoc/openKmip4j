package hu.opdoc.openkmip4j.primitives;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by peter on 2017.05.24..
 */
public enum Type {
    Unset((byte) 0x00),

    Structure((byte) 0x01),
    Integer((byte) 0x02, 4l),
    LongInteger((byte) 0x03, 8l),
    BigInteger((byte) 0x04),
    Enumeration((byte) 0x05, 4l),
    Boolean((byte) 0x06, 8l),
    TextString((byte) 0x07),
    ByteString((byte) 0x08),
    DateTime((byte) 0x09, 8l),
    Interval((byte) 0x0A, 4l);

    private final byte value;
    private final Long length;

    Type(final byte value) {
        this(value, null);
    }

    Type(final byte value, final Long length) {
        this.value = value;
        this.length = length;
    }

    public byte getValue() {
        return value;
    }

    public Long getLength() {
        return length;
    }

    private static final Map<Byte, Type> valueMap = new HashMap<>(10);

    static {
        for (Type type : values()) {
            valueMap.put(type.getValue(), type);
        }
    }

    public static Type fromValue(byte value) {
        return fromValue(Byte.valueOf(value));
    }

    public static Type fromValue(Byte value) {
        final Type result = valueMap.get(value);
        if (result == null) {
            throw new IllegalArgumentException(String.format("Not a valid Type constant value: %d", value));
        }
        return result;
    }
}
