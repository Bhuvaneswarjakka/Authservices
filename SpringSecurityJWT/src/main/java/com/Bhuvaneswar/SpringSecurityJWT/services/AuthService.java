package com.Bhuvaneswar.SpringSecurityJWT.services;

import com.Bhuvaneswar.SpringSecurityJWT.DTOs.UserDto;
import com.Bhuvaneswar.SpringSecurityJWT.Exceptions.UserAlreadyExistsException;
import com.Bhuvaneswar.SpringSecurityJWT.Exceptions.UserDoesNotExistsException;
import com.Bhuvaneswar.SpringSecurityJWT.Repositories.SessionRepository;
import com.Bhuvaneswar.SpringSecurityJWT.Repositories.UserRepository;
import com.Bhuvaneswar.SpringSecurityJWT.models.Session;
import com.Bhuvaneswar.SpringSecurityJWT.models.SessionStatus;
import com.Bhuvaneswar.SpringSecurityJWT.models.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

@Service
public class AuthService
{
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;
    private SessionRepository sessionRepository;
    public AuthService(UserRepository userRepository, SessionRepository sessionRepository)
    {
        this.userRepository=userRepository;
        this.sessionRepository=sessionRepository;
        this.bCryptPasswordEncoder=new BCryptPasswordEncoder();
    }
    public UserDto signUp(String email, String password) throws UserAlreadyExistsException
    {
        //if user is already exists in DB
        Optional<User> userOptional=userRepository.findByEmail(email);
        if(!userOptional.isEmpty())
        {
            throw new UserAlreadyExistsException("User is Already Exists");
        }
        //if user not exists in the DB. so, create new user account and save into DB.
        User user=new User();
        user.setEmail(email);
        //Instead of storing password directly store bcrypted password in database
        user.setPassword(bCryptPasswordEncoder.encode(password));
        //Saving user to database
        User SavedUser=userRepository.save(user);

        return UserDto.from(SavedUser);
    }
    public ResponseEntity<UserDto> login(String email, String password) throws UserDoesNotExistsException {
        Optional<User> userOptional=userRepository.findByEmail(email);
        if(userOptional.isEmpty())
        {
            throw new UserDoesNotExistsException("User not registered please sigup first");
        }

        User user=userOptional.get();
        if(!bCryptPasswordEncoder.matches(password, user.getPassword()))
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        //For now we are creating token , Actually in reality JWT will create tokens
        String token= RandomStringUtils.randomAscii(20);

        MultiValueMapAdapter<String, String> headers=new MultiValueMapAdapter<>(new HashMap<>());
        headers.add("AUTH_TOKEN", token);

        Session session=new Session();
        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setToken(token);
        session.setUser(user);
        sessionRepository.save(session);

        UserDto userDto=UserDto.from(user);
        //typically the token was send as a header in springboot
        ResponseEntity<UserDto> response=new ResponseEntity<>(
                userDto,
                headers,
                HttpStatus.OK
        );
        return response;
    }

    public SessionStatus validate(String token, Long userId)
    {
        Optional<Session> sessionOptional=sessionRepository.findByTokenAndUser_Id(token, userId);
        if(sessionOptional.isEmpty())
        {
            return SessionStatus.INVALID;
        }

        Session session=sessionOptional.get();

        if(!session.getSessionStatus().equals(SessionStatus.ACTIVE))
        {
            return SessionStatus.EXPIRED;
        }
        return SessionStatus.ACTIVE;
    }

    public ResponseEntity<Void> logout(String token, Long userId)
    {
        Optional<Session> sessionOptional=sessionRepository.findByTokenAndUser_Id(token, userId);

        if(sessionOptional.isEmpty())
        {
            return null;
        }

        Session session=sessionOptional.get();

        session.setSessionStatus(SessionStatus.LOGGED_OUT);

        sessionRepository.save(session);

        return ResponseEntity.ok().build();
    }
}
