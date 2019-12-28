package edu.nciae.shop.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ShopProduct implements Serializable {
    private Long id;

    @ApiModelProperty(value = "品牌id")
    private Long brandId;

    @ApiModelProperty(value = "类型id")
    private Integer productCategoryId;

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

    @ApiModelProperty(value = "促销开始时间")
    private Date promotionStartTime;

    @ApiModelProperty(value = "促销结束时间")
    private Date promotionEndTime;

    @ApiModelProperty(value = "活动限购数量")
    private Integer promotionPerLimit;

    private static final long serialVersionUID = 1L;

    // 商品品牌
    private ShopBrand shopBrand;

    // 商品类型
    private ShopCategory shopCategory;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", brandId=").append(brandId);
        sb.append(", productCategoryId=").append(productCategoryId);
        sb.append(", productName=").append(productName);
        sb.append(", picture=").append(picture);
        sb.append(", productSn=").append(productSn);
        sb.append(", deleteStatus=").append(deleteStatus);
        sb.append(", publishStatus=").append(publishStatus);
        sb.append(", sale=").append(sale);
        sb.append(", price=").append(price);
        sb.append(", description=").append(description);
        sb.append(", stock=").append(stock);
        sb.append(", unit=").append(unit);
        sb.append(", promotionStartTime=").append(promotionStartTime);
        sb.append(", promotionEndTime=").append(promotionEndTime);
        sb.append(", promotionPerLimit=").append(promotionPerLimit);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}