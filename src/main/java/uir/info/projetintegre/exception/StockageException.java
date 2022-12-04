package uir.info.projetintegre.exception;

public class StockageException extends RuntimeException {

    public StockageException(String message) {
        super(message);
    }

    public StockageException(String message, Throwable cause) {
        super(message, cause);
    }
}
