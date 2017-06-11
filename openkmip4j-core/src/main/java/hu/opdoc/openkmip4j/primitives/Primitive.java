package hu.opdoc.openkmip4j.primitives;

/**
 * Created by peter on 2017.06.11..
 */
public abstract class Primitive {

    public final TagValue tag;
    public final Type type;
    public final Long length;

    public Primitive(final TagValue tag, final Type type, final Long length) {
        this.tag = tag;
        this.type = type;
        this.length = length;
    }

    public Primitive(final TagValue tag, final Type type) {
        this(tag, type, type.getLength());
        if (length == null) {
            throw new IllegalArgumentException(String.format("Data length must be explicitly defined for data type %s.", type.name()));
        }
    }

    public TagValue getTag() {
        return tag;
    }

    public Type getType() {
        return type;
    }

    public Long getLength() {
        return length;
    }
}
