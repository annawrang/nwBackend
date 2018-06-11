package se.netwomen.NetWomenBackend.service;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(){super("User not found");}
}
