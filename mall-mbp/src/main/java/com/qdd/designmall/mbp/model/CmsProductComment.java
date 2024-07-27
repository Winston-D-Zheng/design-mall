package com.qdd.designmall.mbp.model;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.qdd.designmall.common.serializer.PicUrlSerializer;
import lombok.Data;

/**
 * @TableName cms_product_comment
 */
@TableName(value ="cms_product_comment")
@Data
public class CmsProductComment implements Serializable {
    @Serial
    private static final long serialVersionUID = 230674177140513263L;
    private Integer id;

    private Long productId;

    private Long shopId;

    private Long memberId;

    private String memberNickname;

    private Integer nicknameStatus;

    private String comment;

    @JsonSerialize(using = PicUrlSerializer.class)
    private String pics;

    private LocalDateTime createTime;

    private Integer deleteStatus;
}