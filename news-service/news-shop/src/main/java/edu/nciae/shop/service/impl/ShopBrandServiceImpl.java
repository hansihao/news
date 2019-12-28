package edu.nciae.shop.service.impl;

import edu.nciae.shop.domain.ShopBrand;
import edu.nciae.shop.mapper.ShopBrandMapper;
import edu.nciae.shop.service.ShopBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopBrandServiceImpl implements ShopBrandService {
    @Autowired
    private ShopBrandMapper shopBrandMapper;

    /**
     * 查询所有品牌
     * @return
     */
    @Override
    public List<ShopBrand> selectBrandAll() {
        return selectShopBrandList(new ShopBrand());
    }

    /**
     * 根据条件分页查询品牌数据
     *
     * @param shopBrand 品牌信息
     * @return 品牌数据集合信息
     */
    @Override
    public List<ShopBrand> selectShopBrandList(ShopBrand shopBrand) {
        return shopBrandMapper.selectShopBrandList(shopBrand);
    }
}
