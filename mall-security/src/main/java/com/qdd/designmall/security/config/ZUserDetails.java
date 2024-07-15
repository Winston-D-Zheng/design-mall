package com.qdd.designmall.security.config;

import com.qdd.designmall.common.enums.EUserType;
import org.springframework.security.core.userdetails.UserDetails;

public interface ZUserDetails extends UserDetails {
    Long getUserId();
    EUserType getUserType();
}
