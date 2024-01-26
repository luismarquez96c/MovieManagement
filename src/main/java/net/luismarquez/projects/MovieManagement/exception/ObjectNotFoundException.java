package net.luismarquez.projects.MovieManagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ObjectNotFoundException extends RuntimeException {

    private final String objectNotFoundName;
    private final Throwable cause;

    public ObjectNotFoundException(String objectNotFoundName) {
        this.objectNotFoundName = objectNotFoundName;
        this.cause = null;
    }

    public ObjectNotFoundException(String objectNotFoundName, Throwable cause) {
        this.objectNotFoundName = objectNotFoundName;
        this.cause = cause;
    }

    @Override
    public String getMessage() {

        String message = super.getMessage();

        if(message == null){
            message = "";
        }

        return message
                .concat("(object not found: ")
                .concat(this.objectNotFoundName).concat(")");
    }

    public String getObjectNotFoundName() {
        return objectNotFoundName;
    }
}
