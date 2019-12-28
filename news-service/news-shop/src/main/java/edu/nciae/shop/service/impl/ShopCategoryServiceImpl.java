package edu.nciae.shop.service.impl;

import edu.nciae.shop.domain.ShopCategory;
import edu.nciae.shop.mapper.ShopCategoryMapper;
import edu.nciae.shop.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {
    @Autowired
    private ShopCategoryMapper shopCategoryMapper;

    /**
     * 查询所有类型
     * @return
     */
    @Override
    public List<ShopCategory> selectCategoryAll() {
        return selectShopCategoryList(new ShopCategory());
    }

    /**
     * 根据条件分页查询类型数据
     * @param shopCategory
     * @return
     */
    @Override
    public List<ShopCategory> selectShopCategoryList(ShopCategory shopCategory) {
        return shopCategoryMapper.selectShopCategoryList(shopCategory);
    }
}
