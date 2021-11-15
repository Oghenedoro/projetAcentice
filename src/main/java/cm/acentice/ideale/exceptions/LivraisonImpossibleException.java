package cm.acentice.ideale.exceptions;

public class LivraisonImpossibleException extends Exception{

    public LivraisonImpossibleException() {
    }

    public LivraisonImpossibleException(String message) {
        super(message);
    }

    public LivraisonImpossibleException(String message, Throwable cause) {
        super(message, cause);
    }

    public LivraisonImpossibleException(Throwable cause) {
        super(cause);
    }

    public LivraisonImpossibleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
