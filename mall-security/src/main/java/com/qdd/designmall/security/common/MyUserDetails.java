package com.qdd.designmall.security.common;

import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;


public interface MyUserDetails extends UserDetails {
    Long getId();
}
