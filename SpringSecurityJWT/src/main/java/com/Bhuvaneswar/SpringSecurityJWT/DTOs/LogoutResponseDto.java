package com.Bhuvaneswar.SpringSecurityJWT.DTOs;

import lombok.Data;

@Data
public class LogoutResponseDto
{
    private String token;
    private Long userId;
}
