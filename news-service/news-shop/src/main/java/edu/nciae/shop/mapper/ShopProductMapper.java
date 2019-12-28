package edu.nciae.shop.mapper;

import edu.nciae.shop.domain.ShopProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopProductMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ShopProduct record);

    ShopProduct selectByPrimaryKey(Long id);

    List<ShopProduct> selectAll();

    int updateByPrimaryKey(ShopProduct record);

    /**
     * 根据条件分页查询商品列表
     * @param product 商品信息
     * @return 商品信息集合信息
     */
    List<ShopProduct> selectProductList(ShopProduct product);

    /**
     * 添加商品
     * @param shopProduct
     * @return
     */
    int insertProduct(ShopProduct shopProduct);

    /**
     * 修改商品信息
     *
     * @param shopProduct 商品信息
     * @return 结果
     */
    int updateShopProduct(ShopProduct shopProduct);

    /**
     * 批量删除商品信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteProductByIds(Long[] ids);

    /**
     * 根据id查询库存
     * @param id
     * @return
     */
    int getStockById(Long id);

    /**
     * 减少库存
     * @param id
     * @param num
     * @return
     */
    int reduceStock(@Param("id") Long id, @Param("num") Integer num);

}