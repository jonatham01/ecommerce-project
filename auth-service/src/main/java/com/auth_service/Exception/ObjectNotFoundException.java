package com.auth_service.Exception;

public class ObjectNotFoundException  extends RuntimeException{
    public ObjectNotFoundException(){super("Object not found");}
    public ObjectNotFoundException(String message){super(message);}
    public ObjectNotFoundException(String message, Throwable cause){ super(message,cause);}

}
