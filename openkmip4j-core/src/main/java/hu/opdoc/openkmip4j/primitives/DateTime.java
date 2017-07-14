package hu.opdoc.openkmip4j.primitives;

import java.time.ZonedDateTime;

/**
 * Created by peter on 2017.06.11..
 */
public class DateTime extends SimpleValuePrimitive<ZonedDateTime> {

    public static final Type TYPE = Type.DateTime;

    private ZonedDateTime value;

    public DateTime(final Tag tag, final ZonedDateTime value) {
        super(tag, Type.DateTime, ZonedDateTime.class);
    }

    @Override
    public ZonedDateTime getValue() {
        return value;
    }

    @Override
    public void setValue(ZonedDateTime value) {
        this.value = value;
    }

    @Override
    public boolean isEmpty() {
        return value == null;
    }

    @Override
    protected void calculateLength() {
        setLength(type.getLength());
    }
}
