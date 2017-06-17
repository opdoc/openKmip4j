package hu.opdoc.openkmip4j.representation;

import hu.opdoc.openkmip4j.KmipException;
import hu.opdoc.openkmip4j.primitives.Primitive;
import hu.opdoc.openkmip4j.primitives.StandardTag;
import hu.opdoc.openkmip4j.primitives.Tag;
import hu.opdoc.openkmip4j.primitives.Type;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by peter on 2017.06.14..
 */
public class ParsedMessage {

    private static final int PREREAD_LENGTH = 8;
    private static final String whitespaceCharactersString = " \t\r\n";
    private static final Set<Byte> whitespaceCharacters = new HashSet<>(whitespaceCharactersString.length());
    private static final Byte firstJsonCharacter = "{".getBytes(StandardCharsets.ISO_8859_1)[0];
    private static final Byte firstXmlCharacter = "<".getBytes(StandardCharsets.ISO_8859_1)[0];

    private final Representation representation;
    private final Primitive message;

    public ParsedMessage(InputStream input) throws IOException, KmipException {
        if (!input.markSupported()) {
            input = new BufferedInputStream(input);
        }

        this.representation = identifyRepresentation(input);
        final KmipSerializer serializer = representation.getSerializer();
        this.message = serializer.readPrimitive(input);
    }

    public Representation getRepresentation() {
        return representation;
    }

    public Primitive getMessage() {
        return message;
    }

    private static Representation identifyRepresentation(InputStream input) throws IOException, KmipNotRecognizedRepresentationException {
        if (!input.markSupported()) {
            throw new IllegalArgumentException(String.format("Mark-supporting InputStream is required! Received: %s", input.getClass().getCanonicalName()));
        }

        Representation result = null;
        boolean canBeTTLV = true;
        boolean canBeJSON = true;
        boolean canBeXML  = true;
        final byte[] buffer = new byte[PREREAD_LENGTH];

        while (true) {
            input.mark(PREREAD_LENGTH);
            input.read(buffer);

            if (canBeTTLV && isTTLV(buffer)) {
                result = Representation.TTLV;
                break;
            }

            final Boolean isJSON = canBeJSON ? isJSON(buffer) : false;
            final Boolean isXML = canBeXML ? isXML(buffer) : false;

            if (isJSON != null) {
                if (isJSON) {
                    result = Representation.JSON;
                    break;
                } else {
                    canBeJSON = false;
                }
            }

            if (isXML != null) {
                if (isXML) {
                    result = Representation.XML;
                    break;
                } else {
                    canBeXML = false;
                }
            }

            canBeTTLV = false;
            if (!canBeTTLV && !canBeJSON && !canBeXML) {
                input.reset();
                throw new KmipNotRecognizedRepresentationException();
            }
        }

        input.reset();
        return result;
    }

    private static boolean isTTLV(final byte[] buffer) {
        final Integer tagValue = 0x10000 * buffer[0] + 0x100 * buffer[1] + buffer[2];
        final byte typeValue = buffer[3];

        try {
            // The type must be identifiable, and should be a Structure as any Message is a Structure.
            final Type type = Type.fromValue(typeValue);
            if (Type.Structure != type) {
                // TODO: logger.trace("Not a Structure type (<type>), not a TTLV.")
                return false;
            }

            // The Tag must be identifiable and should be RequestMessage or ResponseMessage.
            final Tag tag = Tag.valueOf(tagValue);
            if (StandardTag.RequestMessage != tag && StandardTag.ResponseMessage != tag) {
                // TODO: logger.trace("Not a Request- or ResponseMessage (<tag>), not TTLV.")
                return false;
            }
        } catch (IllegalArgumentException ex) {
            // TODO: logger.trace("Not a TTLV.", ex)
            return false;
        }

        return true;
    }

    private static Boolean isJSON(final byte[] buffer) {
        for (Byte value : buffer) {
            if (whitespaceCharacters.contains(value)) {
                continue;
            }
            // TODO: logger.trace("Non-whitespace character: <value>")
            return firstJsonCharacter.equals(value);
        }

        // TODO: logger.trace("Only whitespace so far...");
        return null;
    }

    private static Boolean isXML(final byte[] buffer) {
        for (Byte value : buffer) {
            if (whitespaceCharacters.contains(value)) {
                continue;
            }
            // TODO: logger.trace("Non-whitespace character: <value>")
            return firstXmlCharacter.equals(value);
        }

        // TODO: logger.trace("Only whitespace so far...");
        return null;
    }

    static {
        for (Byte charCode : whitespaceCharactersString.getBytes(StandardCharsets.ISO_8859_1)) {
            whitespaceCharacters.add(charCode);
        }
    }
}
