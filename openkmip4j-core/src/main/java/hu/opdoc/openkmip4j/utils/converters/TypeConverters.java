package hu.opdoc.openkmip4j.utils.converters;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by peter on 2017.07.14.
 */
public abstract class TypeConverters {

    private static final ConcurrentHashMap<Class, ConcurrentHashMap<Class, ConverterFunction>> converterCache = new ConcurrentHashMap<>();
    private static final Set<Converter<?,?>> converters = new HashSet<>(100);

    private TypeConverters() {}

    public static <F, T> void registerConverter(final Class<F> fromClass, final Class<T> toClass, final ConverterFunction<F, T> converter) {
        converters.add(new Converter(fromClass, toClass, converter));
    }

    public static <A, B> void registerConverter(final TypeConverter<A, B> converter) {
        final Class<A> classA = converter.getClassA();
        final Class<B> classB = converter.getClassB();

        converterCache.computeIfAbsent(classA, k -> new ConcurrentHashMap<>())
                .putIfAbsent(classB, converter.fromAtoB());
        converterCache.computeIfAbsent(classB, k -> new ConcurrentHashMap<>())
                .putIfAbsent(classA, converter.fromBtoA());
    }

    public static <F, T> ConverterFunction<? super F, ? extends T> getConverter(final Class<F> fromClass, final Class<T> toClass) throws NoConverterException {
        if (fromClass.equals(toClass)) {
            return f -> (T)f;
        } else {
            ConverterFunction<? super F, ? extends T> result = cacheGet(fromClass, toClass);
            if (result != null) {
                return result;
            }

            result = converters.stream()
                    .filter(converter -> converter.isCompatible(fromClass, toClass))
                    .map(converter -> converter.rank(fromClass, toClass))
                    .sorted()
                    .map(converter -> converter.converter.converter)
                    .findFirst()
                    .orElse(null);

            if (result == null) {
                throw new NoConverterException(fromClass, toClass);
            }

            cachePut(fromClass, toClass, result);
            return result;
        }
    }

    static {
        // Register default converters
    }

    private static <F, T> void cachePut(final Class<F> fromClass, final Class<T> toClass, final ConverterFunction<? super F, ? extends T> converter) {
        converterCache.computeIfAbsent(fromClass, k -> new ConcurrentHashMap<>())
                .putIfAbsent(toClass, converter);
    }

    private static <F, T> ConverterFunction<? super F, ? extends T> cacheGet(final Class<F> fromClass, final Class<T> toClass) {
        final Map<Class, ConverterFunction> toMap = converterCache.get(fromClass);
        if (toMap != null) {
            return toMap.get(toClass);
        }
        return null;
    }

    private static class Converter<F, T> {

        private final Class<F> fromClass;
        private final Class<T> toClass;
        private final ConverterFunction<F, T> converter;

        private Converter(Class<F> fromClass, Class<T> toClass, ConverterFunction<F, T> converter) {
            this.fromClass = fromClass;
            this.toClass = toClass;
            this.converter = converter;
        }

        private boolean isCompatible(final Class<?> expectedFromClass, final Class<?> expectedToClass) {
            return expectedFromClass.isAssignableFrom(fromClass) && toClass.isAssignableFrom(expectedToClass);
        }

        private <EF, ET> RankedConverter<EF, ET> rank(final Class<EF> expectedFromClass, final Class<ET> expectedToClass) {
            if (!isCompatible(expectedFromClass, expectedToClass)) {
                return null;
            } else {
                return new RankedConverter<>((Converter<? super EF, ? extends ET>)converter, expectedFromClass, expectedToClass);
            }
        }
    }

    private static class RankedConverter<F, T> implements Comparable<RankedConverter> {

        private final Converter<? super F, ? extends T> converter;
        private final int fromRank;
        private final int toRank;

        private RankedConverter(final Converter<? super F, ? extends T> converter, final Class<F> expectedFromClass, final Class<T> expectedToClass) {
            this.converter = converter;
            this.fromRank = inheritanceDistance(converter.fromClass, expectedFromClass);
            this.toRank = inheritanceDistance(expectedToClass, converter.toClass);
        }

        @Override
        public int compareTo(RankedConverter other) {
            int result = Integer.compare(fromRank, other.fromRank);
            if (result == 0) {
                result = Integer.compare(toRank, other.toRank);
            }
            return result;
        }
    }

    private static <T1, T2 extends T1> int inheritanceDistance(final Class<T1> superClass, final Class<T2> childClass) {
        int distance = 0;
        for (Class clazz = childClass; !superClass.equals(clazz); clazz = clazz.getSuperclass()) {
            if (clazz == null) {
                // This should not happen due to the relation between T1 and T2.
                throw new IllegalArgumentException(String.format("The childClass (%s) must be an descendant of the superClass (%s).", childClass.getCanonicalName(), superClass.getCanonicalName()));
            }
            distance++;
        }
        return distance;
    }
}
