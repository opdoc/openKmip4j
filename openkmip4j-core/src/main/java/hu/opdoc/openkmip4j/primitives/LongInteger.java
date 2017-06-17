package hu.opdoc.openkmip4j.primitives;

/**
 * Created by peter on 2017.06.11..
 */
public class LongInteger extends Primitive {

    private final Long value;

    public LongInteger(final Tag tag, final Long value) {
        super(tag, Type.LongInteger);
        this.value = value;
    }

    public Long getValue() {
        return value;
    }
}
