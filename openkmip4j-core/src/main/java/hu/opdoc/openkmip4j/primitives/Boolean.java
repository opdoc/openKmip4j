package hu.opdoc.openkmip4j.primitives;

/**
 * Created by peter on 2017.06.11..
 */
public class Boolean extends Primitive {

    public static final Type TYPE = Type.Boolean;

    private final boolean value;

    public Boolean(final Tag tag, final boolean value) {
        super(tag, Type.Boolean);
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }
}
