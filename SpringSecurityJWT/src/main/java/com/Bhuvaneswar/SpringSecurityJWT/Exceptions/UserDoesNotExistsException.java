package com.Bhuvaneswar.SpringSecurityJWT.Exceptions;

public class UserDoesNotExistsException extends Exception
{
    public UserDoesNotExistsException(String message)
    {
        super(message);
    }
}
