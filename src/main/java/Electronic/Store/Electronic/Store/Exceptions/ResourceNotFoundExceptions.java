package Electronic.Store.Electronic.Store.Exceptions;

import lombok.Builder;

@Builder
public class ResourceNotFoundExceptions extends RuntimeException {

    public ResourceNotFoundExceptions() {
        super("Resources not found !!");
    }

    public ResourceNotFoundExceptions(String message) {
        super(message);
    }
}
