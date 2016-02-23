package info.xudshen.jandan.data.exception;

public class JokeNotFoundException extends Exception {

    public JokeNotFoundException() {
        super();
    }

    public JokeNotFoundException(final String message) {
        super(message);
    }

    public JokeNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public JokeNotFoundException(final Throwable cause) {
        super(cause);
    }
}
