package hu.opdoc.openkmip4j.v1.object.base;

import hu.opdoc.openkmip4j.KmipObject;
import hu.opdoc.openkmip4j.message.KmipMessageFormatException;
import hu.opdoc.openkmip4j.primitives.*;

import java.lang.Integer;

/**
 * Created by peter on 2017.07.13..
 */
public abstract class Attribute<T extends Primitive> extends KmipObject {

    protected final String name;
    protected final Integer index;
    protected final T valuePrimitive;

    protected Attribute(final Tag tag, final String name, final Integer index, final T valuePrimitive) {
        super(tag);

        this.name = name;
        this.index = index;
        this.valuePrimitive = valuePrimitive;
    }

    protected Attribute(final Structure attributeStructure, final Tag expectedTag, final Class<T> valueClass) {
        super(expectedTag);

        if (!expectedTag.equals(attributeStructure.getTag())) {
            throw new KmipMessageFormatException(String.format("%s tag was expected but %s was found.", expectedTag, attributeStructure.getTag()));
        }

        String readName = null;
        Integer readIndex = null;
        T readPrimitive = null;

        for (final Primitive element : attributeStructure.getValue()) {
            final Tag elementTag = element.getTag();
            final StandardTag standardElementTag;
            if (elementTag instanceof StandardTag) {
                standardElementTag = (StandardTag)elementTag;
            } else {
                throw new KmipMessageFormatException(String.format("Non standard tag found in an Attribute structure: %s.", elementTag));
            }

            switch (standardElementTag) {
                case AttributeName:
                    assertNotRepeated(readName, standardElementTag.name());
                    readName = new String(assertTextString(element).getValue().getValue());
                    break;

                case AttributeIndex:
                    assertNotRepeated(readIndex, standardElementTag.name());
                    readIndex = assertInteger(element).getValue();
                    break;

                case AttributeValue:
                    assertNotRepeated(readPrimitive, standardElementTag.name());
                    readPrimitive = assertPrimitive(element, valueClass);
                    break;

                default:
                    throw new KmipMessageFormatException(String.format("Unrecognized Tag (%s) in an Attribute structure.", standardElementTag));
            }
        }

        if (readName == null) {
            throw new KmipMessageFormatException("No AttributeName tag found in the Attribute structure.");
        }
        if (readPrimitive == null) {
            throw new KmipMessageFormatException("No AttributeValue tag found in the Attribute structure.");
        }

        this.name = readName;
        this.index = readIndex;
        this.valuePrimitive = readPrimitive;
    }

    public String getName() {
        return name;
    }

    public Integer getIndex() {
        return index;
    }

    @Override
    public Primitive toPrimitive() {
        valuePrimitive.setTag(StandardTag.AttributeValue);
        final Structure.StructureBuilder builder = Structure.newInstance(tag)
                .add(new TextString(StandardTag.AttributeName, name))
                .add(valuePrimitive);

        if (index != null) {
            builder.add(new hu.opdoc.openkmip4j.primitives.Integer(StandardTag.AttributeIndex, index));
        }

        return builder.build();
    }
}
