package battleship;

public class TooCloseException extends RuntimeException {

    public TooCloseException(String message) {
        super(message);
    }
}
