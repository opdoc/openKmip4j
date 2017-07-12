package hu.opdoc.openkmip4j.primitives;

import hu.opdoc.openkmip4j.utils.EncryptedByteArray;
import hu.opdoc.openkmip4j.utils.GuardedByteArray;
import hu.opdoc.openkmip4j.utils.GuardedCharArray;

import java.nio.charset.StandardCharsets;

/**
 * Created by peter on 2017.06.11..
 */
public class TextString extends Primitive {

    private final EncryptedByteArray encryptedValue;

    public TextString(final Tag tag, final GuardedCharArray value) {
        super(tag, Type.TextString, 0l);

        if (value == null || value.getValue().length == 0) {
            this.encryptedValue = new EncryptedByteArray();
        } else {
            try (final GuardedByteArray valueBytes = new GuardedByteArray(StandardCharsets.UTF_8.encode(value.getCharBuffer()))) {
                value.getClass();    // Ensure the guard (and the protected value) won't be destroyed before.
                this.length = Long.valueOf(valueBytes.getValue().length);
                this.encryptedValue = new EncryptedByteArray(valueBytes);
            }
        }
    }

    public GuardedByteArray getEncoded() {
        return encryptedValue.getValue();
    }

    public GuardedCharArray getValue() {
        try (final GuardedByteArray decryptedBytes = getEncoded()) {
            final GuardedCharArray result = new GuardedCharArray(StandardCharsets.UTF_8.decode(decryptedBytes.getByteBuffer()));
            return result;
        }
    }
}
