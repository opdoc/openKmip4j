package hu.opdoc.openkmip4j.primitives;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by peter on 2017.06.11..
 */
public class Integer extends Primitive {

    private static final Pattern ONLY_NUMERIC_PATTERN = Pattern.compile("^\\d+$");
    private static final String HEX_VALUE_PREFIX = "0x";

    private java.lang.Integer value = 0;
    private final List<String> maskConstants;

    public Integer(final TagValue tag, final java.lang.Integer value) {
        super(tag, Type.Integer);
        this.value = value == null ? 0 : value;
        this.maskConstants = Collections.emptyList();
    }

    public Integer(final TagValue tag, final String constant) {
        super(tag, Type.Integer);
        this.maskConstants = new ArrayList<>(1);
        this.maskConstants.add(constant);
        processNumericMasks();
    }

    public Integer(final TagValue tag, final Collection<String> constants) {
        super(tag, Type.Integer);
        this.maskConstants = new ArrayList<>(constants);
        processNumericMasks();
    }

    public java.lang.Integer getValue() {
        if (!this.maskConstants.isEmpty()) {
            throw new IllegalStateException(String.format("The Integer value contains constant names that must be resolved: %s.", maskConstants.toString()));
        }

        return value;
    }

    public java.lang.Integer getValue(final ConstantResolver<java.lang.Integer> resolver) {
        if (!this.maskConstants.isEmpty()) {
            value = maskConstants.stream()
                    .map(resolver::resolve)
                    .reduce(value, (x, y) -> x ^ y);
            maskConstants.clear();
        }

        return value;
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
