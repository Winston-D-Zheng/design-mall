package com.qdd.designmall.mbp.model;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.qdd.designmall.common.serializer.PicUrlSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @TableName db_pms_category
 */
@TableName(value ="db_pms_category")
@Data
public class DbPmsCategory implements Serializable {
    private Long id;

    @NotBlank(message = "name 不能为空")
    private String name;

    @JsonSerialize(using = PicUrlSerializer.class)
    private String pic;

    @Schema(description = "如果为0，代表一级分类")
    @NotNull(message = "不能为空")
    private Long parentId;

    private Integer level;

    private Integer sort;

    private Long productCount;
}