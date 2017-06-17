package hu.opdoc.openkmip4j.primitives;

/**
 * Created by peter on 2017.06.11..
 */
public class ByteString extends Primitive {

    private final byte[] value;

    public ByteString(final Tag tag, final byte[] value) {
        super(tag, Type.ByteString, value == null ? 0 : Long.valueOf(value.length));
        this.value = value == null ? new byte[0] : value;
    }

    public byte[] getValue() {
        return value;
    }
}
