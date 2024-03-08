package com.Bhuvaneswar.SpringSecurityJWT.Exceptions;

public class UserAlreadyExistsException extends Exception
{
    public UserAlreadyExistsException(String message)
    {
        super(message);
    }

}
