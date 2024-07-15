package com.qdd.designmall.common.dto;

import com.qdd.designmall.common.enums.EUserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    EUserType EUserType;
    Long userId;


    public static UserDto of(EUserType userType, Long userId) {
        return new UserDto(userType, userId);
    }

}
