package com.qdd.designmall.portal.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.qdd.designmall.mbp.model.UmsMember;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class MemberUpdatePo extends UmsMember {
    @JsonIgnore
    private Long id;
    @JsonIgnore
    private String username;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private Integer status;
    @JsonIgnore
    private LocalDateTime createTime;
    @JsonIgnore
    private Integer sourceType;
}
