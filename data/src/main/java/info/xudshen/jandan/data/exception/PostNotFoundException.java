package info.xudshen.jandan.data.exception;

public class PostNotFoundException extends Exception {

    public PostNotFoundException() {
        super();
    }

    public PostNotFoundException(final String message) {
        super(message);
    }

    public PostNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PostNotFoundException(final Throwable cause) {
        super(cause);
    }
}
