package edu.nciae.shop.mapper;

import edu.nciae.shop.domain.ShopCartItem;

import java.util.List;

public interface ShopCartItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ShopCartItem record);

    ShopCartItem selectByPrimaryKey(Long id);

    List<ShopCartItem> selectAll();

    int updateByPrimaryKey(ShopCartItem record);
}