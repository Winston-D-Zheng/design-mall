package com.qdd.designmall.mbp.model;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@TableName(value = "ums_admin")
@Data
public class UmsAdmin implements Serializable {

    @Serial
    private static final long serialVersionUID = 3417943140717202344L;

    private Long id;

    private String username;

    private String password;

    private String phone;

    private String uuid;

    private String icon;

    private String email;

    private String nickName;

    private String note;

    private LocalDateTime createAt;

    private LocalDateTime loginAt;

    /**
     * 身份类型：
     * <ul style="list-style-type: none;">
     *     <li>0-员工</li>
     *     <li>1-商家</li>
     * </ul>
     */
    private Integer type;

    /**
     * 用户可用状态：
     * <ul style="list-style-type: none;">
     *     <li>0-禁用</li>
     *     <li>1-启用</li>
     * </ul>
     */
    private Integer status;

    /**
     * 权限：
     * <ul style="list-style-type: none;">
     *     <li>MERCHANT：商家</li>
     *     <li>WRITER：写手</li>
     *     <li>CUSTOMER_SERVICE：客服</li>
     * </ul>
     */
    private String roles;
}