package info.xudshen.jandan.data.exception;

public class PicNotFoundException extends Exception {

    public PicNotFoundException() {
        super();
    }

    public PicNotFoundException(final String message) {
        super(message);
    }

    public PicNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PicNotFoundException(final Throwable cause) {
        super(cause);
    }
}
