package edu.nciae.shop.domain;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ShopCategory implements Serializable {
    private Long id;

    @ApiModelProperty(value = "上级分类的编号：0表示一级分类")
    private Long parentId;

    @ApiModelProperty(value = "类别名称")
    private String categoryName;

    @ApiModelProperty(value = "产品数量")
    private Integer productCount;

    @ApiModelProperty(value = "产品单位")
    private String productUnit;

    @ApiModelProperty(value = "显示状态：0->不显示；1->显示")
    private Integer showStatus;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "描述")
    private String description;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    public Integer getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(Integer showStatus) {
        this.showStatus = showStatus;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", parentId=").append(parentId);
        sb.append(", categoryName=").append(categoryName);
        sb.append(", productCount=").append(productCount);
        sb.append(", productUnit=").append(productUnit);
        sb.append(", showStatus=").append(showStatus);
        sb.append(", icon=").append(icon);
        sb.append(", description=").append(description);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}