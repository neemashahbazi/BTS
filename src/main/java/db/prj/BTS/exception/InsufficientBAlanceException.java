package db.prj.BTS.exception;

public class InsufficientBAlanceException extends RuntimeException {
    public InsufficientBAlanceException(String errorMessage) {
        super(errorMessage);
    }
}
