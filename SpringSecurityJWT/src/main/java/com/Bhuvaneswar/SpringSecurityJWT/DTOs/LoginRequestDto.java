package com.Bhuvaneswar.SpringSecurityJWT.DTOs;

import lombok.Data;

@Data
public class LoginRequestDto
{
    private String email;
    private String password;

}
