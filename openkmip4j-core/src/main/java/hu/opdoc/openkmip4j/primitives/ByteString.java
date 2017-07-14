package hu.opdoc.openkmip4j.primitives;

import hu.opdoc.openkmip4j.utils.arrays.EncryptedByteArray;
import hu.opdoc.openkmip4j.utils.arrays.GuardedByteArray;

/**
 * Created by peter on 2017.06.11.
 */
public class ByteString extends SimpleValuePrimitive<GuardedByteArray> {

    public static final Type TYPE = Type.ByteString;

    private EncryptedByteArray encryptedValue = null;

    public ByteString() {
        this(null);
    }

    public ByteString(final Tag tag) {
        super(tag, TYPE, GuardedByteArray.class);
    }

    public ByteString(final Tag tag, final GuardedByteArray guard) {
        super(tag, TYPE, GuardedByteArray.class);
        setValue(guard);
    }

    @Override
    public GuardedByteArray getValue() {
        return (encryptedValue == null) ? null : encryptedValue.getValue();
    }

    @Override
    public void setValue(GuardedByteArray value) {
        setLength(value.getValue().length);
        this.encryptedValue = new EncryptedByteArray(value);
    }

    @Override
    public boolean isEmpty() {
        return encryptedValue == null;
    }

    @Override
    protected void calculateLength() {
        if (getLength() == null) {
            try (final GuardedByteArray decryptedValue = getValue()) {
                if (decryptedValue != null) {
                    setLength(decryptedValue.getValue().length);
                }
            }
        }
    }
}
