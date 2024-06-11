package com.qdd.designmall.mbp.model;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.qdd.designmall.common.serializer.PicUrlSerializer;
import lombok.Data;

/**
 * @TableName oms_order
 */
@TableName(value = "db_oms_order")
@Data
public class DbOmsOrder implements Serializable {
    @Serial
    private static final long serialVersionUID = 2457667875798002739L;

    private Long id;

    private String orderSn;

    private Long productId;

    private Long shopId;

    @JsonSerialize(using = PicUrlSerializer.class)
    private String productPic;

    private String productName;

    private BigDecimal productPrice;

    private BigDecimal realPrice;

    private String productAttr;

    private Long memberId;

    private LocalDateTime createTime;

    /**
     * 结束阶段
     */
    private Integer endStage;

    /**
     * 当前阶段。0-尚未开始
     */
    private Integer currentStage;
    /**
     * 订单状态：0->待付款；1->已支付；2->已完成；4->已关闭；5->已取消；6->无效订单；
     */
    private Integer status;

    /**
     * 删除状态：0->未删除；1->已删除
     */
    private Integer deleteStatus = 0;


    private LocalDateTime completeTime;
}