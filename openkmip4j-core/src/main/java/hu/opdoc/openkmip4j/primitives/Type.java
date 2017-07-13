package hu.opdoc.openkmip4j.primitives;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by peter on 2017.05.24..
 */
public enum Type {
    Structure((byte) 0x01, hu.opdoc.openkmip4j.primitives.Structure.class),
    Integer((byte) 0x02, 4l, hu.opdoc.openkmip4j.primitives.Integer.class),
    LongInteger((byte) 0x03, 8l, hu.opdoc.openkmip4j.primitives.LongInteger.class),
    BigInteger((byte) 0x04, hu.opdoc.openkmip4j.primitives.BigInteger.class),
    Enumeration((byte) 0x05, 4l, hu.opdoc.openkmip4j.primitives.Enumeration.class),
    Boolean((byte) 0x06, 8l, hu.opdoc.openkmip4j.primitives.Boolean.class),
    TextString((byte) 0x07, hu.opdoc.openkmip4j.primitives.TextString.class),
    ByteString((byte) 0x08, hu.opdoc.openkmip4j.primitives.ByteString.class),
    DateTime((byte) 0x09, 8l, hu.opdoc.openkmip4j.primitives.DateTime.class),
    Interval((byte) 0x0A, 4l, hu.opdoc.openkmip4j.primitives.Interval.class);

    private final byte value;
    private final Long length;
    private final Class<? extends Primitive> primitiveClass;

    Type(final byte value, final Class<? extends Primitive> primitiveClass) {
        this(value, null, primitiveClass);
    }

    Type(final byte value, final Long length, final Class<? extends Primitive> primitiveClass) {
        this.value = value;
        this.length = length;
        this.primitiveClass = primitiveClass;
    }

    public byte getValue() {
        return value;
    }

    public Long getLength() {
        return length;
    }

    public Class<? extends Primitive> getPrimitiveClass() {
        return primitiveClass;
    }

    private static final Map<Byte, Type> valueMap = new HashMap<>(10);

    static {
        for (Type type : values()) {
            valueMap.put(type.getValue(), type);
        }
    }

    public static Type valueOf(byte value) {
        return valueOf(Byte.valueOf(value));
    }

    public static Type valueOf(Byte value) {
        final Type result = valueMap.get(value);
        if (result == null) {
            throw new IllegalArgumentException(String.format("Not a valid Type constant value: %d", value));
        }
        return result;
    }
}
