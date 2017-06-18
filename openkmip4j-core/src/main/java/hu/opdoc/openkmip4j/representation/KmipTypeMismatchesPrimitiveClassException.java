package hu.opdoc.openkmip4j.representation;

import hu.opdoc.openkmip4j.KmipRuntimeException;
import hu.opdoc.openkmip4j.primitives.Primitive;

/**
 * Thrown when a {@link hu.opdoc.openkmip4j.primitives.Primitive} class provides a mismatching {@link hu.opdoc.openkmip4j.primitives.Type}.
 * Such thing must not happen.
 *
 * Created by peter on 2017.06.18..
 */
public class KmipTypeMismatchesPrimitiveClassException extends KmipRuntimeException {

    private final Primitive faultyPrimitive;

    public KmipTypeMismatchesPrimitiveClassException(Primitive faultyPrimitive) {
        super(String.format("Primitive class %s provides mismatching type %s.", faultyPrimitive.getClass().getCanonicalName(), faultyPrimitive.getType()));
        this.faultyPrimitive = faultyPrimitive;
    }

    public Primitive getFaultyPrimitive() {
        return faultyPrimitive;
    }
}
