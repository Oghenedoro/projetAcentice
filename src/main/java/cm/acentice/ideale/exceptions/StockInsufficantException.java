package cm.acentice.ideale.exceptions;

public class StockInsufficantException extends Exception{

    public StockInsufficantException() {
    }

    public StockInsufficantException(String message) {
        super(message);
    }

    public StockInsufficantException(String message, Throwable cause) {
        super(message, cause);
    }

    public StockInsufficantException(Throwable cause) {
        super(cause);
    }

    public StockInsufficantException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
