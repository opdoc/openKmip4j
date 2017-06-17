package hu.opdoc.openkmip4j.representation;

import hu.opdoc.openkmip4j.KmipException;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by peter on 2017.06.17..
 */
public class KmipNotRecognizedRepresentationException extends KmipException {

    public KmipNotRecognizedRepresentationException() {
        super("Unrecognized message representation. Supported ones: " + String.join(
                ", ",
                Arrays.asList(Representation.values()).stream()
                        .map(Representation::name)
                        .collect(Collectors.toList())
        ));
    }
}
