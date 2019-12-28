package edu.nciae.shop.mapper;

import edu.nciae.shop.domain.ShopCategory;

import java.util.List;

public interface ShopCategoryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ShopCategory record);

    ShopCategory selectByPrimaryKey(Long id);

    List<ShopCategory> selectAll();

    int updateByPrimaryKey(ShopCategory record);

    /**
     * 根据条件分页查询类型数据
     * @param shopCategory
     * @return
     */
    List<ShopCategory> selectShopCategoryList(ShopCategory shopCategory);
}