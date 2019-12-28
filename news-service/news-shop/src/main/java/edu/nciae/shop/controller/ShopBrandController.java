package edu.nciae.shop.controller;

import edu.nciae.common.controller.BaseController;
import edu.nciae.common.domain.R;
import edu.nciae.shop.service.ShopBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 品牌提供者
 */
@RestController
@RequestMapping("brand")
public class ShopBrandController extends BaseController {
    @Autowired
    private ShopBrandService shopBrandService;

    @GetMapping("all")
    public R all() {
        return R.ok().put("rows", shopBrandService.selectBrandAll());
    }
}
