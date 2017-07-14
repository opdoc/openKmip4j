package hu.opdoc.openkmip4j.primitives;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * Created by peter on 2017.06.11.
 */
public class Interval extends SimpleValuePrimitive<Duration> {

    public static final Type TYPE = Type.Interval;

    private static final Long MIN_VALUE = 0l;
    private static final Long MAX_VALUE = Math.round(Math.pow(2, 32));

    private Duration value = null;

    public Interval() {
        this(null);
    }

    public Interval(final Tag tag) {
        super(tag, Type.Interval, Duration.class);
    }

    public Interval(final Tag tag, final Duration value) {
        super(tag, Type.Interval, Duration.class);
        this.value = value == null ? Duration.ZERO : value;
    }

    public Interval(final Tag tag, final Long value) {
        super(tag, Type.Interval, Duration.class);
        this.value = value == null ? Duration.ZERO : Duration.of(value, ChronoUnit.MILLIS);
    }

    @Override
    public Duration getValue() {
        return value;
    }

    public Long getNumericValue() {
        return (value == null) ? null : value.get(ChronoUnit.MILLIS);
    }

    @Override
    public void setValue(Duration value) {
        this.value = value;
    }

    @Override
    public boolean isEmpty() {
        return value == null;
    }

    @Override
    protected void calculateLength() {
        setLength(type.getLength());
    }

    @Override
    protected void validate() {
        super.validate();

        final long millis = value.get(ChronoUnit.MILLIS);
        if (millis < MIN_VALUE || millis > MAX_VALUE) {
            throw new IllegalArgumentException(String.format("An Interval value must be between 0 and 2^32 milliseconds. Given: %s that makes %d ms.", value.toString(), millis));
        }
    }
}
