package hu.opdoc.openkmip4j.representation.ttlv;

import hu.opdoc.openkmip4j.KmipException;
import hu.opdoc.openkmip4j.primitives.*;
import hu.opdoc.openkmip4j.representation.KmipSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by peter on 2017.06.18..
 */
public class TtlvSerializer implements KmipSerializer {

    @Override
    public Primitive readPrimitive(InputStream input) throws IOException, KmipException {
        return TtlvReader.readRecursive(input).primitive;
    }

    @Override
    public void writePrimitive(Primitive primitive, OutputStream output) throws IOException, KmipException {
        TtlvWriter.writeRecursive(primitive, output);
    }

    /*
     * Reading section
     */
}
