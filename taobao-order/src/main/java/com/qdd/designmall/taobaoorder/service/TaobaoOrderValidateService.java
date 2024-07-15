package com.qdd.designmall.taobaoorder.service;

import com.qdd.designmall.taobaoorder.po.ValidateConditionPo;

public interface TaobaoOrderValidateService {
    void validate(ValidateConditionPo param, Long shopId);
}
