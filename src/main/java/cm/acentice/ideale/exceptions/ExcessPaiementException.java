package cm.acentice.ideale.exceptions;

public class ExcessPaiementException extends Exception{

    public ExcessPaiementException() {
    }

    public ExcessPaiementException(String message) {
        super(message);
    }

    public ExcessPaiementException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcessPaiementException(Throwable cause) {
        super(cause);
    }

    public ExcessPaiementException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
