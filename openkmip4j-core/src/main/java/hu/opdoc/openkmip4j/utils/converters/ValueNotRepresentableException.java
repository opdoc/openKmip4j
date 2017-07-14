package hu.opdoc.openkmip4j.utils.converters;

/**
 * Created by peter on 2017.07.14.
 */
public class ValueNotRepresentableException extends ConversionException {

    public ValueNotRepresentableException(String message) {
        super(message);
    }

    public ValueNotRepresentableException(String message, Throwable cause) {
        super(message, cause);
    }
}
