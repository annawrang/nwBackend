package se.netwomen.NetWomenBackend.service.exceptions;

public class InvalidCookieException extends RuntimeException {
    public InvalidCookieException(){ super("Invalid cookie");};
}
