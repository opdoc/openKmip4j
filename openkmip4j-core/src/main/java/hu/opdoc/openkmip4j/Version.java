package hu.opdoc.openkmip4j;

/**
 * Created by peter on 2017.06.17..
 */
public enum Version {
    KMIP_1_0(1, 0),
    KMIP_1_1(1, 1),
    KMIP_1_2(1, 2),
    KMIP_1_3(1, 3);

    public final int major;
    public final int minor;

    Version(int major, int minor) {
        this.major = major;
        this.minor = minor;
    }
}
