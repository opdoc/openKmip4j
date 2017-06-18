package hu.opdoc.openkmip4j.representation;

import hu.opdoc.openkmip4j.representation.ttlv.TtlvSerializer;

/**
 * Created by peter on 2017.06.14..
 */
public enum Representation {
    // TODO: populate serializers!
    TTLV(new TtlvSerializer()),
    JSON(null),
    XML(null);

    private final KmipSerializer serializer;

    Representation(KmipSerializer serializer) {
        this.serializer = serializer;
    }

    public KmipSerializer getSerializer() {
        return serializer;
    }
}
