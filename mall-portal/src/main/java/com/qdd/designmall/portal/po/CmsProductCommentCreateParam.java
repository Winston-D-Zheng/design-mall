package com.qdd.designmall.portal.po;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CmsProductCommentCreateParam {
    @Schema(description = "商品ID")
    @NotNull(message = "商品ID不能为空")
    private Long productId;

    @Schema(description = "是否匿名；1->匿名，0->实名")
    @NotNull(message = "是否匿名不能为空")
    private Integer nicknameStatus;

    @Schema(description = "评论内容")
    @NotBlank(message = "评论内容不能为空")
    private String comment;

    @Schema(description = "评论图片")
    private String pics;
}
