package hu.opdoc.openkmip4j.utils.converters;

import hu.opdoc.openkmip4j.KmipInvalidSettingException;

/**
 * Created by peter on 2017.07.14.
 */
public abstract class TypeConverter<A, B> {

    private final Class<A> classA;
    private final Class<B> classB;

    protected TypeConverter(Class<A> classA, Class<B> classB) {
        if (classA == null || classB == null) {
            throw new KmipInvalidSettingException(String.format("Both A and B classes must be specified for converter %s.", getClass().getCanonicalName()));
        }

        this.classA = classA;
        this.classB = classB;
    }

    public abstract ConverterFunction<A, B> fromAtoB();

    public abstract ConverterFunction<B, A> fromBtoA();

    public Class<A> getClassA() {
        return classA;
    }

    public Class<B> getClassB() {
        return classB;
    }
}
