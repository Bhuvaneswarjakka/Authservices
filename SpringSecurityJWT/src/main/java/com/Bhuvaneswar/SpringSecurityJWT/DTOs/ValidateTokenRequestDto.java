package com.Bhuvaneswar.SpringSecurityJWT.DTOs;

import lombok.Data;

@Data
public class ValidateTokenRequestDto
{
    private String token;
    private Long userId;
}
