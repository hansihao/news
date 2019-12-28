package edu.nciae.order.service;

import edu.nciae.order.domain.ShopOrder;
import edu.nciae.order.vo.ShopOrderVo;
import edu.nciae.system.domain.SysUser;

public interface ShopOrderService {

    /**
     * 新增订单
     *
     * @param shopOrderVo 订单信息
     * @return 结果
     */
    int insertOrder(SysUser sysUser, ShopOrderVo shopOrderVo);

    /**
     * 保存订单
     * @param shopOrder
     * @return
     */
    int saveOrder(ShopOrder shopOrder);
}
