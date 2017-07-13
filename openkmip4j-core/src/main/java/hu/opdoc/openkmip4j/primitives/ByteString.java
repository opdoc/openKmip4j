package hu.opdoc.openkmip4j.primitives;

import hu.opdoc.openkmip4j.utils.EncryptedByteArray;
import hu.opdoc.openkmip4j.utils.GuardedByteArray;

/**
 * Created by peter on 2017.06.11..
 */
public class ByteString extends Primitive {

    public static final Type TYPE = Type.ByteString;

    private final EncryptedByteArray encryptedValue;

    public ByteString(final Tag tag, final GuardedByteArray guard) {
        super(tag, Type.ByteString, (guard == null) ? 0 : Long.valueOf(guard.getValue().length));
        this.encryptedValue = new EncryptedByteArray(guard);
    }

    public GuardedByteArray getValue() {
        return encryptedValue.getValue();
    }
}
