package hu.opdoc.openkmip4j;

/**
 * Created by peter on 2017.07.14.
 *
 * Represents the case when some settings have been made that were invalid in the given context.
 */
public class KmipInvalidSettingException extends KmipRuntimeException {

    public KmipInvalidSettingException(String message) {
        super(message);
    }

    public KmipInvalidSettingException(String message, Throwable cause) {
        super(message, cause);
    }

    public KmipInvalidSettingException(Throwable cause) {
        super(cause);
    }
}
