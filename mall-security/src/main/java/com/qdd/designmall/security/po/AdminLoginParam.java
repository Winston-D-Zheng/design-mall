package com.qdd.designmall.security.po;

import com.qdd.designmall.mbp.model.UmsAdmin;
import lombok.Data;

@Data
public class AdminLoginParam {
    private String identifier;
    private String password;
    /**
     * 登陆类型
     * @see UmsAdmin#type
     */
    private int type;
}
