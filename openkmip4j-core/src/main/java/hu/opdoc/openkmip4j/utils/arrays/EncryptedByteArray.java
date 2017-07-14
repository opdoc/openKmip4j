package hu.opdoc.openkmip4j.utils.arrays;

import hu.opdoc.openkmip4j.KmipRuntimeException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.GeneralSecurityException;

/**
 * Created by peter on 2017.07.11..
 */
public class EncryptedByteArray {

    private static final String ENCRYPTION_ALGORITHM = "AES";
    private static final int    ENCRYPTION_KEY_SIZE  = 128;
    private static final String ENCRYPTION_CIPHER    = ENCRYPTION_ALGORITHM + "/CBC/PKCS5Padding";

    private byte[] value;
    private int length;
    private final SecretKey encryptionKey;
    private final Cipher encryptionCipher;

    public EncryptedByteArray() {
        this(null);
    }

    public EncryptedByteArray(final GuardedByteArray guard) {

        try {
            final KeyGenerator keyGenerator = KeyGenerator.getInstance(ENCRYPTION_ALGORITHM);
            keyGenerator.init(ENCRYPTION_KEY_SIZE);

            this.encryptionKey = keyGenerator.generateKey();
            this.encryptionCipher = Cipher.getInstance(ENCRYPTION_CIPHER);
            setValue(guard);
        } catch (GeneralSecurityException cause) {
            throw new KmipRuntimeException("Cannot encrypt secret data stored in memory!", cause);
        }
    }

    public GuardedByteArray getValue() {
        if (value.length == 0) {
            return new GuardedByteArray(value);
        }

        try {
            encryptionCipher.init(Cipher.DECRYPT_MODE, encryptionKey);
            return new GuardedByteArray(encryptionCipher.doFinal(value));
        } catch (GeneralSecurityException cause) {
            throw new KmipRuntimeException("Cannot decrypt secret data stored in memory!", cause);
        }
    }

    public void setValue(GuardedByteArray guard) {
        if (guard == null || guard.getValue().length == 0) {
            this.value = new byte[0];
            this.length = 0;
        } else {
            try {
                encryptionCipher.init(Cipher.ENCRYPT_MODE, encryptionKey);
                this.value = encryptionCipher.doFinal(guard.getValue());
                this.length = guard.getValue().length;
            } catch (GeneralSecurityException cause) {
                throw new KmipRuntimeException("Cannot encrypt secret data stored in memory!", cause);
            }
        }
    }

    public int length() {
        return length;
    }

    public boolean isEmpty() {
        return length == 0;
    }
}
