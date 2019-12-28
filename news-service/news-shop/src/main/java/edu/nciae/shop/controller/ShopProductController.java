package edu.nciae.shop.controller;

import edu.nciae.common.auth.annotation.HasPermissions;
import edu.nciae.common.controller.BaseController;
import edu.nciae.common.domain.R;
import edu.nciae.common.log.annotation.OperLog;
import edu.nciae.common.log.enums.BusinessType;
import edu.nciae.shop.domain.ShopProduct;
import edu.nciae.shop.service.ShopProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 商品信息
 */
@Slf4j
@RestController
@RequestMapping("product")
public class ShopProductController extends BaseController {
    @Autowired
    private ShopProductService shopProductService;

    /**
     * 查询商品列表
     * @param shopProduct
     * @return
     */
    @GetMapping("list")
    public R list(ShopProduct shopProduct) {
        startPage();
        return result(shopProductService.selectProductList(shopProduct));
    }

    /**
     * 新增保存商品
     */
    @HasPermissions("shop:product:add")
    @PostMapping("save")
    @OperLog(title = "商品管理", businessType = BusinessType.INSERT)
    public R addSave(@RequestBody ShopProduct shopProduct) {
        int i = shopProductService.insertProduct(shopProduct);
        return i > 0 ? R.ok() : R.error();
    }

    /**
     * 修改保存商品
     */
    @HasPermissions("shop:product:edit")
    @OperLog(title = "商品管理", businessType = BusinessType.UPDATE)
    @PostMapping("update")
    public R editSave(@RequestBody ShopProduct shopProduct) {
        int i = shopProductService.updateProduct(shopProduct);
        return i > 0 ? R.ok() : R.error();
    }

    /**
     * 修改状态
     *
     * @param shopProduct
     * @return
     */
    @HasPermissions("shop:product:edit")
    @PostMapping("status")
    @OperLog(title = "商品管理", businessType = BusinessType.UPDATE)
    public R status(@RequestBody ShopProduct shopProduct) {
        int i = shopProductService.changeStatus(shopProduct);
        return i > 0 ? R.ok() : R.error();
    }

    /**
     * 删除用户
     *
     * @throws Exception
     */
    @HasPermissions("shop:product:remove")
    @OperLog(title = "商品管理", businessType = BusinessType.DELETE)
    @PostMapping("remove")
    public R remove(String ids) throws Exception {
        int i = shopProductService.deleteProductByIds(ids);
        return i > 0 ? R.ok() : R.error();
    }

    /**
     * Redis 校验库存
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("stock/redis/{id}")
    public boolean checkStockWithRedis(@PathVariable Long id) {
        return shopProductService.checkStockWithRedis(id);
    }

    /**
     * 校验库存
     * @param id
     * @return
     */
    @GetMapping("stock/mysql/{id}")
    public boolean checkStock(@PathVariable Long id) {
        return shopProductService.getStockById(id);
    }

    /**
     * 根据id查询商品
     * @param id
     * @return
     */
    @GetMapping("find/{id}")
    public ShopProduct findProductById(@PathVariable("id") Long id) {
        return shopProductService.findProductById(id);
    }

    /**
     * 根据id查询商品,并且添加至redis
     * @param id
     * @return
     */
    @GetMapping("addcache/{id}")
    public ShopProduct findProductByIdAndCache(@PathVariable("id") Long id) {
        return shopProductService.findProductByIdAndCache(id);
    }

    /**
     * 减少库存
     * @param id
     * @param num
     * @return
     */
    @PutMapping("stockReduce/{id}/{num}")
    public int reduceOneStock(@PathVariable("id") Long id, @PathVariable("num") Integer num) {
        return shopProductService.reduceStock(id, num);
    }

}
