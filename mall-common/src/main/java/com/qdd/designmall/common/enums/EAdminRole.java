package com.qdd.designmall.common.enums;


import java.util.stream.Stream;

public enum EAdminRole {
    WRITER,             // 写手
    CUSTOMER_SERVICE,   // 客服
    MERCHANT;           // 商家

    public EAdminRole of(String roleName) {
        String upperRoleName = roleName.toUpperCase().strip();
        return Stream.of(values()).filter(v -> v.name().equals(upperRoleName)).findFirst().orElseThrow(() ->
                new RuntimeException("该权限类型不存在")
        );
    }

    /**
     * 获取security 需要的身份格式
     *
     * @param roleName 身份名称（不加 ROLE_ 前缀）
     * @return 带 ROLE_ 前缀的权限名
     */
    public String getRole(String roleName) {
        EAdminRole eAdminRole = this.of(roleName);
        return "ROLE_" + eAdminRole.name();
    }

    public String getRole() {
        return "ROLE_" + name();
    }

    public String getName(String roleName) {
        EAdminRole eAdminRole = of(roleName);
        return eAdminRole.name();
    }
}
