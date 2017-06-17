package hu.opdoc.openkmip4j.primitives;

/**
 * Created by peter on 2017.06.11..
 */
public class BigInteger extends Primitive {

    private final java.math.BigInteger value;

    public BigInteger(final Tag tag, final java.math.BigInteger value) {
        super(tag, Type.BigInteger, value == null ? 0l : ceilDivMinOne(value.bitCount(), 64) * 8);
        this.value = value == null ? java.math.BigInteger.ZERO : value;
    }

    public java.math.BigInteger getValue() {
        return value;
    }

    private static long ceilDivMinOne(final int x, final int y) {
        int result = Math.floorDiv(x, y);
        if (result * y < x) {
            result++;
        }
        return Math.max(1, result);
    }
}
