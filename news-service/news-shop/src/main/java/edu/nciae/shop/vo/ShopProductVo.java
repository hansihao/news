package edu.nciae.shop.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class ShopProductVo {
    private Long id;

    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    @ApiModelProperty(value = "类型名称")
    private String productCategoryName;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "图片")
    private String picture;

    @ApiModelProperty(value = "货号")
    private String productSn;

    @ApiModelProperty(value = "删除状态：0->未删除；1->已删除")
    private Integer deleteStatus;

    @ApiModelProperty(value = "上架状态：0->下架；1->上架")
    private Integer publishStatus;

    @ApiModelProperty(value = "销量")
    private Integer sale;

    @ApiModelProperty(value = "单价")
    private BigDecimal price;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "库存")
    private Integer stock;

    @ApiModelProperty(value = "单位")
    private String unit;

}
