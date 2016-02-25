package info.xudshen.jandan.data.exception;

public class FavoItemNotFoundException extends Exception {

    public FavoItemNotFoundException() {
        super();
    }

    public FavoItemNotFoundException(final String message) {
        super(message);
    }

    public FavoItemNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public FavoItemNotFoundException(final Throwable cause) {
        super(cause);
    }
}
