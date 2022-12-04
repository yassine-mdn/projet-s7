package uir.info.projetintegre.exception;

public class StockageFichierNotFoundException extends StockageException {

    public StockageFichierNotFoundException(String message) {
        super(message);
    }

    public StockageFichierNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
