package hu.opdoc.openkmip4j.primitives;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by peter on 2017.06.11.
 */
public class Integer extends SimpleValuePrimitive<java.lang.Integer> {

    public static final Type TYPE = Type.Integer;

    private static final Pattern ONLY_NUMERIC_PATTERN = Pattern.compile("^\\d+$");
    private static final String HEX_VALUE_PREFIX = "0x";

    private java.lang.Integer value = 0;
    private final List<String> maskConstants = new ArrayList<>();

    public Integer() {
        this(null);
    }

    public Integer(final Tag tag) {
        super(tag, TYPE, java.lang.Integer.class);
    }

    public Integer(final Tag tag, final java.lang.Integer value) {
        super(tag, TYPE, java.lang.Integer.class);
        setValue(value);
    }

    public Integer(final Tag tag, final String constant) {
        super(tag, TYPE, java.lang.Integer.class);
        this.maskConstants.add(constant);
        processNumericMasks();
    }

    public Integer(final Tag tag, final Collection<String> constants) {
        super(tag, TYPE, java.lang.Integer.class);
        this.maskConstants.addAll(constants);
        processNumericMasks();
    }

    @Override
    public java.lang.Integer getValue() {
        if (!this.maskConstants.isEmpty()) {
            throw new IllegalStateException(String.format("The Integer value contains constant names that must be resolved: %s.", maskConstants.toString()));
        }

        return value;
    }

    public java.lang.Integer getValue(final ConstantResolver<java.lang.Integer> resolver) {
        resolveConstants(resolver);
        return value;
    }

    @Override
    public void setValue(java.lang.Integer value) {
        this.value = value;
    }

    public void resolveConstants(final ConstantResolver<java.lang.Integer> resolver) {
        if (!this.maskConstants.isEmpty()) {
            value = maskConstants.stream()
                    .map(resolver::resolve)
                    .reduce(value, (x, y) -> x ^ y);
            maskConstants.clear();
        }
    }

    @Override
    public boolean isEmpty() {
        return value == null && maskConstants.isEmpty();
    }

    @Override
    protected void calculateLength() {
        setLength(type.getLength());
    }

    private void processNumericMasks() {
        value = 0;
        final Iterator<String> iterator = maskConstants.iterator();
        while (iterator.hasNext()) {
            final String constant = iterator.next();
            final java.lang.Integer thisValue;

            if (constant.startsWith(HEX_VALUE_PREFIX)) {
                thisValue = java.lang.Integer.parseInt(constant.substring(HEX_VALUE_PREFIX.length()), 16);
            } else if (ONLY_NUMERIC_PATTERN.matcher(constant).matches()) {
                thisValue = java.lang.Integer.parseInt(constant);
            } else {
                continue;
            }

            value ^= thisValue;
            iterator.remove();
        }
    }
}
