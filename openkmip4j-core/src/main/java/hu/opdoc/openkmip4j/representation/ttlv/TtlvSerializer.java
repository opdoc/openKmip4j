package hu.opdoc.openkmip4j.representation.ttlv;

import hu.opdoc.openkmip4j.KmipException;
import hu.opdoc.openkmip4j.primitives.*;
import hu.opdoc.openkmip4j.primitives.Boolean;
import hu.opdoc.openkmip4j.primitives.Integer;
import hu.opdoc.openkmip4j.representation.KmipSerializer;
import hu.opdoc.openkmip4j.representation.KmipTypeMismatchesPrimitiveClassException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

/**
 * Created by peter on 2017.06.18..
 */
public class TtlvSerializer implements KmipSerializer {

    @Override
    public Primitive readPrimitive(InputStream input) throws IOException, KmipException {
        return null;
    }

    @Override
    public void writePrimitive(Primitive primitive, OutputStream output) throws IOException, KmipException {
        writeRecursive(primitive, output);
    }

    private long writeRecursive(Primitive primitive, OutputStream output) throws IOException, KmipException {
        final Type type = primitive.getType();
        final long valueLength = primitive.getLength();

        // Header (TTL)
        final TtlvHeader header = new TtlvHeader(primitive.getTag(), type, valueLength);
        final byte[] headerBuffer = new byte[TtlvHeader.HEADER_LENGTH.intValue()];
        final long headerLength = header.write(headerBuffer);
        output.write(headerBuffer);

        // Value (V)
        switch (type) {
            case Integer:
                if (!(primitive instanceof Integer)) {
                    throw new KmipTypeMismatchesPrimitiveClassException(primitive);
                }
                writeSignedInteger(((Integer) primitive).getValue(), output);
                break;

            case Enumeration:
            case Interval:
                final Long value;
                if (primitive instanceof Enumeration) {
                    value = ((Enumeration)primitive).getNumericValue();
                } else if (primitive instanceof Interval) {
                    value = ((Interval)primitive).getNumericValue();
                } else {
                    throw new KmipTypeMismatchesPrimitiveClassException(primitive);
                }

                writeUnsignedInteger(value, output);
                break;

            case Boolean:
                if (!(primitive instanceof Boolean)) {
                    throw new KmipTypeMismatchesPrimitiveClassException(primitive);
                }
                writeBoolean(((Boolean) primitive).getValue(), output);
                break;

            // TODO: Further Primitives...
        }

        return headerLength + valueLength;
    }

    private long writeSignedInteger(java.lang.Integer value, OutputStream output) throws IOException {
        return writeUnsignedInteger(java.lang.Integer.toUnsignedLong(value), output);
    }

    private long writeUnsignedInteger(Long value, OutputStream output) throws IOException {
        final byte[] valueBuffer = new byte[4];
        valueBuffer[3] = (byte)(value & 0xFF);
        value >>= 8;
        valueBuffer[2] = (byte)(value & 0xFF);
        value >>= 8;
        valueBuffer[1] = (byte)(value & 0xFF);
        value >>= 8;
        valueBuffer[0] = (byte)(value & 0xFF);

        output.write(valueBuffer);
        return 4;
    }

    private long writeBoolean(boolean value, OutputStream output) throws IOException {
        final byte[] valueBuffer = new byte[8];
        Arrays.fill(valueBuffer, (byte)0);
        valueBuffer[7] = value ? (byte)1 : (byte)0;

        output.write(valueBuffer);
        return 8;
    }
}
