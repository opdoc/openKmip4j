package hu.opdoc.openkmip4j.utils;

import java.io.Closeable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * <p>Guards an array of bytes so that it's content will be overwritten with
 * 0x00 when @destroy or @close is called or this guard object is destroyed.</p>
 *
 * <p><b>IMPORTANT:</b> don't forget to call @destroy or use this object
 * otherwise after the processing of the wrapped array otherwise the guard
 * may be destroyed too early for optimization not being used any more!</p>
 *
 * <p>Created by peter on 2017.07.11.</p>
 */
public class GuardedByteArray implements Closeable {

    private final byte[] value;

    public GuardedByteArray(byte[] value) {
        this.value = value == null ? new byte[0] : value;
    }

    public GuardedByteArray(final ByteBuffer buffer) {
        this(buffer.array());
    }

    public byte[] getValue() {
        return value;
    }

    public ByteBuffer getByteBuffer() {
        return ByteBuffer.wrap(value).order(ByteOrder.BIG_ENDIAN);
    }

    public void destroy() {
        Arrays.fill(value, (byte)0);
    }

    @Override
    public void close() {
        destroy();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        destroy();
    }
}
