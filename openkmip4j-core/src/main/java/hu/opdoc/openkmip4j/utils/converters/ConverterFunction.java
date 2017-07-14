package hu.opdoc.openkmip4j.utils.converters;

/**
 * Created by peter on 2017.07.14.
 */
public interface ConverterFunction<F, T> {

    T convert(F from) throws ConversionException;
}
