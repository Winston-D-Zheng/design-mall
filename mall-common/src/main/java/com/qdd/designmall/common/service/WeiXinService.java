package com.qdd.designmall.common.service;


public interface WeiXinService {

    /**
     * 生产小程序二维码
     *
     * @param payload 载荷，最大32个可见字符，只支持数字，大小写英文以及部分特殊字符：!#$&'()*+,/:;=?@-._~
     * @return Base64
     */
    String generateMiniProgramQRCode(String payload);
}
