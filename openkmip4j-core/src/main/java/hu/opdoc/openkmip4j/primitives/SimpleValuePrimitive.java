package hu.opdoc.openkmip4j.primitives;

import hu.opdoc.openkmip4j.utils.converters.ConversionException;
import hu.opdoc.openkmip4j.utils.converters.TypeConverters;

/**
 * Created by peter on 2017.07.14..
 *
 * @param <T>   Internal data representation.
 */
public abstract class SimpleValuePrimitive<T> extends Primitive {

    protected final Class<T> valueClass;

    protected SimpleValuePrimitive(Tag tag, Type type, Long length, Class<T> valueClass) {
        super(tag, type, length);

        if (valueClass == null) {
            throw new NullPointerException(String.format("The value class of the %s primitive must be specified.", type));
        }
        this.valueClass = valueClass;
    }

    protected SimpleValuePrimitive(Tag tag, Type type, Class<T> valueClass) {
        this(tag, type, null, valueClass);
    }

    public SimpleValuePrimitive(Type type, Class<T> valueClass) {
        this(null, type, valueClass);
    }

    public abstract T getValue();

    public <O> O getValue(final Class<O> expectedClass) throws ConversionException {
        if (expectedClass == null) {
            throw new NullPointerException("The expected class of the value must be specified.");
        }
        final T originalValue = getValue();
        return TypeConverters.getConverter((Class<T>)originalValue.getClass(), expectedClass).convert(originalValue);
    }

    public abstract void setValue(T value);

    public <I> void setValueConverted(final I value) throws ConversionException {
        setValue((value == null) ? null : TypeConverters.getConverter((Class<I>)value.getClass(), valueClass).convert(value));
    }
}
