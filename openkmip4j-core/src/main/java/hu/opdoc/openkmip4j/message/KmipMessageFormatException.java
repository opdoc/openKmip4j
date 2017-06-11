package hu.opdoc.openkmip4j.message;

import hu.opdoc.openkmip4j.KmipRuntimeException;

/**
 * Created by peter on 2017.05.24..
 */
public class KmipMessageFormatException extends KmipRuntimeException {
    // TODO: Fill me up with code!

    public KmipMessageFormatException(String message) {
        super(message);
    }

    public KmipMessageFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public KmipMessageFormatException(Throwable cause) {
        super(cause);
    }
}
