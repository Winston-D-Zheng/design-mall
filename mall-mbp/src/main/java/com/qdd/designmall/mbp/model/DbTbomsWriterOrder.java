package com.qdd.designmall.mbp.model;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;

/**
 * @TableName db_taobao_order_and_writer
 */
@TableName(value = "db_tboms_writer_order")
@Data
public class DbTbomsWriterOrder implements Serializable {
    private Long id;

    private Long shopId;

    private Long integratedOrderId;

    /**
     * 写手id
     */
    private Long writerId;

    /**
     * 应付金额
     */
    private BigDecimal shouldPay;

    /**
     * 支付状态：
     * <ul style="list-style-type: none;">
     *     <li>0: 未支付</li>
     *     <li>1: 已支付</li>
     * </ul>
     */
    private Integer payState;

    /**
     * 订单状态
     *
     * @see com.qdd.designmall.mbp.model.DbTbomsIntegratedOrder#orderState
     */
    private Integer orderState;

    /**
     * 支付单号
     */
    private String paymentOrderNo;

    /**
     * 支付时间
     */
    private Date payTime;


    private Long updaterId;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    @Serial
    private static final long serialVersionUID = 1L;
}