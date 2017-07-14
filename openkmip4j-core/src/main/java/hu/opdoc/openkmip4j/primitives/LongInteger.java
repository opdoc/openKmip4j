package hu.opdoc.openkmip4j.primitives;

/**
 * Created by peter on 2017.06.11.
 */
public class LongInteger extends SimpleValuePrimitive<Long> {

    public static final Type TYPE = Type.LongInteger;

    private Long value = null;

    public LongInteger() {
        this(null);
    }

    public LongInteger(final Tag tag) {
        super(tag, Type.LongInteger, Long.class);
    }

    public LongInteger(final Tag tag, final Long value) {
        super(tag, Type.LongInteger, Long.class);
        this.value = value;
    }

    @Override
    public Long getValue() {
        return value;
    }

    @Override
    public void setValue(Long value) {
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
