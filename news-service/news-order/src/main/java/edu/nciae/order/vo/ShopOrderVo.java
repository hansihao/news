package edu.nciae.order.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ShopOrderVo {
    @ApiModelProperty(value = "商品id")
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "购买数量")
    private Integer productQuantity;

    @ApiModelProperty(value = "收货人姓名")
    private String receiverName;

    @ApiModelProperty(value = "收货人电话")
    private String receiverPhone;

    @ApiModelProperty(value = "收货人邮编")
    private String receiverPostCode;

    @ApiModelProperty(value = "省份/直辖市")
    private String receiverProvince;

    @ApiModelProperty(value = "城市")
    private String receiverCity;

    @ApiModelProperty(value = "区")
    private String receiverRegion;

    @ApiModelProperty(value = "详细地址")
    private String receiverDetailAddress;

    @ApiModelProperty(value = "订单备注")
    private String note;


}
