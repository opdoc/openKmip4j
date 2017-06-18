package hu.opdoc.openkmip4j.primitives;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * Created by peter on 2017.06.11..
 */
public class Interval extends Primitive {

    private static final Long MIN_VALUE = 0l;
    private static final Long MAX_VALUE = Math.round(Math.pow(2, 32));
    private final Duration value;

    public Interval(final Tag tag, final Duration value) {
        super(tag, Type.Interval);
        this.value = value == null ? Duration.ZERO : value;
        validate();
    }

    public Interval(final Tag tag, final Long value) {
        super(tag, Type.Interval);
        this.value = value == null ? Duration.ZERO : Duration.of(value, ChronoUnit.MILLIS);
        validate();
    }

    public Duration getValue() {
        return value;
    }

    public Long getNumericValue() {
        return value.get(ChronoUnit.MILLIS);
    }

    private void validate() {
        final long millis = value.get(ChronoUnit.MILLIS);
        if (millis < MIN_VALUE || millis > MAX_VALUE) {
            throw new IllegalArgumentException(String.format("An Interval value must be between 0 and 2^32 milliseconds. Given: %s that makes %d ms.", value.toString(), millis));
        }
    }
}
