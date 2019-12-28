package edu.nciae.shop.service;

import edu.nciae.shop.domain.ShopCategory;

import java.util.List;

public interface ShopCategoryService {
    /**
     * 查询所有类型
     * @return 类型列表
     */
    List<ShopCategory> selectCategoryAll();

    /**
     * 根据条件分页查询类型数据
     * @param shopCategory
     * @return
     */
    List<ShopCategory> selectShopCategoryList(ShopCategory shopCategory);
}
