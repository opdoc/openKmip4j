package hu.opdoc.openkmip4j.primitives;

import hu.opdoc.openkmip4j.Version;

import java.lang.*;
import java.lang.Integer;

/**
 * Created by peter on 2017.06.17..
 */
public class NonStandardExtensionTag implements Tag {

    private final Integer value;
    private String name;

    public NonStandardExtensionTag(Integer value, String name) {
        if (value == null) {
            throw new NullPointerException(String.format("The Tag value must be specified for Extensions%s.", name == null ? "" : " (" + name + ")"));
        }

        this.name = name;
        this.value = value;
    }

    @Override
    public String name() {
        return name;
    }

    public void name(String name) {
        this.name = name;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public Version getIntroducedInVersion() {
        return Version.KMIP_1_0;
    }

    @Override
    public Version getDeprecatedInVersion() {
        return null;
    }

    @Override
    public boolean isValidInVersion(Version version) {
        return true;
    }

    @Override
    public String toString() {
        return String.format("NonStandardExtensionTag(0x%H, %s)", getValue(), name());
    }
}
