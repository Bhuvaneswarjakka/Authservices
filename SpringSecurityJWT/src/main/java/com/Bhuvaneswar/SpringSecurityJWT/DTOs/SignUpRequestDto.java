package com.Bhuvaneswar.SpringSecurityJWT.DTOs;

import lombok.Data;

@Data
public class SignUpRequestDto
{
    private String email;
    private String password;
}
