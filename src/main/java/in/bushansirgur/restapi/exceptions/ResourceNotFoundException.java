package in.bushansirgur.restapi.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String massage) {
        super(massage);
    }

}
