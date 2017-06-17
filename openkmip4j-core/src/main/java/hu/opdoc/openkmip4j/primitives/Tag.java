package hu.opdoc.openkmip4j.primitives;

import hu.opdoc.openkmip4j.Version;

/**
 * Created by peter on 2017.06.17..
 */
public interface Tag {
    String name();

    java.lang.Integer getValue();

    Version getIntroducedInVersion();

    Version getDeprecatedInVersion();

    boolean isValidInVersion(final Version version);

    static Tag valueOf(final java.lang.Integer value) {
        return valueOf(value, null);
    }

    static Tag valueOf(final String name) {
        return valueOf(null, name);
    }

    static Tag valueOf(final java.lang.Integer value, final String name) {
        if (value == null && name == null) {
            throw new NullPointerException("Either the name or the numeric value of the Tag must be provided.");
        }

        final StandardTag standardTag = value == null ? StandardTag.valueOf(name) : StandardTag.valueOf(value);

        if (StandardTag.NonStandardExtensionTag == standardTag) {
            return new NonStandardExtensionTag(value, name);
        }

        return standardTag;
    }
}
