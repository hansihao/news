package edu.nciae.shop.controller;

import edu.nciae.common.controller.BaseController;
import edu.nciae.common.domain.R;
import edu.nciae.shop.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类型提供者
 */
@RestController
@RequestMapping("category")
public class ShopCategoryController extends BaseController {
    @Autowired
    private ShopCategoryService shopCategoryService;

    @GetMapping("all")
    public R all() {
        return R.ok().put("rows", shopCategoryService.selectCategoryAll());
    }

}
