package com.Bhuvaneswar.SpringSecurityJWT.models;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Role extends Basemodel
{
    private String rolename;
}
