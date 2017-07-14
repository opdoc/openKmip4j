package hu.opdoc.openkmip4j.utils.converters;

/**
 * Created by peter on 2017.07.14.
 */
public class NoConverterException extends ConversionException {

    private final Class<?> fromClass;
    private final Class<?> toClass;

    public NoConverterException(Class<?> fromClass, Class<?> toClass) {
        super(String.format("No conversion is available from %s to %s.", fromClass.getCanonicalName(), toClass.getCanonicalName()));
        this.fromClass = fromClass;
        this.toClass = toClass;
    }

    public Class<?> getFromClass() {
        return fromClass;
    }

    public Class<?> getToClass() {
        return toClass;
    }
}
