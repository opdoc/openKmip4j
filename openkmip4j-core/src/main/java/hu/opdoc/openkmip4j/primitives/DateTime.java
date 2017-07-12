package hu.opdoc.openkmip4j.primitives;

import java.time.ZonedDateTime;

/**
 * Created by peter on 2017.06.11..
 */
public class DateTime extends Primitive {

    private final ZonedDateTime value;

    public DateTime(final Tag tag, final ZonedDateTime value) {
        super(tag, Type.DateTime);
        if (value == null) {
            throw new NullPointerException("Time information must be specified.");
        }
        this.value = value;
    }

    public ZonedDateTime getValue() {
        return value;
    }
}
