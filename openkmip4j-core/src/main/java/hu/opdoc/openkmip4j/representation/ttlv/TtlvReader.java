package hu.opdoc.openkmip4j.representation.ttlv;

import hu.opdoc.openkmip4j.KmipException;
import hu.opdoc.openkmip4j.KmipRuntimeException;
import hu.opdoc.openkmip4j.primitives.*;
import hu.opdoc.openkmip4j.primitives.Boolean;
import hu.opdoc.openkmip4j.primitives.Integer;
import hu.opdoc.openkmip4j.utils.arrays.GuardedByteArray;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Created by peter on 2017.07.12..
 */
abstract class TtlvReader {

    private TtlvReader() {}

    static ReadResult readRecursive(final InputStream input) throws IOException, KmipException {
        final TtlvHeader header = new TtlvHeader(input);
        final Type type = header.getType();
        final Tag tag = header.getTag();

        try (final GuardedByteArray buffer = (type == Type.Structure) ? new GuardedByteArray(new byte[0]) : readBytes(header.getLength().intValue(), input)) {
            final Primitive primitive;
            long readBytes = TtlvHeader.HEADER_LENGTH + buffer.getValue().length;

            switch (type) {
                case Integer: {
                    primitive = new Integer(tag, buffer.getByteBuffer().getInt());
                    break;
                }

                case Enumeration:
                case Interval: {
                    long value = 0;
                    for (int i = 0; i < 4; i++) {
                        value <<= 8;
                        value += buffer.getValue()[i];
                    }
                    if (type == Type.Enumeration) {
                        primitive = new Enumeration(tag, value);
                    } else {
                        primitive = new Interval(tag, value);
                    }
                    break;
                }

                case DateTime:
                case LongInteger: {
                    final long value = buffer.getByteBuffer().getLong();
                    if (type == Type.LongInteger) {
                        primitive = new LongInteger(tag, value);
                    } else {
                        primitive = new DateTime(tag, ZonedDateTime.ofInstant(Instant.ofEpochSecond(value), ZoneId.systemDefault()));
                    }
                    break;
                }

                case BigInteger: {
                    primitive = new BigInteger(tag, buffer);
                    break;
                }

                case Boolean: {
                    final byte significantByte = buffer.getValue()[7];
                    if (significantByte < 0 || significantByte > 1) {
                        final byte[] bytes = buffer.getValue();
                        throw new UnsupportedEncodingException(
                                String.format(
                                        "Invalid Boolean primitive value found: %H%H%H%H%H%H%H%H.",
                                        bytes[0],
                                        bytes[1],
                                        bytes[2],
                                        bytes[3],
                                        bytes[4],
                                        bytes[5],
                                        bytes[6],
                                        bytes[7]
                                )
                        );
                    }
                    primitive = new Boolean(tag, significantByte > 0);
                    break;
                }

                case TextString: {
                    primitive = new TextString(tag, buffer);
                    break;
                }

                case ByteString: {
                    primitive = new ByteString(tag, buffer);
                    break;
                }

                case Structure: {
                    primitive = new Structure(tag);
                    long remainingBytes = header.getLength();
                    while (remainingBytes >= TtlvHeader.HEADER_LENGTH) {
                        final ReadResult readResult = readRecursive(input);
                        remainingBytes -= readResult.readBytes;
                        ((Structure)primitive).getValue().add(readResult.primitive);
                    }
                    if (remainingBytes > 0) {
                        final GuardedByteArray padding = readBytes(Long.valueOf(remainingBytes).intValue(), input);
                        padding.destroy();
                    }
                    readBytes += header.getLength();
                    break;
                }

                default: {
                    throw new KmipRuntimeException(String.format("Unknown primitive type: %s", type));
                }
            }

            return new ReadResult(primitive, readBytes);
        }
    }

    public static class ReadResult {

        public final Primitive primitive;
        public final long readBytes;

        public ReadResult(Primitive primitive, long readBytes) {
            this.primitive = primitive;
            this.readBytes = readBytes;
        }
    }

    private static GuardedByteArray readBytes(final int needed, final InputStream input) throws IOException {
        final GuardedByteArray buffer = new GuardedByteArray(new byte[needed]);
        final int reallyRead = input.read(buffer.getValue());
        if (reallyRead < needed) {
            buffer.destroy();
            throw new EOFException(String.format("Further %d bytes must have been read but only %d available.", needed, reallyRead));
        }
        return buffer;
    }
}
