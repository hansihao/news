package edu.nciae.shop.mapper;

import edu.nciae.shop.domain.ShopBrand;

import java.util.List;

public interface ShopBrandMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ShopBrand record);

    ShopBrand selectByPrimaryKey(Long id);

    List<ShopBrand> selectAll();

    int updateByPrimaryKey(ShopBrand record);

    /**
     * 根据条件分页查询品牌数据
     * @param shopBrand
     * @return
     */
    List<ShopBrand> selectShopBrandList(ShopBrand shopBrand);
}