package hu.opdoc.openkmip4j.primitives;

import java.nio.charset.StandardCharsets;

/**
 * Created by peter on 2017.06.11..
 */
public class TextString extends Primitive {

    private final String value;

    public TextString(final TagValue tag, final String value) {
        super(tag, Type.TextString, value == null ? 0 : Long.valueOf(value.getBytes(StandardCharsets.UTF_8).length));
        this.value = value == null ? "" : value;
    }

    public String getValue() {
        return value;
    }
}
