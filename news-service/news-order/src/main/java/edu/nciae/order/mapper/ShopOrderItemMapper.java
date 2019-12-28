package edu.nciae.order.mapper;

import edu.nciae.order.domain.ShopOrderItem;

import java.util.List;

public interface ShopOrderItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ShopOrderItem record);

    ShopOrderItem selectByPrimaryKey(Long id);

    List<ShopOrderItem> selectAll();

    int updateByPrimaryKey(ShopOrderItem record);
}