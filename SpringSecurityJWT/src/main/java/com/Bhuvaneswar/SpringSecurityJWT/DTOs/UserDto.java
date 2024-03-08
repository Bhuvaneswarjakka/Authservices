package com.Bhuvaneswar.SpringSecurityJWT.DTOs;

import com.Bhuvaneswar.SpringSecurityJWT.models.Role;
import com.Bhuvaneswar.SpringSecurityJWT.models.User;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserDto
{
    private String email;
    private Set<Role> roles=new HashSet<>();

    public static UserDto from(User user)
    {
        UserDto userDto=new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());
        return userDto;
    }
    /*
    Userdto.from(user);
     */

}
