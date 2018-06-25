package se.netwomen.NetWomenBackend.service.exceptions;

public class EmailNotFoundException extends RuntimeException {
    public EmailNotFoundException(String s){
        super(s);
    }
}
