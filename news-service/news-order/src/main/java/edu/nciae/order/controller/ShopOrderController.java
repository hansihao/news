package edu.nciae.order.controller;

import edu.nciae.common.annotation.LoginUser;
import edu.nciae.common.auth.annotation.HasPermissions;
import edu.nciae.common.domain.R;
import edu.nciae.common.log.annotation.OperLog;
import edu.nciae.common.log.enums.BusinessType;
import edu.nciae.order.service.ShopOrderService;
import edu.nciae.order.vo.ShopOrderVo;
import edu.nciae.system.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单提供者
 */
@RestController
@RequestMapping("order")
public class ShopOrderController {
    @Autowired
    private ShopOrderService shopOrderService;

    /**
     * 新增订单
     */
    @HasPermissions("shop:order:add")
    @PostMapping("save")
    @OperLog(title = "商品管理", businessType = BusinessType.INSERT)
    public R addSave(@LoginUser SysUser sysUser, @RequestBody ShopOrderVo shopOrderVo) {
        int i = shopOrderService.insertOrder(sysUser, shopOrderVo);
        return i >= 0 ? R.ok() : R.error("请勿重复下单");
    }
}
