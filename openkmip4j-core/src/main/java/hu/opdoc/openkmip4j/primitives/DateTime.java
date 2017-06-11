package hu.opdoc.openkmip4j.primitives;

import java.time.LocalDateTime;

/**
 * Created by peter on 2017.06.11..
 */
public class DateTime extends Primitive {

    private final LocalDateTime value;

    public DateTime(final TagValue tag, final LocalDateTime value) {
        super(tag, Type.DateTime);
        this.value = value == null ? LocalDateTime.MIN : value;
    }

    public LocalDateTime getValue() {
        return value;
    }
}
