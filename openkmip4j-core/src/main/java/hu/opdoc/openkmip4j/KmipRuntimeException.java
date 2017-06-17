package hu.opdoc.openkmip4j;

/**
 * Created by peter on 2017.05.24..
 */
public class KmipRuntimeException extends RuntimeException {

    public KmipRuntimeException() {
    }

    public KmipRuntimeException(String message) {
        super(message);
    }

    public KmipRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public KmipRuntimeException(Throwable cause) {
        super(cause);
    }

    public KmipRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
