package hu.opdoc.openkmip4j.representation;

import hu.opdoc.openkmip4j.KmipException;
import hu.opdoc.openkmip4j.primitives.Primitive;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by peter on 2017.06.14..
 */
public interface KmipSerializer {
    /**
     * Reads a @{@link Primitive} from an @{@link InputStream}.
     *
     * @param input The @{@link InputStream} to read.
     * @return  The @{@link Primitive} (mostly a @{@link hu.opdoc.openkmip4j.primitives.Structure}) that has been read.
     * @throws IOException  In case of a reading error.
     * @throws KmipException    In case of a KMIP parsing error.
     */
    Primitive readPrimitive(final InputStream input) throws IOException, KmipException;

    /**
     * Writes a @{@link Primitive} onto an @{@link OutputStream}.
     *
     * @param primitive The @{@link Primitive} (mostly a Message's @{@link hu.opdoc.openkmip4j.primitives.Structure}) to be serialized.
     * @param output    The @{@link OutputStream} to write the serialized representation to.
     * @throws IOException  In case of a writing error.
     * @throws KmipException    In case of a @{@link Primitive} cannot be represented.
     */
    void writePrimitive(final Primitive primitive, final OutputStream output) throws IOException, KmipException;
}
