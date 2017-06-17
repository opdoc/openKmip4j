package hu.opdoc.openkmip4j.representation;

/**
 * Created by peter on 2017.06.14..
 */
public enum Representation {
    TTLV(null), JSON(null), XML(null);  // TODO: populate serializers!

    private final KmipSerializer serializer;

    Representation(KmipSerializer serializer) {
        this.serializer = serializer;
    }

    public KmipSerializer getSerializer() {
        return serializer;
    }
}
