package com.Bhuvaneswar.SpringSecurityJWT.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Session extends Basemodel
{
    @ManyToOne
    private User user;
    private String token;
    private Date expiringAt;
    @Enumerated(EnumType.ORDINAL)
    private SessionStatus sessionStatus;
}

/*@Enumerated(EnumType.ORDINAL)
  sessions
  id | user_id | token | expiringAt | sessionStatus
                                            1
                                            3
                                            2
 */

/*
@Enumerated(EnumType.STRING)
Sessions
id | user_id | token | expiringAt | sessionStatus
                                          ACTIVE
                                          LOGGED_OUT
                                          EXPIRED
 */
