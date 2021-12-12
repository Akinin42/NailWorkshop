package org.nailservice.exception;

public class InvalidOrderTimeException extends RuntimeException {

    public InvalidOrderTimeException(String message) {
        super(message);        
    }    
}
