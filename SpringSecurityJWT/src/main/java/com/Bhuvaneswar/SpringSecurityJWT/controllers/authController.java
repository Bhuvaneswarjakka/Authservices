package com.Bhuvaneswar.SpringSecurityJWT.controllers;


import com.Bhuvaneswar.SpringSecurityJWT.DTOs.*;
import com.Bhuvaneswar.SpringSecurityJWT.Exceptions.UserAlreadyExistsException;
import com.Bhuvaneswar.SpringSecurityJWT.Exceptions.UserDoesNotExistsException;
import com.Bhuvaneswar.SpringSecurityJWT.models.SessionStatus;
import com.Bhuvaneswar.SpringSecurityJWT.models.User;
import com.Bhuvaneswar.SpringSecurityJWT.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class authController
{
    private AuthService authService;
    public authController(AuthService authService)
    {
        this.authService=authService;
    }
    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpRequestDto request) throws UserAlreadyExistsException {
        UserDto userDto=authService.signUp(request.getEmail(), request.getPassword());
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }
    /*
    Why userdto we are returning?
     because we don't need total information of user
     */

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto request) throws UserDoesNotExistsException {
        return authService.login(request.getEmail(), request.getPassword());

    }

    @PostMapping("/validate")
    public ResponseEntity<SessionStatus> validateToken(@RequestBody ValidateTokenRequestDto request)
    {
        SessionStatus sessionStatus=authService.validate(request.getToken(), request.getUserId());
        return new ResponseEntity<>(sessionStatus, HttpStatus.ACCEPTED);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutResponseDto request)
    {
        return authService.logout(request.getToken(), request.getUserId());
    }
}
