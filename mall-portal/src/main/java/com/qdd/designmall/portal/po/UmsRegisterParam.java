package com.qdd.designmall.portal.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.qdd.designmall.mbp.model.UmsMember;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class UmsRegisterParam extends UmsMember {
    @JsonIgnore
    private Long id;
    @JsonIgnore
    private Integer status;
    @JsonIgnore
    private LocalDateTime createTime;

    String username;
    String password;
    String nickname;
    String phone;

}