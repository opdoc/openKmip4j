package hu.opdoc.openkmip4j.representation.ttlv;

import hu.opdoc.openkmip4j.KmipException;
import hu.opdoc.openkmip4j.KmipRuntimeException;
import hu.opdoc.openkmip4j.primitives.*;
import hu.opdoc.openkmip4j.primitives.Boolean;
import hu.opdoc.openkmip4j.primitives.Integer;
import hu.opdoc.openkmip4j.representation.KmipTypeMismatchesPrimitiveClassException;
import hu.opdoc.openkmip4j.utils.GuardedByteArray;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * Created by peter on 2017.07.12..
 */
abstract class TtlvWriter {

    private static final byte[] PADDING = new byte[8];

    static {
        Arrays.fill(PADDING, (byte)0);
    }

    private TtlvWriter() {}

    static long writeRecursive(Primitive primitive, OutputStream output) throws IOException, KmipException {
        final Type type = primitive.getType();
        final long valueLength = primitive.getLength();

        // Header (TTL)
        final TtlvHeader header = new TtlvHeader(primitive.getTag(), type, valueLength);
        final byte[] headerBuffer = new byte[TtlvHeader.HEADER_LENGTH.intValue()];
        final long headerLength = header.write(headerBuffer);
        output.write(headerBuffer);

        // Value (V)
        final long writtenBytes;
        switch (type) {
            case Integer: {
                if (!(primitive instanceof Integer)) {
                    throw new KmipTypeMismatchesPrimitiveClassException(primitive);
                }
                writtenBytes = writeSignedInteger(((Integer) primitive).getValue(), output);
                break;
            }

            case Enumeration:
            case Interval: {
                final Long value;
                if (primitive instanceof Enumeration) {
                    value = ((Enumeration)primitive).getNumericValue();
                } else if (primitive instanceof Interval) {
                    value = ((Interval)primitive).getNumericValue();
                } else {
                    throw new KmipTypeMismatchesPrimitiveClassException(primitive);
                }

                writtenBytes = writeUnsignedInteger(value, output);
                break;
            }

            case DateTime:
            case LongInteger: {
                final Long value;
                if (primitive instanceof LongInteger) {
                    value = ((LongInteger)primitive).getValue();
                } else if (primitive instanceof DateTime) {
                    value = ((DateTime)primitive).getValue().toEpochSecond();
                } else {
                    throw new KmipTypeMismatchesPrimitiveClassException(primitive);
                }
                writtenBytes = writeSignedLongInteger(value, output);
                break;
            }

            case BigInteger: {
                if (!(primitive instanceof BigInteger)) {
                    throw new KmipTypeMismatchesPrimitiveClassException(primitive);
                }
                try (final GuardedByteArray guard = ((BigInteger)primitive).getByteArray()) {
                    output.write(guard.getValue());
                    writtenBytes = guard.getValue().length;
                }
                break;
            }

            case Boolean: {
                if (!(primitive instanceof Boolean)) {
                    throw new KmipTypeMismatchesPrimitiveClassException(primitive);
                }
                writtenBytes = writeBoolean(((Boolean) primitive).getValue(), output);
                break;
            }

            case TextString: {
                if (!(primitive instanceof TextString)) {
                    throw new KmipTypeMismatchesPrimitiveClassException(primitive);
                }
                try (final GuardedByteArray guardedEncodedBytes = ((TextString)primitive).getEncoded()) {
                    output.write(guardedEncodedBytes.getValue());
                    writtenBytes = guardedEncodedBytes.getValue().length;
                }
                break;
            }

            case ByteString: {
                if (!(primitive instanceof ByteString)) {
                    throw new KmipTypeMismatchesPrimitiveClassException(primitive);
                }
                try (final GuardedByteArray guardedEncodedBytes = ((ByteString)primitive).getValue()) {
                    output.write(guardedEncodedBytes.getValue());
                    writtenBytes = guardedEncodedBytes.getValue().length;
                }
                break;
            }

            case Structure: {
                if (!(primitive instanceof Structure)) {
                    throw new KmipTypeMismatchesPrimitiveClassException(primitive);
                }
                long writtenSoFar = 0;
                for (final Primitive subPrimitive : ((Structure)primitive).getValue()) {
                    writtenSoFar += writeRecursive(subPrimitive, output);
                }
                if (writtenSoFar < valueLength) {
                    if (valueLength - writtenSoFar >= 8) {
                        throw new KmipRuntimeException(
                                String.format(
                                        "Wrong calculated length (%d) of a Structure that contains elements of only %d bytes.",
                                        valueLength,
                                        writtenSoFar
                                )
                        );
                    }
                    output.write(PADDING, 0, Long.valueOf(valueLength - writtenSoFar).intValue());
                }
                writtenBytes = valueLength;
                break;
            }

            default: {
                throw new KmipRuntimeException(String.format("Unknown primitive type: %s", type));
            }
        }

        if (writtenBytes != valueLength) {
            throw new KmipRuntimeException(
                    String.format(
                            "Mismatching value (length of %d) and value length (%d) in the header of a(n) %s primitive.",
                            writtenBytes,
                            valueLength,
                            primitive.getType()
                    )
            );
        }

        return headerLength + valueLength;
    }

    private static long writeSignedInteger(java.lang.Integer value, OutputStream output) throws IOException {
        output.write(ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putInt(value).array());
        return 4;
    }

    private static long writeUnsignedInteger(Long value, OutputStream output) throws IOException {
        final byte[] valueBuffer = new byte[4];
        for (int i = 3; i >= 0; i--) {
            valueBuffer[i] = (byte)(value & 0xFF);
            value >>= 8;
        }
        output.write(valueBuffer);
        return 4;
    }

    private static long writeSignedLongInteger(Long value, OutputStream output) throws IOException {
        output.write(ByteBuffer.allocate(8).order(ByteOrder.BIG_ENDIAN).putLong(value).array());
        return 8;
    }

    private static long writeBoolean(boolean value, OutputStream output) throws IOException {
        final byte[] valueBuffer = new byte[8];
        Arrays.fill(valueBuffer, (byte)0x00);
        valueBuffer[7] = value ? (byte)0x01 : (byte)0x00;

        output.write(valueBuffer);
        return 8;
    }
}
