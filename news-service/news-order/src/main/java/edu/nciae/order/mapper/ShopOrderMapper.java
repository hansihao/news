package edu.nciae.order.mapper;

import edu.nciae.order.domain.ShopOrder;

import java.util.List;

public interface ShopOrderMapper {
    int deleteByPrimaryKey(String id);

    int insert(ShopOrder record);

    ShopOrder selectByPrimaryKey(String id);

    List<ShopOrder> selectAll();

    int updateByPrimaryKey(ShopOrder record);
}