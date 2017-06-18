package hu.opdoc.openkmip4j.representation.ttlv;

import hu.opdoc.openkmip4j.primitives.Tag;
import hu.opdoc.openkmip4j.primitives.Type;

/**
 * Created by peter on 2017.06.18..
 */
public class TtlvHeader {

    public static final Long HEADER_LENGTH = 8l;

    private Tag tag;
    private Type type;
    private Long length;

    public TtlvHeader(Tag tag, Type type, Long length) {
        this.tag = tag;
        this.type = type;
        this.length = length;
    }

    public TtlvHeader(byte[] bytes) {
        if (bytes == null) {
            throw new NullPointerException("Buffer must be specified.");
        }
        if (bytes.length < HEADER_LENGTH) {
            throw new IndexOutOfBoundsException(String.format("The buffer must be at least %d bytes long.", HEADER_LENGTH));
        }

        final Integer tagValue = 0x10000 * bytes[0] + 0x100 * bytes[1] + bytes[2];
        final byte typeValue = bytes[3];

        this.tag = Tag.valueOf(tagValue);
        this.type = Type.valueOf(typeValue);
        this.length = Long.valueOf(0x1000000 * bytes[4] + 0x10000 * bytes[5] + 0x100 * bytes[6] + bytes[7]);
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public long write(byte[] buffer) {
        return write(buffer, 0);
    }

    public long write(byte[] buffer, int offset) {
        if (buffer.length < offset + HEADER_LENGTH) {
            throw new IndexOutOfBoundsException(String.format("The buffer must be at least %d bytes longer than the offset position.", HEADER_LENGTH));
        }

        final int tagValue = tag == null ? 0 : tag.getValue();
        final long lengthValue = length == null ? 0 : length.longValue();

        buffer[offset + 0] = (byte) ((tagValue >> 16) & 0xFF);
        buffer[offset + 1] = (byte) ((tagValue >> 8) & 0xFF);
        buffer[offset + 2] = (byte) (tagValue & 0xFF);
        buffer[offset + 3] = type.getValue();
        buffer[offset + 4] = (byte) ((lengthValue >> 24) & 0xFF);
        buffer[offset + 5] = (byte) ((lengthValue >> 16) & 0xFF);
        buffer[offset + 6] = (byte) ((lengthValue >> 8) & 0xFF);
        buffer[offset + 7] = (byte) (lengthValue & 0xFF);

        return HEADER_LENGTH;
    }
}
