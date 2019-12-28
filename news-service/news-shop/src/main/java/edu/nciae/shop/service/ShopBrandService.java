package edu.nciae.shop.service;

import edu.nciae.shop.domain.ShopBrand;

import java.util.List;

public interface ShopBrandService {

    /**
     * 查询所有品牌
     * @return 品牌列表
     */
    List<ShopBrand> selectBrandAll();

    /**
     * 根据条件分页查询品牌数据
     * @param shopBrand
     * @return
     */
    List<ShopBrand> selectShopBrandList(ShopBrand shopBrand);
}
