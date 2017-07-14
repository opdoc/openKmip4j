package hu.opdoc.openkmip4j.primitives;

/**
 * Created by peter on 2017.06.11.
 */
public class Boolean extends SimpleValuePrimitive<java.lang.Boolean> {

    public static final Type TYPE = Type.Boolean;

    private java.lang.Boolean value;

    public Boolean() {
        this(null);
    }

    public Boolean(final Tag tag) {
        this(tag, null);
    }

    public Boolean(final Tag tag, final java.lang.Boolean value) {
        super(tag, TYPE, java.lang.Boolean.class);
        this.value = value;
    }

    @Override
    public java.lang.Boolean getValue() {
        return value;
    }

    @Override
    public void setValue(java.lang.Boolean value) {

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
