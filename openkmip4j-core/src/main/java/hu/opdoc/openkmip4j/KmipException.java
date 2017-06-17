package hu.opdoc.openkmip4j;

/**
 * Created by peter on 2017.06.17..
 */
public class KmipException extends Exception {

    public KmipException() {
    }

    public KmipException(String message) {
        super(message);
    }

    public KmipException(String message, Throwable cause) {
        super(message, cause);
    }

    public KmipException(Throwable cause) {
        super(cause);
    }

    public KmipException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
