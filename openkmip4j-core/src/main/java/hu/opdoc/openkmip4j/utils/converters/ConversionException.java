package hu.opdoc.openkmip4j.utils.converters;

import hu.opdoc.openkmip4j.KmipException;

/**
 * Created by peter on 2017.07.14.
 */
public class ConversionException extends KmipException {

    public ConversionException() {
    }

    public ConversionException(String message) {
        super(message);
    }

    public ConversionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConversionException(Throwable cause) {
        super(cause);
    }

    public ConversionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
