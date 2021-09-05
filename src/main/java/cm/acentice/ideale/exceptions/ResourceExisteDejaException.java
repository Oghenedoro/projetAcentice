package cm.acentice.ideale.exceptions;

public class ResourceExisteDejaException extends Exception{
    public ResourceExisteDejaException() {
    }

    public ResourceExisteDejaException(String message) {
        super(message);
    }

    public ResourceExisteDejaException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceExisteDejaException(Throwable cause) {
        super(cause);
    }

    public ResourceExisteDejaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
