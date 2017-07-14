package hu.opdoc.openkmip4j.primitives;

import hu.opdoc.openkmip4j.utils.arrays.EncryptedByteArray;
import hu.opdoc.openkmip4j.utils.arrays.GuardedByteArray;
import hu.opdoc.openkmip4j.utils.arrays.GuardedCharArray;

import java.nio.charset.StandardCharsets;

/**
 * Created by peter on 2017.06.11.
 */
public class TextString extends SimpleValuePrimitive<GuardedCharArray> {

    public static final Type TYPE = Type.TextString;

    private EncryptedByteArray encryptedValue = null;

    public TextString() {
        this(null);
    }

    public TextString(final Tag tag) {
        super(tag, Type.TextString, 0l, GuardedCharArray.class);
    }

    public TextString(final Tag tag, final String value) {
        super(tag, Type.TextString, 0l, GuardedCharArray.class);
        setValue(value);
    }

    public TextString(final Tag tag, final GuardedCharArray value) {
        super(tag, Type.TextString, 0l, GuardedCharArray.class);
        setValue(value);
    }

    public TextString(final Tag tag, final GuardedByteArray encoded) {
        super(tag, Type.TextString, Long.valueOf(encoded.getValue().length), GuardedCharArray.class);
        this.encryptedValue = new EncryptedByteArray(encoded);
    }

    public GuardedByteArray getEncoded() {
        return encryptedValue.getValue();
    }

    @Override
    public GuardedCharArray getValue() {
        try (final GuardedByteArray decryptedBytes = getEncoded()) {
            final GuardedCharArray result = new GuardedCharArray(StandardCharsets.UTF_8.decode(decryptedBytes.getByteBuffer()));
            return result;
        }
    }

    @Override
    public void setValue(final GuardedCharArray value) {
        if (value == null) {
            this.encryptedValue = null;
        } else {
            try (final GuardedByteArray valueBytes = new GuardedByteArray(StandardCharsets.UTF_8.encode(value.getCharBuffer()))) {
                value.getClass();    // Ensure the guard (and the protected value) won't be destroyed before.
                setLength(valueBytes.getValue().length);
                this.encryptedValue = new EncryptedByteArray(valueBytes);
            }
        }
    }

    public void setValue(final String value) {
        try (final GuardedByteArray guardedValue = new GuardedByteArray(value.getBytes(StandardCharsets.UTF_8))) {
            setLength(guardedValue.getValue().length);
            this.encryptedValue = new EncryptedByteArray(guardedValue);
        }
    }

    @Override
    public boolean isEmpty() {
        return encryptedValue == null;
    }

    @Override
    protected void calculateLength() {
        if (encryptedValue != null && getLength() == null) {
            try (final GuardedByteArray decryptedValue = encryptedValue.getValue()) {
                setLength(decryptedValue.getValue().length);
            }
        }
    }
}
