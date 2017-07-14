package hu.opdoc.openkmip4j.utils.converters;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by peter on 2017.07.14..
 */
public abstract class TypeConverters {

    private static final ConcurrentHashMap<Class<?>, ConcurrentHashMap<Class<?>, ConverterFunction<?, ?>>> converters = new ConcurrentHashMap<>();

    private TypeConverters() {}

    public static <A, B> void registerConverter(final TypeConverter<A, B> converter) {
        final Class<A> classA = converter.getClassA();
        final Class<B> classB = converter.getClassB();

        converters.computeIfAbsent(classA, k -> new ConcurrentHashMap<>())
                .putIfAbsent(classB, converter.fromAtoB());
        converters.computeIfAbsent(classB, k -> new ConcurrentHashMap<>())
                .putIfAbsent(classA, converter.fromBtoA());
    }

    public static <F, T> ConverterFunction<F, T> getConverter(final Class<F> fromClass, final Class<T> toClass) throws NoConverterException {
        final ConverterFunction<F, T> result;
        if (fromClass.equals(toClass)) {
            result = f -> (T)f;
        } else {
            Map<Class<?>, ConverterFunction<?, ?>> toMap = converters.get(fromClass);
            if (toMap != null) {
                result = (ConverterFunction<F, T>) toMap.get(toClass);
            } else {
                result = null;
            }
        }

        if (result == null) {
            throw new NoConverterException(fromClass, toClass);
        } else {
            return result;
        }
    }

    static {
        // Register default converters
    }
}
