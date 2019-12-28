package edu.nciae.shop.service;

import edu.nciae.shop.domain.ShopProduct;

import java.util.List;

public interface ShopProductService {
    /**
     * 根据条件分页查询商品列表
     * @param product 商品信息
     * @return 商品信息集合信息
     */
    List<ShopProduct> selectProductList(ShopProduct product);

    /**
     * 商品状态修改
     * @param shopProduct 商品信息
     * @return 结果
     */
    int changeStatus(ShopProduct shopProduct);

    /**
     * 批量删除商品信息
     * @param ids 需要删除的数据ID
     * @return 结果
     * @throws Exception 异常
     */
    int deleteProductByIds(String ids) throws Exception;

    /**
     * 添加商品
     * @param shopProduct
     * @return
     */
    int insertProduct(ShopProduct shopProduct);

    /**
     * 修改商品信息
     * @param shopProduct
     * @return
     */
    int updateProduct(ShopProduct shopProduct);

    /**
     * 根据id查询库存
     * @param id
     * @return
     */
    boolean getStockById(Long id);

    /**
     * 从redis中根据id查询库存
     * @param id
     * @return
     */
    boolean checkStockWithRedis(Long id);

    /**
     * 根据id查询商品
     * @param id
     * @return
     */
    ShopProduct findProductById(Long id);

    /**
     * 根据id查询商品,并且添加至redis
     * @param id
     * @return
     */
    ShopProduct findProductByIdAndCache(Long id);

    /**
     * 减少库存
     * @param id
     * @param num
     * @return
     */
    int reduceStock(Long id, Integer num);
}
