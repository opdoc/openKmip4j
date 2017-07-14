package hu.opdoc.openkmip4j.utils.arrays;

import java.io.Closeable;
import java.nio.CharBuffer;
import java.util.Arrays;

/**
 * <p>Guards an array of chars so that it's content will be overwritten with
 * ' ' when @destroy or @close is called or this guard object is destroyed.</p>
 *
 * <p><b>IMPORTANT:</b> don't forget to call @destroy or use this object
 * otherwise after the processing of the wrapped array otherwise the guard
 * may be destroyed too early for optimization not being used any more!</p>
 *
 * <p>Created by peter on 2017.07.11.</p>
 */
public class GuardedCharArray implements Closeable {

    private final char[] value;

    public GuardedCharArray(char[] value) {
        this.value = value == null ? new char[0] : value;
    }

    public GuardedCharArray(final CharBuffer buffer) {
        this(buffer.array());
    }

    public char[] getValue() {
        return value;
    }

    public CharBuffer getCharBuffer() {
        return CharBuffer.wrap(value);
    }

    public void destroy() {
        Arrays.fill(value, ' ');
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
