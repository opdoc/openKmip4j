package hu.opdoc.openkmip4j.primitives;

import hu.opdoc.openkmip4j.KmipInvalidSettingException;

/**
 * Created by peter on 2017.06.11.
 */
public abstract class Primitive {

    private Tag tag;
    protected final Type type;
    private Long length;

    protected Primitive(final Tag tag, final Type type, final Long length) {
        if (type == null) {
            throw new NullPointerException("The type of a Primitive must not be null.");
        }
        if (length != null && type.getLength() != null && !length.equals(type.getLength())) {
            throw new KmipInvalidSettingException(
                    String.format(
                            "The data Length of a %s primitive must be %d, the set value of %d is not allowed.",
                            type,
                            type.getLength(),
                            length
                    )
            );
        }

        this.tag = tag;
        this.type = type;
        this.length = (length == null) ? type.getLength() : length;
    }

    protected Primitive(final Tag tag, final Type type) {
        this(tag, type, null);
    }

    protected Primitive(final Type type) {
        this(null, type);
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public Type getType() {
        return type;
    }

    public Long getLength() {
        return length;
    }

    public abstract boolean isEmpty();

    protected void setLength(long length) {
        if (type.getLength() != null && !type.getLength().equals(length)) {
            throw new KmipInvalidSettingException(String.format("The length of a %s must be %d but %d was set.", type, type.getLength(), length));
        }
        this.length = length;
    }

    protected abstract void calculateLength();

    protected void validate() {
        if (type.getLength() == null) {
            calculateLength();
        }
        if (tag == null) {
            throw new KmipInvalidSettingException(String.format("The Tag value of the %s primitive hasn't been set.", type));
        }
        if (length == null) {
            throw new KmipInvalidSettingException(String.format("The Length of the %s primitive cannot be calculated.", type));
        }
        if (isEmpty()) {
            throw new KmipInvalidSettingException(String.format("No value has been set on a %s primitive.", type));
        }
    }
}
