package com.qdd.designmall.common.dto;

import com.qdd.designmall.common.enums.EUserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    EUserType EUserType;
    Long userId;
}
