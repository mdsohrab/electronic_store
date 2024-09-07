package Electronic.Store.Electronic.Store.Exceptions;

public class BadApiRequest extends RuntimeException {

    public BadApiRequest(String message) {
        super(message);

    }

    public BadApiRequest() {
        super("Bad request !! ");
    }
}
